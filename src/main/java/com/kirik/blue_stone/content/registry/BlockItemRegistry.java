package com.kirik.blue_stone.content.registry;

import com.kirik.blue_stone.core.Bluestone;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Bluestone.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockItemRegistry {
    @SubscribeEvent
    public static void itemRegistryEvent(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        BlockRegistry.BLUESTONE_BLOCK_REGISTER.getEntries().stream().map(RegistryObject::get).forEach(block -> {
            Item.Properties properties = new Item.Properties().tab(Bluestone.BLUESTONE_TAB);
            BlockItem blockItem = new BlockItem(block, properties);
            blockItem.setRegistryName(block.getRegistryName());
            registry.register(blockItem);
        });
    }
}
