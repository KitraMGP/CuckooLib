package com.github.zi_jing.cuckoolib.util.math;

public class Vector2i {
	public static final Vector2i ORIGIN = new Vector2i(0, 0);

	private int x;
	private int y;

	public Vector2i(int width, int height) {
		this.x = width;
		this.y = height;
	}

	public static Vector2i of(int x, int y) {
		return new Vector2i(x, y);
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public Vector2i plus(Vector2i vec) {
		return new Vector2i(this.x + vec.x, this.y + vec.y);
	}

	public Vector2i minus(Vector2i vec) {
		return new Vector2i(this.x - vec.x, this.y - vec.y);
	}

	public Vector2i multiply(int scale) {
		return new Vector2i(this.x * scale, this.y * scale);
	}

	public int scalarMultiply(Vector2i vec) {
		return this.x * vec.x + this.y * vec.y;
	}

	public Vector2i scaleToSize(int width, int height) {
		float a = (float) width / (float) this.x;
		float b = (float) height / (float) this.y;
		float w, h;
		w = this.x * a;
		h = this.y * a;
		if (w > width || h > height) {
			w = this.x * b;
			h = this.y * b;
		}
		this.x = (int) w;
		this.y = (int) h;
		return this;
	}
}