package com.github.zi_jing.cuckoolib.network;

import java.util.function.Supplier;

import com.github.zi_jing.cuckoolib.gui.ModularContainer;
import com.github.zi_jing.cuckoolib.gui.widget.IWidget;

import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageGuiToClient implements IMessage {
	private PacketBuffer data;
	private int window;

	public MessageGuiToClient() {

	}

	public MessageGuiToClient(PacketBuffer data, int window) {
		this.data = data;
		this.window = window;
	}

	public static MessageGuiToClient decode(PacketBuffer buf) {
		return new MessageGuiToClient(new PacketBuffer(Unpooled.copiedBuffer((buf.readBytes(buf.readInt())))),
				buf.readInt());
	}

	@Override
	public void encode(PacketBuffer buf) {
		buf.writeInt(this.data.readableBytes());
		buf.writeBytes(this.data);
		buf.writeInt(this.window);
	}

	@Override
	public void process(Supplier<Context> ctx) {
		ctx.get().enqueueWork(() -> {
			PlayerEntity player = Minecraft.getInstance().player;
			Container container;
			if (player != null) {
				if (this.window == 0) {
					container = player.container;
				} else if (this.window == player.openContainer.windowId) {
					container = player.openContainer;
				} else {
					return;
				}
				if (container instanceof ModularContainer) {
					IWidget widget = ((ModularContainer) container).getGuiInfo().getWidget(this.data.readInt());
					widget.receiveMessageFromServer(this.data);
				}
			}
		});
	}
}