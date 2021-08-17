package com.kirik.blue_stone.content.blocks.wires;

import com.kirik.blue_stone.content.blocks.BluestoneConnectionBlock;
import com.kirik.blue_stone.content.blocks.groups.IWire;
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

import javax.annotation.Nullable;

public class WireDoubleBlock extends BluestoneConnectionBlock implements IWire {
    public static final DirectionProperty FACING = HORIZONTAL_FACING_TWO_SIDED;
    public static final BooleanProperty LEFT_ON = BooleanProperty.create("left_on");
    public static final BooleanProperty RIGHT_ON = BooleanProperty.create("right_on");
    public WireDoubleBlock() {
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LEFT_ON, Boolean.FALSE).setValue(RIGHT_ON, Boolean.FALSE));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(FACING, toTwoSided(context.getHorizontalDirection()));
    }
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACING, LEFT_ON, RIGHT_ON);
    }
    @Override
    public ActionResultType use(BlockState blockState, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult rayTraceResult) {
        if (world.isClientSide) {
            return ActionResultType.SUCCESS;
        } else {
            boolean left = world.getBlockState(pos).getValue(LEFT_ON);
            boolean right = world.getBlockState(pos).getValue(RIGHT_ON);
            if (left && !right) {
                world.setBlock(pos, blockState.setValue(LEFT_ON, Boolean.FALSE).setValue(RIGHT_ON, Boolean.TRUE),3);
            } else if (!left && right) {
                world.setBlock(pos, blockState.setValue(LEFT_ON, Boolean.TRUE).setValue(RIGHT_ON, Boolean.TRUE), 3);
            } else if (left) {
                world.setBlock(pos, blockState.setValue(LEFT_ON, Boolean.FALSE).setValue(RIGHT_ON, Boolean.FALSE), 3);
            } else {
                world.setBlock(pos, blockState.setValue(LEFT_ON, Boolean.TRUE).setValue(RIGHT_ON, Boolean.FALSE), 3);
            }
            updateNeighbors(blockState, world, pos, FACING);
            return ActionResultType.CONSUME;
        }
    }

    @Override
    public boolean isConnectedTo(BlockState state, Direction direction) {
        return state.getValue(FACING).equals(direction) || state.getValue(FACING).getOpposite().equals(direction);
    }

    @Override
    public boolean isConnectedTo(BlockState state, BlockPos pos1, BlockPos pos2) {
        return isConnectedTo(state, Util.directionBetweenBlocks(pos1, pos2));
    }

    @Override
    public boolean isDouble() {
        return true;
    }
}
