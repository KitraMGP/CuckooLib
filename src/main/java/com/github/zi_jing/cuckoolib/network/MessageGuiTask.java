package com.github.zi_jing.cuckoolib.network;

import java.util.function.Supplier;

import com.github.zi_jing.cuckoolib.gui.ModularContainer;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageGuiTask implements IMessage {
	private int window;
	private int[] tasks;

	public MessageGuiTask() {

	}

	public MessageGuiTask(int window, int[] tasks) {
		this.window = window;
		this.tasks = tasks;
	}

	public static MessageGuiTask decode(PacketBuffer buf) {
		MessageGuiTask msg = new MessageGuiTask();
		msg.window = buf.readInt();
		int length = buf.readInt();
		int[] tasks = new int[length];
		for (int i = 0; i < length; i++) {
			tasks[i] = buf.readInt();
		}
		msg.tasks = tasks;
		return msg;
	}

	@Override
	public void encode(PacketBuffer buf) {
		buf.writeInt(this.window);
		buf.writeInt(this.tasks.length);
		for (int id : this.tasks) {
			buf.writeInt(id);
		}
	}

	@Override
	public void process(Supplier<Context> ctx) {
		ctx.get().enqueueWork(() -> {
			PlayerEntity player = Minecraft.getInstance().player;
			Container container;
			if (player != null) {
				if (this.window == 0) {
					container = player.containerMenu;
				} else if (this.window == player.containerMenu.containerId) {
					container = player.containerMenu;
				} else {
					return;
				}
				if (container instanceof ModularContainer) {
					for (int id : this.tasks) {
						((ModularContainer) container).getGuiHolder().executeTask((ModularContainer) container, id);
					}
				}
			}
		});
	}
}