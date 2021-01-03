package com.github.zi_jing.cuckoolib.gui.widget;

import java.util.function.Consumer;

import com.github.zi_jing.cuckoolib.util.data.ButtonClickData;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundEvents;

public class ButtonWidget extends WidgetBase {
	protected Consumer<ButtonClickData> callback;

	public ButtonWidget(int x, int y, int width, int height, Consumer<ButtonClickData> callback) {
		super(x, y, width, height);
		this.callback = callback;
	}

	@Override
	public boolean onMouseClicked(double mouseX, double mouseY, int button) {
		ButtonClickData data = new ButtonClickData((int) mouseX - this.position.getX(),
				(int) mouseY - this.position.getY(), button);
		this.writeToServer(this.id, (buf) -> {
			data.writeToBuf(buf);
		});
		Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
		return true;
	}

	@Override
	public void receiveMessageFromClient(PacketBuffer data) {
		this.callback.accept(ButtonClickData.readFromBuf(data));
	}
}