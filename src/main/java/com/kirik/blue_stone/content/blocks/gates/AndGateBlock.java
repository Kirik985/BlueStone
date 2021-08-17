package com.kirik.blue_stone.content.blocks.gates;

import com.kirik.blue_stone.content.blocks.BluestoneBlockBase;
import com.kirik.blue_stone.content.blocks.BluestoneConnectionBlock;
import com.kirik.blue_stone.content.blocks.groups.IWire;
import com.kirik.blue_stone.content.blocks.wires.WireDoubleBlock;
import com.kirik.blue_stone.core.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.lwjgl.system.CallbackI;

import javax.annotation.Nullable;
import java.util.Random;

public class AndGateBlock extends BluestoneGateBase {
    public static final BooleanProperty LEFT_ON = BooleanProperty.create("left_on");
    public static final BooleanProperty RIGHT_ON = BooleanProperty.create("right_on");
    public static final BooleanProperty GATE_ON = BooleanProperty.create("gate_on");
    public AndGateBlock() {
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LEFT_ON, Boolean.FALSE).setValue(RIGHT_ON, Boolean.FALSE).setValue(GATE_ON, Boolean.FALSE));
    }
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACING, LEFT_ON, RIGHT_ON, GATE_ON);
    }

    @Override
    public void neighborChanged(BlockState p_220069_1_, World p_220069_2_, BlockPos p_220069_3_, Block p_220069_4_, BlockPos p_220069_5_, boolean p_220069_6_) {
        updatePower(p_220069_1_,p_220069_2_,p_220069_3_);
    }

    @Override
    public boolean isConnectedTo(BlockState state, Direction direction) {
        return state.getValue(FACING).equals(direction);
    }

    @Override
    public boolean isConnectedTo(BlockState state, BlockPos pos1, BlockPos pos2) {
        return isConnectedTo(state, Util.directionBetweenBlocks(pos1, pos2));
    }

    public void updateState(BlockState state, World world, BlockPos pos) {
        if (!world.isClientSide()) {
            boolean gate_on = state.getValue(GATE_ON);
            boolean left = state.getValue(LEFT_ON);
            boolean right = state.getValue(RIGHT_ON);
            if (left && right && !gate_on) {
                world.setBlock(pos, state.setValue(GATE_ON, Boolean.TRUE), 2);
                updateNeighbors(state, world, pos, FACING);
            } else if ((!left || !right) && gate_on) {
                world.setBlock(pos, state.setValue(GATE_ON, Boolean.FALSE), 2);
                updateNeighbors(state, world, pos, FACING);
            }
        }
    }
    public void updatePower(BlockState state, World world, BlockPos pos) {
        if (!world.isClientSide()) {
            BlockState blockBehind = world.getBlockState(pos.relative(state.getValue(FACING).getOpposite()));
            if (blockBehind.getBlock() instanceof IWire) {
                if (((IWire) blockBehind.getBlock()).isDouble()) {
                    if (((BluestoneConnectionBlock) blockBehind.getBlock()).isConnectedTo(blockBehind, pos.relative(state.getValue(FACING).getOpposite()), pos)) {
                        if (blockBehind.getValue(WireDoubleBlock.LEFT_ON)) {
                            world.setBlock(pos, state.setValue(LEFT_ON, Boolean.TRUE).setValue(RIGHT_ON, Boolean.FALSE), 2);
                        } else if (blockBehind.getValue(WireDoubleBlock.RIGHT_ON)) {
                            world.setBlock(pos, state.setValue(RIGHT_ON, Boolean.TRUE).setValue(LEFT_ON, Boolean.FALSE), 2);
                        } else if (blockBehind.getValue(WireDoubleBlock.LEFT_ON) && blockBehind.getValue(WireDoubleBlock.RIGHT_ON)) {
                            world.setBlock(pos, state.setValue(LEFT_ON, Boolean.TRUE).setValue(RIGHT_ON, Boolean.TRUE), 2);
                        } else if (!blockBehind.getValue(WireDoubleBlock.LEFT_ON) && !blockBehind.getValue(WireDoubleBlock.RIGHT_ON)) {
                            world.setBlock(pos, state.setValue(RIGHT_ON, Boolean.FALSE).setValue(LEFT_ON, Boolean.FALSE), 2);
                        }
                    }
                }
            } else {
                world.setBlock(pos, state.setValue(RIGHT_ON, Boolean.FALSE).setValue(LEFT_ON, Boolean.FALSE), 2);
            }
            updateState(state, world, pos);
        }
    }

    @Override
    public boolean isON(BlockState state) {
        return false;
    }
}
