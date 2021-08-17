package com.kirik.blue_stone.content.items;

import com.kirik.blue_stone.core.Bluestone;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestItem extends Item {
    Logger logger = LogManager.getLogger();
    public TestItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }
    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        if (world.isClientSide) {
            return ActionResult.success(playerEntity.getMainHandItem());
        } else {
            Vector3d playerLook = playerEntity.getLookAngle();
            logger.debug("Player Look angle: " + playerLook);
            Vector3d reversedPlayerLook = playerLook.reverse();
            logger.debug("Reversed Player Look angle: " + reversedPlayerLook);
            Vector3d multipliedReversedPlayerLook = reversedPlayerLook.scale(5D);
            logger.debug("Multiplied Reversed Player Look angle:" + multipliedReversedPlayerLook);
            //playerEntity.move(MoverType.SELF, multipliedReversedPlayerLook);
            playerEntity.setDeltaMovement(playerEntity.getDeltaMovement().add(multipliedReversedPlayerLook));
            logger.debug("Added Movement to player successfully!");
            /*
            playerEntity.move(MoverType.PLAYER, new Vector3d(0,5,0));
            logger.debug("Moved!");

             */
            return ActionResult.consume(playerEntity.getMainHandItem());
        }
    }
}
