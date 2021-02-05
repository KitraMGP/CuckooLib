package com.github.zi_jing.cuckoolib.network;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.github.zi_jing.cuckoolib.gui.IModularGuiHolder;
import com.github.zi_jing.cuckoolib.gui.ModularGuiInfo;

import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageModularGuiOpen implements IMessage {
	private PacketBuffer holderPacket;
	private PacketBuffer[] parentHolderPackets;
	private List<PacketBuffer> updateData;
	private int window;

	public MessageModularGuiOpen() {

	}

	public MessageModularGuiOpen(PacketBuffer holderPacket, PacketBuffer[] parentHolderPacket,
			List<PacketBuffer> updateData, int window) {
		this.holderPacket = holderPacket;
		this.parentHolderPackets = parentHolderPacket;
		this.updateData = updateData;
		this.window = window;
	}

	public static MessageModularGuiOpen decode(PacketBuffer buf) {
		MessageModularGuiOpen msg = new MessageModularGuiOpen();
		msg.holderPacket = new PacketBuffer(Unpooled.copiedBuffer(buf.readBytes(buf.readInt())));
		int size = buf.readInt();
		msg.parentHolderPackets = new PacketBuffer[size];
		for (int i = 0; i < size; i++) {
			msg.parentHolderPackets[i] = new PacketBuffer(Unpooled.copiedBuffer(buf.readBytes(buf.readInt())));
		}
		size = buf.readInt();
		msg.updateData = new ArrayList<PacketBuffer>();
		for (int i = 0; i < size; i++) {
			msg.updateData.add(new PacketBuffer(Unpooled.copiedBuffer(buf.readBytes(buf.readInt()))));
		}
		msg.window = buf.readInt();
		return msg;
	}

	@Override
	public void encode(PacketBuffer buf) {
		buf.writeInt(this.holderPacket.readableBytes());
		buf.writeBytes(this.holderPacket);
		buf.writeInt(this.parentHolderPackets.length);
		for (int i = 0; i < this.parentHolderPackets.length; i++) {
			buf.writeInt(this.parentHolderPackets[i].readableBytes());
			buf.writeBytes(this.parentHolderPackets[i]);
		}
		buf.writeInt(this.updateData.size());
		this.updateData.forEach((data) -> {
			buf.writeInt(data.readableBytes());
			buf.writeBytes(data);
		});
		buf.writeInt(this.window);
	}

	@Override
	public void process(Supplier<Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ResourceLocation codecName = this.holderPacket.readResourceLocation();
			if (!ModularGuiInfo.REGISTRY.containsKey(codecName)) {
				throw new RuntimeException("The gui holder registry name is invalid");
			}
			IModularGuiHolder[] parentHolders = new IModularGuiHolder[this.parentHolderPackets.length];
			for (int i = 0; i < parentHolders.length; i++) {
				ResourceLocation parentCodecName = this.parentHolderPackets[i].readResourceLocation();
				if (!ModularGuiInfo.REGISTRY.containsKey(parentCodecName)) {
					throw new RuntimeException("The gui holder registry name is invalid");
				}
				parentHolders[i] = ModularGuiInfo.REGISTRY.getValue(parentCodecName)
						.readHolder(this.parentHolderPackets[i]);
			}
			ModularGuiInfo.openClientModularGui(this.window,
					ModularGuiInfo.REGISTRY.getValue(codecName).readHolder(this.holderPacket), parentHolders,
					this.updateData);
		});
	}
}