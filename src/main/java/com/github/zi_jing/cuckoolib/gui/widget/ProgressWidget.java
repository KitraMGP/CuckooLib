package com.github.zi_jing.cuckoolib.gui.widget;

import java.util.function.Supplier;

import com.github.zi_jing.cuckoolib.client.render.IWidgetRenderer;
import com.github.zi_jing.cuckoolib.client.render.TextureArea;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ProgressWidget extends WidgetBase {
	protected TextureArea texture;
	protected Supplier<Float> supplier;
	protected float progress;
	protected MoveType moveType;

	public ProgressWidget(int x, int y, int width, int height, Supplier<Float> supplier, MoveType moveType) {
		super(x, y, width, height);
		this.supplier = supplier;
		this.moveType = moveType;
	}

	public WidgetBase setTexture(TextureArea texture) {
		this.texture = texture;
		return this;
	}

	@Override
	public WidgetBase setRenderer(IWidgetRenderer renderer) {
		throw new UnsupportedOperationException("Use #setTexture(texture)");
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void drawInBackground(MatrixStack transform, float partialTicks, int mouseX, int mouseY, int guiLeft,
			int guiTop) {
		if (this.texture == null) {
			return;
		}
		switch (this.moveType) {
		case HORIZONTAL:
			this.texture.drawSubArea(transform, guiLeft + this.position.getX(), guiTop + this.position.getY(),
					(int) (this.size.getX() * this.progress), this.size.getY(), 0, 0,
					((int) (this.size.getX() * this.progress)) / ((float) this.size.getX()), 1);
			break;
		case HORIZONTAL_INVERTED:
			this.texture.drawSubArea(transform,
					guiLeft + this.position.getX() + this.size.getX() - (int) (this.size.getX() * this.progress),
					guiTop + this.position.getY(), (int) (this.size.getX() * this.progress), this.size.getY(),
					1 - ((int) (this.size.getX() * this.progress)) / ((float) this.size.getX()), 0,
					((int) (this.size.getX() * this.progress)) / ((float) this.size.getX()), 1);
			break;
		case VERTICAL:
			this.texture.drawSubArea(transform, guiLeft + this.position.getX(), guiTop + this.position.getY(),
					this.size.getX(), (int) (this.size.getY() * this.progress), 0, 0, 1,
					((int) (this.size.getY() * this.progress)) / ((float) this.size.getY()));
			break;
		case VERTICAL_INVERTED:
			this.texture.drawSubArea(transform, guiLeft + this.position.getX(),
					guiTop + this.position.getY() + this.size.getY() - (int) (this.size.getY() * this.progress),
					this.size.getX(), (int) (this.size.getY() * this.progress), 0,
					1 - ((int) (this.size.getY() * this.progress)) / ((float) this.size.getY()), 1,
					((int) (this.size.getY() * this.progress)) / ((float) this.size.getY()));
			break;
		default:
			break;
		}
	}

	@Override
	public void detectAndSendChanges() {
		float update = MathHelper.clamp(this.supplier.get(), 0, 1);
		if (update != this.progress) {
			this.progress = update;
			this.writePacketToClient(this.id, (buf) -> buf.writeFloat(update));
		}
	}

	@Override
	public void receiveMessageFromServer(PacketBuffer buffer) {
		this.progress = buffer.readFloat();
	}
}