package com.kirik.blue_stone.content.blocks.gates;

import com.kirik.blue_stone.content.blocks.BluestoneConnectionBlock;
import com.kirik.blue_stone.content.blocks.groups.IGate;
import com.kirik.blue_stone.content.blocks.groups.IWire;
import com.kirik.blue_stone.content.blocks.wires.WireDoubleBlock;
import com.kirik.blue_stone.core.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.function.BiFunction;

public class BluestoneGate extends BluestoneGateBase implements IGate {
    //public static final BooleanProperty GATE_ON = BooleanProperty.create("gate_on");
    public static final BooleanProperty LEFT_ON = BooleanProperty.create("left_on");
    public static final BooleanProperty RIGHT_ON = BooleanProperty.create("right_on");
    public final BiFunction<Boolean, Boolean, Boolean> condition;

    public BluestoneGate(BiFunction<Boolean, Boolean, Boolean> condition) {
        this.condition = condition;
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LEFT_ON, Boolean.FALSE).setValue(RIGHT_ON, Boolean.FALSE));
    }

    @Override
    public boolean isConnectedTo(BlockState state, Direction direction) {
        return state.getValue(FACING).equals(direction);
    }

    @Override
    public boolean isConnectedTo(BlockState state, BlockPos pos1, BlockPos pos2) {
        return isConnectedTo(state, Util.directionBetweenBlocks(pos1, pos2));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACING, LEFT_ON, RIGHT_ON);
    }

    @Override
    public void neighborChanged(BlockState p_220069_1_, World p_220069_2_, BlockPos p_220069_3_, Block p_220069_4_, BlockPos p_220069_5_, boolean p_220069_6_) {
        updatePower(p_220069_1_, p_220069_2_, p_220069_3_);
    }

    @Override
    public boolean isON(BlockState state) {
        boolean left = state.getValue(LEFT_ON);
        boolean right = state.getValue(RIGHT_ON);
        return condition.apply(left, right);
    }

    public void updatePower(BlockState state, World world, BlockPos pos) {
        if (!world.isClientSide()) {
            BlockState blockBehind = world.getBlockState(pos.relative(state.getValue(FACING).getOpposite()));
            if (blockBehind.getBlock() instanceof IWire) {
                if (((IWire) blockBehind.getBlock()).isDouble()) {
                    if (((BluestoneConnectionBlock) blockBehind.getBlock()).isConnectedTo(blockBehind, pos.relative(state.getValue(FACING).getOpposite()), pos)) {
                        boolean left = blockBehind.getValue(WireDoubleBlock.LEFT_ON);
                        boolean right = blockBehind.getValue(WireDoubleBlock.RIGHT_ON);
                        if (left && !right) {
                            world.setBlock(pos, state.setValue(LEFT_ON, Boolean.TRUE).setValue(RIGHT_ON, Boolean.FALSE), 2);
                        } else if (!left && right) {
                            world.setBlock(pos, state.setValue(LEFT_ON, Boolean.FALSE).setValue(RIGHT_ON, Boolean.TRUE), 2);
                        } else if (left && right) {
                            world.setBlock(pos, state.setValue(LEFT_ON, Boolean.TRUE).setValue(RIGHT_ON, Boolean.TRUE), 2);
                        } else {
                            world.setBlock(pos, state.setValue(LEFT_ON, Boolean.FALSE).setValue(RIGHT_ON, Boolean.FALSE), 2);
                        }
                    }
                }
            }
            else {
                world.setBlock(pos, state.setValue(LEFT_ON, Boolean.FALSE).setValue(RIGHT_ON, Boolean.FALSE), 2);
            }
        }
    }
}