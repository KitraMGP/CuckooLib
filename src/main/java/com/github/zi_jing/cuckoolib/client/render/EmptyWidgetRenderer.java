package com.github.zi_jing.cuckoolib.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;

public class EmptyWidgetRenderer implements IWidgetRenderer {
	public static final EmptyWidgetRenderer INSTANCE = new EmptyWidgetRenderer();

	@Override
	public void draw(MatrixStack transform, int x, int y, int width, int height) {

	}
}