package com.kirik.blue_stone.test;

import com.kirik.blue_stone.content.registry.ItemRegistry;
import com.kirik.blue_stone.core.Bluestone;
import com.kirik.blue_stone.core.Util;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

import static com.kirik.blue_stone.core.Util.*;

//@Mod.EventBusSubscriber
@SuppressWarnings("deprecation")
public class TestRender {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        TestCommand.register(event.getDispatcher());
    }
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void eventHandler(RenderGameOverlayEvent event) {
        int posX = (event.getWindow().getGuiScaledWidth()) / 2;
        int posY = (event.getWindow().getGuiScaledHeight()) / 2;
        PlayerEntity entity = Minecraft.getInstance().player;
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderSystem.color4f(0, 0, 1.0F, 0.5F);
        RenderSystem.disableAlphaTest();
        if (entity == null) return;
        if (entity.getPersistentData().contains("test_message")) {
            drawString(event.getMatrixStack(), entity.getPersistentData().getString("test_message"), (posX * 2) + 100, (posY * 2 + 50), -1);
        }

        /*
        if (entity.getMainHandItem().getItem().equals(ItemRegistry.TEST_OVERLAY_ITEM.get())) {
            //Minecraft.getInstance().getTextureManager().bind(new ResourceLocation(Bluestone.MOD_ID, "textures/gui/test_overlay.png"));
            //AbstractGui.blit(event.getMatrixStack(), posX + -114, posY + -129, 0, 0, 256, 256, 256, 256);
            if (entity.getOffhandItem().getItem().equals(Items.BRICK)) {
                Minecraft.getInstance().font.draw(event.getMatrixStack(), "Lol, you are smart! XD", posX - 203, posY - 97, -1);
            }
        }
         */
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.enableAlphaTest();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static class TestOverlayItem extends Item {
        public TestOverlayItem() {
            super(new Properties().stacksTo(1).tab(Bluestone.BLUESTONE_TAB));
        }
    }
    public static class TestCommand {
        public static void register(CommandDispatcher<CommandSource> sourceCommandDispatcher) {
            sourceCommandDispatcher.register(Commands.literal("test_overlay").then(Commands.argument("message", StringArgumentType.greedyString())).executes((source) -> {
                String s = StringArgumentType.getString(source, "message");
                source.getSource().getEntity().getPersistentData().putString("test_message", s);
                return 1;
            }));
        }
    }
}
