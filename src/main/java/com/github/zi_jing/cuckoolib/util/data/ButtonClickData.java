package com.github.zi_jing.cuckoolib.util.data;

import io.netty.buffer.ByteBuf;

public class ButtonClickData {
	private int count, x, y, button;

	public ButtonClickData(int count, int x, int y, int button) {
		this.count = count;
		this.x = x;
		this.y = y;
		this.button = button;
	}

	public static ButtonClickData readFromBuf(ByteBuf buf) {
		return new ButtonClickData(buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt());
	}

	public void writeToBuf(ByteBuf buf) {
		buf.writeInt(this.count);
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.button);
	}

	public int getCount() {
		return this.count;
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