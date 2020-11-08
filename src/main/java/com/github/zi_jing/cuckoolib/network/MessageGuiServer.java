package com.github.zi_jing.cuckoolib.network;

import com.github.zi_jing.cuckoolib.CuckooLib;
import com.github.zi_jing.cuckoolib.gui.ModularContainer;
import com.github.zi_jing.cuckoolib.gui.widget.IWidget;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageGuiServer implements IMessage {
	private PacketBuffer data;
	private int window;

	public MessageGuiServer() {

	}

	public MessageGuiServer(PacketBuffer data, int window) {
		this.data = data;
		this.window = window;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.window = buf.readInt();
		this.data = new PacketBuffer(Unpooled.copiedBuffer((buf.readBytes(buf.readInt()))));
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.window);
		buf.writeInt(this.data.readableBytes());
		buf.writeBytes(this.data);
	}

	public static class Handler implements IMessageHandler<MessageGuiServer, IMessage> {
		@Override
		public IMessage onMessage(MessageGuiServer msg, MessageContext ctx) {
			CuckooLib.proxy.getThreadListener(ctx).addScheduledTask(() -> {
				EntityPlayer player = CuckooLib.proxy.getPlayer(ctx);
				Container container;
				if (player != null) {
					if (msg.window == 0) {
						container = player.inventoryContainer;
					} else if (msg.window == player.openContainer.windowId) {
						container = player.openContainer;
					} else {
						return;
					}
					if (container instanceof ModularContainer) {
						IWidget widget = ((ModularContainer) container).getGuiInfo().getWidget(msg.data.readInt());
						widget.receiveMessageFromServer(msg.data);
					}
				}
			});
			return null;
		}
	}
}