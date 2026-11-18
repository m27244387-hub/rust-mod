package com.rustbuilding.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.core.Direction;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BuildingBlock extends Block {
    public static final IntegerProperty TIER = IntegerProperty.create("tier", 0, 4);
    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);

    public BuildingBlock(MapColor color, float strength, SoundType sound) {
        super(BlockBehaviour.Properties.of().mapColor(color).strength(strength).sound(sound)
                .requiresCorrectToolForDrops());
        registerDefaultState(stateDefinition.any().setValue(TIER, 0).setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TIER, FACING);
    }
}
