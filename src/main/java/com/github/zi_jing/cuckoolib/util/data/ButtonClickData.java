package com.github.zi_jing.cuckoolib.util.data;

import io.netty.buffer.ByteBuf;

public class ButtonClickData {
	private int widgetId, x, y, button;

	public ButtonClickData(int widgetId, int x, int y, int button) {
		this.widgetId = widgetId;
		this.x = x;
		this.y = y;
		this.button = button;
	}

	public static ButtonClickData readFromBuf(ByteBuf buf) {
		return new ButtonClickData(buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt());
	}

	public void writeToBuf(ByteBuf buf) {
		buf.writeInt(this.widgetId);
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.button);
	}

	public int getWidgetId() {
		return this.widgetId;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getButton() {
		return this.button;
	}
}