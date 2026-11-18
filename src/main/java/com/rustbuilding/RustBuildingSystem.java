package com.rustbuilding;

import com.rustbuilding.registry.ModBlocks;
import com.rustbuilding.registry.ModItems;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(RustBuildingSystem.MODID)
public class RustBuildingSystem {
    public static final String MODID = "rustbuilding";

    public RustBuildingSystem() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlocks.BLOCKS.register(bus);
        ModItems.ITEMS.register(bus);
    }
}
