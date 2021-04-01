package com.github.zi_jing.cuckoolib.gui.widget;

import java.util.function.BiConsumer;

import com.github.zi_jing.cuckoolib.gui.ModularContainer;
import com.github.zi_jing.cuckoolib.util.data.ButtonClickData;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundEvents;

public class ButtonWidget extends WidgetBase {
	protected int count;
	protected BiConsumer<ButtonClickData, ModularContainer> callback, callbackClient;

	public ButtonWidget(int count, int x, int y, int width, int height,
			BiConsumer<ButtonClickData, ModularContainer> callback) {
		this(count, x, y, width, height, callback, null);
	}

	public ButtonWidget(int count, int x, int y, int width, int height,
			BiConsumer<ButtonClickData, ModularContainer> callback,
			BiConsumer<ButtonClickData, ModularContainer> callbackClient) {
		super(x, y, width, height);
		this.count = count;
		this.callback = callback;
		this.callbackClient = callbackClient;
	}

	@Override
	public boolean onMouseClicked(double mouseX, double mouseY, int button) {
		ButtonClickData data = new ButtonClickData(this.count, (int) mouseX - this.position.getX(),
				(int) mouseY - this.position.getY(), button);
		if (this.callbackClient != null) {
			this.callbackClient.accept(data, this.guiInfo.getContainer());
		}
		this.writePacketToServer(this.id, (buf) -> {
			data.writeToBuf(buf);
		});
		Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
		return true;
	}

	@Override
	public void receiveMessageFromClient(PacketBuffer data) {
		this.callback.accept(ButtonClickData.readFromBuf(data), this.guiInfo.getContainer());
	}
}