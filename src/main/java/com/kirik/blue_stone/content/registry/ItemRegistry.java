package com.kirik.blue_stone.content.registry;

import com.kirik.blue_stone.content.items.TestItem;
import com.kirik.blue_stone.core.Bluestone;
import com.kirik.blue_stone.test.TestRender;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistry {
    public static final DeferredRegister<Item> BLUESTONE_ITEM_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, Bluestone.MOD_ID);

    //public static final RegistryObject<Item> TEST_OVERLAY_ITEM = BLUESTONE_ITEM_REGISTER.register("test_overlay_item", TestRender.TestOverlayItem::new);
    //public static final RegistryObject<Item> TEST_ITEM = BLUESTONE_ITEM_REGISTER.register("test_item", () -> new TestItem(new Item.Properties()));
}
