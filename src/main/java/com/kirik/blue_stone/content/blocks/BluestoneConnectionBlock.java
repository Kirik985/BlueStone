package com.kirik.blue_stone.content.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.state.DirectionProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BluestoneConnectionBlock extends BluestoneBlockBase {
    public abstract boolean isConnectedTo(BlockState state, Direction direction);
    public abstract boolean isConnectedTo(BlockState state, BlockPos pos1, BlockPos pos2);
    public void updateNeighbors(BlockState state, World world, BlockPos pos, DirectionProperty property) {
        world.updateNeighborsAt(pos, state.getBlock());
        world.updateNeighborsAt(pos.relative(state.getValue(property)), world.getBlockState(pos.relative(state.getValue(property))).getBlock());
    }
    public void updateNeighbor(BlockState state, World world, BlockPos pos, DirectionProperty property) {
        world.updateNeighborsAt(pos.relative(state.getValue(property)), state.getBlock());
    }
}
