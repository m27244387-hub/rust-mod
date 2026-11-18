package com.rustbuilding.block;

import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BuildingBlock extends Block {
    public static final IntegerProperty TIER = IntegerProperty.create("tier", 0, 4);

    public BuildingBlock(MapColor color, float strength, SoundType sound) {
        super(BlockBehaviour.Properties.of().mapColor(color).strength(strength).sound(sound)
                .requiresCorrectToolForDrops());
        registerDefaultState(stateDefinition.any().setValue(TIER, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TIER);
    }
}
