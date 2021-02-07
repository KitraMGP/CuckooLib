package com.github.zi_jing.cuckoolib.client.render;

import com.github.zi_jing.cuckoolib.CuckooLib;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.util.ResourceLocation;

public class TextureArea implements IWidgetRenderer {
	public static final TextureArea DEFAULT_BACKGROUND = createFullTexture(
			new ResourceLocation(CuckooLib.MODID, "textures/gui/default_background.png"));

	private ResourceLocation location;
	private float x, y, width, height;

	public TextureArea(ResourceLocation location, float x, float y, float width, float height) {
		this.location = location;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public static TextureArea createFullTexture(ResourceLocation location) {
		return createTexture(location, 0, 0, 1, 1);
	}

	public static TextureArea createTexture(ResourceLocation location, float x, float y, float width, float height) {
		return new TextureArea(location, x, y, width, height);
	}

	public static TextureArea createSubTexture(TextureArea area, float x, float y, float width, float height) {
		return new TextureArea(area.location, area.x + x * area.width, area.y + y * area.height, width * area.width,
				height * area.height);
	}

	@Override
	public void draw(MatrixStack transform, int x, int y, int width, int height) {
		GLUtil.bindTexture(this.location);
		GLUtil.drawScaledTexturedRect(transform, x, y, width, height, this.x, this.y, this.width, this.height);
	}

	public void draw(MatrixStack transform, int x, int y, int width, int height, int alpha) {
		GLUtil.bindTexture(this.location);
		GLUtil.drawScaledTexturedRect(transform, x, y, width, height, this.x, this.y, this.width, this.height, alpha);
	}

	public void drawSubArea(MatrixStack transform, int x, int y, int width, int height, float subX, float subY,
			float subWidth, float subHeight) {
		GLUtil.bindTexture(this.location);
		GLUtil.drawScaledTexturedRect(transform, x, y, width, height, this.x + x * this.width, this.y + y * this.height,
				width * this.width, height * this.height);
	}

	public void drawSubArea(MatrixStack transform, int x, int y, int width, int height, float subX, float subY,
			float subWidth, float subHeight, int alpha) {
		GLUtil.bindTexture(this.location);
		GLUtil.drawScaledTexturedRect(transform, x, y, width, height, this.x + x * this.width, this.y + y * this.height,
				width * this.width, height * this.height, alpha);
	}
}