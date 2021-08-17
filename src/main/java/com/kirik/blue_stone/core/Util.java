package com.kirik.blue_stone.core;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.UUID;

public class Util {
    public static final UUID NIL_UUID = new UUID(0L, 0L);
    private Util() {}
    public static List<BlockPos> getNeighbors(BlockPos pos) {
        ArrayList<BlockPos> out = new ArrayList<>();
        out.add(pos.above());
        out.add(pos.below());
        out.add(pos.east());
        out.add(pos.west());
        out.add(pos.north());
        out.add(pos.south());
        return out;
    }
    public static Direction directionBetweenBlocks(BlockPos pos1, BlockPos pos2) {
        DifferentInPos different = checkPos(pos1,pos2);
        if (different.isXDifferent()) {
            return pos1.getX() > pos2.getX() ? Direction.WEST : Direction.EAST;
        } else if (different.isYDifferent()) {
            return pos1.getY() > pos2.getY() ? Direction.DOWN : Direction.UP;
        } else {
            return pos1.getZ() > pos2.getZ() ? Direction.NORTH : Direction.SOUTH;
        }
    }
    private static DifferentInPos checkPos(BlockPos pos1, BlockPos pos2) {
        boolean isXDifferent = pos1.getX() != pos2.getX();
        boolean isYDifferent = pos1.getY() != pos2.getY();
        boolean isZDifferent = pos1.getZ() != pos2.getZ();
        if ((isXDifferent && isYDifferent) || (isXDifferent && isZDifferent) || (isYDifferent && isZDifferent)) {
            throw new IllegalArgumentException("blocks pos is different!");
        } else {
            return new DifferentInPos(isXDifferent,isYDifferent,isZDifferent);
        }
    }
    private static class DifferentInPos {
        private final boolean isXDifferent;
        private final boolean isYDifferent;
        private final boolean isZDifferent;
        public DifferentInPos(boolean isXDifferent, boolean isYDifferent, boolean isZDifferent) {
            this.isXDifferent = isXDifferent;
            this.isYDifferent = isYDifferent;
            this.isZDifferent = isZDifferent;
        }
        public boolean isXDifferent() {
            return isXDifferent;
        }
        public boolean isYDifferent() {
            return isYDifferent;
        }
        public boolean isZDifferent() {
            return isZDifferent;
        }
    }
    public static void drawComponent(MatrixStack stack, ITextComponent component, float f1, float f2, int i) {
        Minecraft.getInstance().font.draw(stack, component, f1, f2, i);
    }
    public static void drawString(MatrixStack stack, String s, float f1, float f2, int i) {
        Util.drawComponent(stack, new StringTextComponent(s), f1, f2, i);
    }
    public static void drawShadowComponent(MatrixStack stack, ITextComponent component, float f1, float f2, int i) {
        Minecraft.getInstance().font.drawShadow(stack, component, f1, f2, i);
    }
    public static void drawShadowString(MatrixStack stack, String s, float f1, float f2, int i) {
        Util.drawShadowComponent(stack, new StringTextComponent(s), f1, f2, i);
    }
}
