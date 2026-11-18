package com.rustbuilding.registry;

import com.rustbuilding.RustBuildingSystem;
import com.rustbuilding.block.BuildingBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, RustBuildingSystem.MODID);

    public static final RegistryObject<Block> TWIG_FOUNDATION = BLOCKS.register("twig_foundation",
            () -> new BuildingBlock(MapColor.WOOD, 0.2f, SoundType.GRASS));
    public static final RegistryObject<Block> BUILDING_WALL = BLOCKS.register("building_wall",
            () -> new BuildingBlock(MapColor.WOOD, 2.0f, SoundType.WOOD));
    public static final RegistryObject<Block> BUILDING_FLOOR = BLOCKS.register("building_floor",
            () -> new BuildingBlock(MapColor.WOOD, 2.0f, SoundType.WOOD));
    public static final RegistryObject<Block> BUILDING_ROOF = BLOCKS.register("building_roof",
            () -> new BuildingBlock(MapColor.WOOD, 2.0f, SoundType.WOOD));

    private ModBlocks() {}
}
