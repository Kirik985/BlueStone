package com.kirik.blue_stone.content.blocks.gates;

public class NewAndGate extends BluestoneGate {
    public NewAndGate() {
        super((left,right) -> left && right);
    }
}
