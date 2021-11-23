package com.github.zi_jing.cuckoolib.gui.widget;

import com.github.zi_jing.cuckoolib.client.render.IWidgetRenderer;
import com.github.zi_jing.cuckoolib.gui.ModularContainer;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RendererWidget extends WidgetBase {
	protected IContainerRenderer containerRenderer;

	public RendererWidget(int x, int y, int width, int height, IWidgetRenderer renderer) {
		this(x, y, width, height, (container, transform, px, py, pw, ph) -> renderer.draw(transform, px, py, pw, ph));
	}

	public RendererWidget(int x, int y, int width, int height, IContainerRenderer containerRenderer) {
		super(x, y, width, height);
		this.containerRenderer = containerRenderer;
	}

	@Override
	public WidgetBase setRenderer(IWidgetRenderer renderer) {
		throw new UnsupportedOperationException();
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void drawInBackground(MatrixStack transform, float partialTicks, int mouseX, int mouseY, int guiLeft, int guiTop) {
		this.containerRenderer.draw(this.guiInfo.getContainer(), transform, guiLeft + this.position.getX(), guiTop + this.position.getY(), this.size.getX(), this.size.getY());
	}

	@FunctionalInterface
	public interface IContainerRenderer {
		void draw(ModularContainer container, MatrixStack transform, int x, int y, int width, int height);
	}
}