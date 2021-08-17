package com.kirik.blue_stone.content.blocks.gates;

public class OrGate extends BluestoneGate {
    public OrGate() {
        super((left, right) -> left || right);
    }
}
