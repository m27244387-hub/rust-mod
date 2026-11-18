package com.rustbuilding.item;

import com.rustbuilding.block.BuildingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class BuildingHammerItem extends Item {
    public BuildingHammerItem(Properties properties) { super(properties); }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        if (!state.hasProperty(BuildingBlock.TIER)) return InteractionResult.PASS;
        if (level.isClientSide) return InteractionResult.SUCCESS;

        int tier = state.getValue(BuildingBlock.TIER);
        if (tier >= 4) {
            context.getPlayer().displayClientMessage(Component.literal("Уже максимальный уровень: Armored"), true);
            return InteractionResult.CONSUME;
        }

        int nextTier = tier + 1;
        // MVP tier progression. Resource costs and TC authorization are added
        // in the next implementation milestone.
        level.setBlock(pos, state.setValue(BuildingBlock.TIER, nextTier), 3);
        context.getPlayer().displayClientMessage(
                Component.literal("Улучшено до уровня " + tierName(nextTier)), true);
        return InteractionResult.CONSUME;
    }

    private static String tierName(int tier) {
        return switch (tier) {
            case 0 -> "Twig";
            case 1 -> "Wood";
            case 2 -> "Stone";
            case 3 -> "Metal";
            case 4 -> "Armored";
            default -> "Unknown";
        };
    }
}
