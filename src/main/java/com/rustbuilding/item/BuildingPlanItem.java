package com.rustbuilding.item;

import com.rustbuilding.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;

public class BuildingPlanItem extends Item {
    public BuildingPlanItem(Properties properties) { super(properties); }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (level.isClientSide) return InteractionResult.SUCCESS;

        BlockPos target = context.getClickedPos().relative(context.getClickedFace());
        if (!level.getBlockState(target).canBeReplaced()) return InteractionResult.FAIL;

        // MVP placement: the plan currently places a basic wall. Later versions
        // will add a radial menu and grid-snapped selection of every building piece.
        BlockState state = ModBlocks.BUILDING_WALL.get().defaultBlockState();
        if (level.getBlockState(target.below()).isAir()) {
            state = ModBlocks.TWIG_FOUNDATION.get().defaultBlockState();
        }
        level.setBlock(target, state, 3);
        if (!context.getPlayer().getAbilities().instabuild) {
            context.getItemInHand().shrink(0); // Building Plan is reusable.
        }
        return InteractionResult.CONSUME;
    }
}
