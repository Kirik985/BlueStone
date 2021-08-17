package com.kirik.blue_stone.content.blocks;

import com.kirik.blue_stone.core.Bluestone;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraftforge.common.ToolType;

public abstract class BluestoneBlockBase extends Block {
    public static final DirectionProperty HORIZONTAL_FACING_TWO_SIDED = DirectionProperty.create("facing", Direction.EAST, Direction.NORTH);
    public static final DirectionProperty HORIZONTAL_FACING_FOUR_SIDED = BlockStateProperties.HORIZONTAL_FACING;
    public static final DirectionProperty ALL_SIDES_FACING = BlockStateProperties.FACING;
    public BluestoneBlockBase() {
        super(Properties.of(Bluestone.BluestoneMaterial).harvestTool(ToolType.PICKAXE).harvestLevel(0).strength(0.5F));
    }
    public Direction toTwoSided(Direction d) {
        switch (d) {
            case EAST:
            case WEST:
                return Direction.EAST;
            case SOUTH:
            case NORTH:
                return Direction.NORTH;
            default:
                return null;
        }
    }
}
