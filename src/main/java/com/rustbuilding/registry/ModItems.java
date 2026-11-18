package com.rustbuilding.registry;

import com.rustbuilding.RustBuildingSystem;
import com.rustbuilding.item.BuildingHammerItem;
import com.rustbuilding.item.BuildingPlanItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, RustBuildingSystem.MODID);

    public static final RegistryObject<Item> BUILDING_PLAN = ITEMS.register("building_plan",
            () -> new BuildingPlanItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BUILDING_HAMMER = ITEMS.register("building_hammer",
            () -> new BuildingHammerItem(new Item.Properties().durability(250)));

    public static final RegistryObject<Item> TWIG_FOUNDATION_ITEM = ITEMS.register("twig_foundation",
            () -> new BlockItem(ModBlocks.TWIG_FOUNDATION.get(), new Item.Properties()));
    public static final RegistryObject<Item> BUILDING_WALL_ITEM = ITEMS.register("building_wall",
            () -> new BlockItem(ModBlocks.BUILDING_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> BUILDING_FLOOR_ITEM = ITEMS.register("building_floor",
            () -> new BlockItem(ModBlocks.BUILDING_FLOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> BUILDING_ROOF_ITEM = ITEMS.register("building_roof",
            () -> new BlockItem(ModBlocks.BUILDING_ROOF.get(), new Item.Properties()));

    private ModItems() {}
}
