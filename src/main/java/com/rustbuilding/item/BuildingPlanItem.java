package com.rustbuilding.item;

import com.rustbuilding.block.BuildingBlock;
import com.rustbuilding.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.LocalPlayer;
import java.util.function.Consumer;
import com.rustbuilding.client.HeldItemAnimations;

public class BuildingPlanItem extends Item {
    public BuildingPlanItem(Properties properties) { super(properties); }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player,
                    net.minecraft.world.entity.HumanoidArm arm, net.minecraft.world.item.ItemStack stack,
                    float partialTick, float equipProcess, float swingProcess) {
                return HeldItemAnimations.applyPlan(poseStack, player, arm, stack,
                        partialTick, equipProcess, swingProcess);
            }
        });
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos target = context.getClickedPos().relative(context.getClickedFace());
        if (!level.getBlockState(target).canBeReplaced()) return InteractionResult.FAIL;
        if (level.isClientSide) return InteractionResult.SUCCESS;

        boolean foundation = level.getBlockState(target.below()).isAir();
        BlockState state = (foundation ? ModBlocks.TWIG_FOUNDATION.get() : ModBlocks.BUILDING_WALL.get())
                .defaultBlockState();
        Direction facing = context.getPlayer() == null ? Direction.NORTH : context.getPlayer().getDirection().getOpposite();
        state = state.setValue(BuildingBlock.FACING, facing);
        level.setBlock(target, state, 3);
        return InteractionResult.CONSUME;
    }
}
