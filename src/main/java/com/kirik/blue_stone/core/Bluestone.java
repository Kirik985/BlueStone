package com.kirik.blue_stone.core;

import com.kirik.blue_stone.api.contents.Blocks;
import com.kirik.blue_stone.content.registry.BlockRegistry;
import com.kirik.blue_stone.content.registry.ItemRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Bluestone.MOD_ID)
public class Bluestone {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final Material BluestoneMaterial = (new Material.Builder(MaterialColor.COLOR_CYAN)).build();
    public static final String MOD_ID = "bluestone";
    public static final ItemGroup BLUESTONE_TAB = new BluestoneTab("Bluestone");
    public Bluestone() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setup);

        BlockRegistry.BLUESTONE_BLOCK_REGISTER.register(eventBus);
        ItemRegistry.BLUESTONE_ITEM_REGISTER.register(eventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }
    private void setup(FMLCommonSetupEvent event) {

    }
    public static class BluestoneTab extends ItemGroup {
        public BluestoneTab(String label) {
            super(label);
        }

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Item.BY_BLOCK.getOrDefault(Blocks.BLUESTONE_WIRE_MONO_BLOCK, Items.REDSTONE));
        }
    }
}
