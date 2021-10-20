package com.github.zi_jing.cuckoolib.gui.widget;

import java.util.function.IntSupplier;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PointerWidget extends WidgetBase {
	protected IntSupplier supplier;
	protected int progress;
	protected MoveType moveType;

	public PointerWidget(int x, int y, int width, int height, IntSupplier supplier, MoveType moveType) {
		super(x, y, width, height);
		this.supplier = supplier;
		this.moveType = moveType;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void drawInBackground(MatrixStack transform, float partialTicks, int mouseX, int mouseY, int guiLeft,
			int guiTop) {
		if (this.renderer == null) {
			return;
		}
		switch (this.moveType) {
		case HORIZONTAL:
			this.renderer.draw(transform, guiLeft + this.position.getX() + this.progress, guiTop + this.position.getY(),
					this.size.getX(), this.size.getY());
			break;
		case HORIZONTAL_INVERTED:
			this.renderer.draw(transform, guiLeft + this.position.getX() - this.progress, guiTop + this.position.getY(),
					this.size.getX(), this.size.getY());
			break;
		case VERTICAL:
			this.renderer.draw(transform, guiLeft + this.position.getX(), guiTop + this.position.getY() + this.progress,
					this.size.getX(), this.size.getY());
			break;
		case VERTICAL_INVERTED:
			this.renderer.draw(transform, guiLeft + this.position.getX(), guiTop + this.position.getY() - this.progress,
					this.size.getX(), this.size.getY());
			break;
		default:
			break;
		}
	}

	@Override
	public void detectAndSendChanges() {
		int update = this.supplier.getAsInt();
		if (update != this.progress) {
			this.progress = update;
			this.writePacketToClient(this.id, (buf) -> buf.writeInt(update));
		}
	}

	@Override
	public void receiveMessageFromServer(PacketBuffer buffer) {
		this.progress = buffer.readInt();
	}
}