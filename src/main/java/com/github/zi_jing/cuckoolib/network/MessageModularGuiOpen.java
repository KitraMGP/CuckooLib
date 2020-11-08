package com.github.zi_jing.cuckoolib.network;

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
	private ResourceLocation registryName;
	private List<PacketBuffer> updateData;
	private int window;

	public MessageModularGuiOpen() {

	}

	public MessageModularGuiOpen(ResourceLocation registryName, List<PacketBuffer> updateData, int window) {
		this.registryName = registryName;
		this.updateData = updateData;
		this.window = window;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.window = buf.readInt();
		this.registryName = new PacketBuffer(buf.readBytes(buf.readInt())).readResourceLocation();
		int size = buf.readInt();
		for (int i = 0; i < size; i++) {
			this.updateData.add(new PacketBuffer(buf.readBytes(buf.readInt())));
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.window);
		PacketBuffer name = new PacketBuffer(Unpooled.buffer()).writeResourceLocation(this.registryName);
		buf.writeInt(name.readableBytes());
		buf.writeBytes(name);
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
				if (ModularGuiInfo.REGISTRY.containsKey(msg.registryName)) {
					ModularGuiInfo.openClientModularGui(msg.window, ModularGuiInfo.REGISTRY.getObject(msg.registryName),
							msg.updateData);
				} else {
					throw new RuntimeException("The gui holder registry name is invalid");
				}
			});
			return null;
		}
	}
}