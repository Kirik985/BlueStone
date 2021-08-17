package com.kirik.blue_stone.content.blocks.gates;

import com.kirik.blue_stone.content.blocks.BluestoneConnectionBlock;
import net.minecraft.block.BlockState;
import net.minecraft.state.DirectionProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BluestoneGateBase extends BluestoneConnectionBlock {
    public static final DirectionProperty FACING = HORIZONTAL_FACING_FOUR_SIDED;
    public abstract boolean isON(BlockState state);
}
