package com.github.zi_jing.cuckoolib.network;

import java.util.ArrayList;
import java.util.List;

import com.github.zi_jing.cuckoolib.CuckooLib;
import com.github.zi_jing.cuckoolib.gui.ModularGuiInfo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageModularGuiOpen implements IMessage {
	private PacketBuffer holderPacket;
	private List<PacketBuffer> updateData;
	private int window;

	public MessageModularGuiOpen() {

	}

	public MessageModularGuiOpen(PacketBuffer holderPacket, List<PacketBuffer> updateData, int window) {
		this.holderPacket = holderPacket;
		this.updateData = updateData;
		this.window = window;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.window = buf.readInt();
		this.holderPacket = new PacketBuffer(Unpooled.copiedBuffer(buf.readBytes(buf.readInt())));
		int size = buf.readInt();
		this.updateData = new ArrayList<PacketBuffer>();
		for (int i = 0; i < size; i++) {
			this.updateData.add(new PacketBuffer(Unpooled.copiedBuffer(buf.readBytes(buf.readInt()))));
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.window);
		buf.writeInt(this.holderPacket.readableBytes());
		buf.writeBytes(this.holderPacket);
		buf.writeInt(this.updateData.size());
		this.updateData.forEach((data) -> {
			buf.writeInt(data.readableBytes());
			buf.writeBytes(data);
		});
	}

	public static class Handler implements IMessageHandler<MessageModularGuiOpen, IMessage> {
		@Override
		public IMessage onMessage(MessageModularGuiOpen msg, MessageContext ctx) {
			CuckooLib.proxy.getThreadListener(ctx).addScheduledTask(() -> {
				ResourceLocation codecName = msg.holderPacket.readResourceLocation();
				if (ModularGuiInfo.REGISTRY.containsKey(codecName)) {
					ModularGuiInfo.openClientModularGui(msg.window,
							ModularGuiInfo.REGISTRY.getObject(codecName).readHolder(msg.holderPacket), msg.updateData);
				} else {
					throw new RuntimeException("The gui holder registry name is invalid");
				}
			});
			return null;
		}
	}
}