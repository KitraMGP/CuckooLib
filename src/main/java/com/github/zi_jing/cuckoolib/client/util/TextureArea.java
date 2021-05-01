package com.github.zi_jing.cuckoolib.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TextureArea {
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

    public void draw(int x, int y, int width, int height) {
        Minecraft.getMinecraft().renderEngine.bindTexture(this.location);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(x, y + height, 0).tex(this.x, this.y + this.height).endVertex();
        bufferbuilder.pos(x + width, y + height, 0).tex(this.x + this.width, this.y + this.height).endVertex();
        bufferbuilder.pos(x + width, y, 0).tex(this.x + this.width, this.y).endVertex();
        bufferbuilder.pos(x, y, 0).tex(this.x, this.y).endVertex();
        tessellator.draw();
    }
}