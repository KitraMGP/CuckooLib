package com.github.zi_jing.cuckoolib.network;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public interface IMessage {
	void encode(PacketBuffer buf);

	void process(Supplier<Context> ctx);
}