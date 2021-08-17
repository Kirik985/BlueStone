package com.kirik.blue_stone.content.blocks.wires;

import com.kirik.blue_stone.content.blocks.BluestoneConnectionBlock;
import com.kirik.blue_stone.content.blocks.gates.BluestoneGateBase;
import com.kirik.blue_stone.content.blocks.groups.IGate;
import com.kirik.blue_stone.content.blocks.groups.IWire;
import com.kirik.blue_stone.core.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.crash.ReportedException;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class WireMonoBlock extends BluestoneConnectionBlock {
    public static final DirectionProperty FACING = HORIZONTAL_FACING_TWO_SIDED;
    public static final BooleanProperty POWERED = BooleanProperty.create("powered");
    public WireMonoBlock() {
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POWERED, Boolean.FALSE));
    }

    @Override
    public void neighborChanged(BlockState p_220069_1_, World p_220069_2_, BlockPos p_220069_3_, Block p_220069_4_, BlockPos p_220069_5_, boolean p_220069_6_) {
        System.err.println(p_220069_3_);
        System.err.println(p_220069_5_);
        if (p_220069_3_.equals(p_220069_5_)) {
            System.err.println("Block updated itself, lol XD (" + p_220069_3_.toShortString() + ")");
            return;
        }
        update(p_220069_1_, p_220069_2_, p_220069_3_);
    }

    @Override
    public boolean isConnectedTo(BlockState state,Direction direction) {
        return state.getValue(FACING).equals(direction) || state.getValue(FACING).getOpposite().equals(direction);
    }

    @Override
    public boolean isConnectedTo(BlockState state, BlockPos pos1, BlockPos pos2) {
        return isConnectedTo(state, Util.directionBetweenBlocks(pos1, pos2));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(FACING, toTwoSided(context.getHorizontalDirection()));
    }
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACING, POWERED);
    }
    public void update(BlockState state, World world, BlockPos pos) {
        if (!world.isClientSide()) {
            BlockState blockBehind = world.getBlockState(pos.relative(state.getValue(FACING).getOpposite()));
            if (blockBehind.getBlock() instanceof BluestoneConnectionBlock) {
                if (((BluestoneConnectionBlock)blockBehind.getBlock()).isConnectedTo(blockBehind, pos.relative(state.getValue(FACING).getOpposite()), pos)) {
                    if (blockBehind.getBlock() instanceof IWire) {
                        if (!((IWire) blockBehind.getBlock()).isDouble()) {
                            setPowered(world, pos, state, blockBehind.getValue(POWERED));
                        }
                    } else if (blockBehind.getBlock() instanceof IGate) {
                        boolean gate_on = ((BluestoneGateBase)blockBehind.getBlock()).isON(blockBehind);
                        setPowered(world, pos, state, gate_on);
                    }
                }
            }
            else {
                setPowered(world, pos, state, false);
            }
        }
    }
    private void setPowered(World world, BlockPos pos, BlockState state, boolean powered) {
        world.setBlock(pos, state.setValue(POWERED, powered), 2);
        try {
            updateNeighbors(state, world, pos, FACING);
        } catch (ReportedException | StackOverflowError throwable) {
            System.err.println("Caught Exception during updating block at " + pos.toShortString());
        }

    }

    @Override
    public void onPlace(BlockState p_220082_1_, World p_220082_2_, BlockPos p_220082_3_, BlockState p_220082_4_, boolean p_220082_5_) {
        this.update(p_220082_1_, p_220082_2_, p_220082_3_);
    }
}