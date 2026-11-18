package com.rustbuilding.item;

import com.rustbuilding.block.BuildingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.LocalPlayer;
import java.util.function.Consumer;
import com.rustbuilding.client.HeldItemAnimations;

public class BuildingHammerItem extends Item {
    private static final int[] COSTS = {0, 8, 12, 8, 1};

    public BuildingHammerItem(Properties properties) { super(properties); }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player,
                    net.minecraft.world.entity.HumanoidArm arm, ItemStack stack,
                    float partialTick, float equipProcess, float swingProcess) {
                return HeldItemAnimations.applyHammer(poseStack, player, arm, stack,
                        partialTick, equipProcess, swingProcess);
            }
        });
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        if (!state.hasProperty(BuildingBlock.TIER)) return InteractionResult.PASS;
        if (level.isClientSide) return InteractionResult.SUCCESS;

        Player player = context.getPlayer();
        if (player == null) return InteractionResult.PASS;
        int tier = state.getValue(BuildingBlock.TIER);
        if (tier >= 4) {
            player.displayClientMessage(Component.literal("Уже максимальный уровень: Armored"), true);
            return InteractionResult.CONSUME;
        }

        int next = tier + 1;
        int cost = COSTS[next];
        net.minecraft.world.item.Item required = switch (next) {
            case 1 -> Items.OAK_PLANKS;
            case 2 -> Items.COBBLESTONE;
            case 3 -> Items.IRON_INGOT;
            case 4 -> Items.NETHERITE_INGOT;
            default -> Items.AIR;
        };
        int have = count(player, required);
        if (!player.getAbilities().instabuild && have < cost) {
            player.displayClientMessage(Component.literal("Не хватает ресурсов: нужно " + cost + " × " + required.getDescription().getString()
                    + ", у вас " + have), true);
            return InteractionResult.CONSUME;
        }
        if (!player.getAbilities().instabuild) consume(player, required, cost);
        level.setBlock(pos, state.setValue(BuildingBlock.TIER, next), 3);
        context.getItemInHand().hurtAndBreak(1, player, p -> p.broadcastBreakEvent(context.getHand()));
        player.displayClientMessage(Component.literal("Улучшено до " + tierName(next)
                + " | базовая прочность уровня: " + hp(next) + " HP (система урона ещё не подключена)"), true);
        return InteractionResult.CONSUME;
    }

    private static int count(Player player, net.minecraft.world.item.Item item) {
        return player.getInventory().items.stream().filter(s -> s.is(item)).mapToInt(ItemStack::getCount).sum();
    }

    private static void consume(Player player, net.minecraft.world.item.Item item, int amount) {
        for (ItemStack stack : player.getInventory().items) {
            if (amount <= 0) break;
            if (stack.is(item)) {
                int take = Math.min(amount, stack.getCount());
                stack.shrink(take);
                amount -= take;
            }
        }
    }

    private static int hp(int tier) { return new int[]{10, 250, 500, 1000, 2000}[tier]; }
    private static String tierName(int tier) {
        return switch (tier) {
            case 0 -> "Twig"; case 1 -> "Wood"; case 2 -> "Stone";
            case 3 -> "Metal"; case 4 -> "Armored"; default -> "Unknown";
        };
    }
}
