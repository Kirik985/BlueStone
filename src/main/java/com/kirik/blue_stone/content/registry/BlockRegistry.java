package com.kirik.blue_stone.content.registry;

import com.kirik.blue_stone.content.blocks.gates.BluestoneGate;
import com.kirik.blue_stone.content.blocks.gates.NewAndGate;
import com.kirik.blue_stone.content.blocks.gates.OrGate;
import com.kirik.blue_stone.content.blocks.other.BlankBlock;
import com.kirik.blue_stone.content.blocks.wires.WireDoubleBlock;
import com.kirik.blue_stone.content.blocks.wires.WireMonoBlock;
import com.kirik.blue_stone.core.Bluestone;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLUESTONE_BLOCK_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, Bluestone.MOD_ID);

    public static final RegistryObject<Block> WIRE_MONO_BLOCK = BLUESTONE_BLOCK_REGISTER.register("wire_mono", WireMonoBlock::new);
    public static final RegistryObject<Block> BASE_BLOCK = BLUESTONE_BLOCK_REGISTER.register("base_block", BlankBlock::new);
    public static final RegistryObject<Block> WIRE_DOUBLE_BLOCK = BLUESTONE_BLOCK_REGISTER.register("wire_double", WireDoubleBlock::new);
    public static final RegistryObject<Block> AND_GATE_BLOCK = BLUESTONE_BLOCK_REGISTER.register("and_gate", NewAndGate::new);
    public static final RegistryObject<Block> OR_GATE_BLOCK = BLUESTONE_BLOCK_REGISTER.register("or_gate", OrGate::new);
}
