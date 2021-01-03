package com.github.zi_jing.cuckoolib.client.gui;

import com.github.zi_jing.cuckoolib.gui.ModularContainer;
import com.github.zi_jing.cuckoolib.gui.ModularGuiInfo;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ModularScreen extends ContainerScreen<ModularContainer> {
	protected ModularGuiInfo guiInfo;

	public ModularScreen(int window, ModularGuiInfo guiInfo, PlayerInventory inv, ITextComponent title) {
		super(new ModularContainer(null, window, guiInfo), inv, title);
		this.guiInfo = guiInfo;
	}

	public ModularGuiInfo getGuiInfo() {
		return this.guiInfo;
	}

	public ModularContainer getContainer() {
		return this.container;
	}

	@Override
	public void init() {
		this.xSize = this.guiInfo.getWidth();
		this.ySize = this.guiInfo.getHeight();
		super.init();
	}

	@Override
	public void render(MatrixStack transform, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(transform);
		this.guiInfo.getBackground().draw(transform, this.guiLeft, this.guiTop, this.xSize, this.ySize);
		super.render(transform, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(transform, mouseX, mouseY);
		this.guiInfo.handleMouseHovered(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack transform, int mouseX, int mouseY) {
		this.guiInfo.drawInForeground(transform, mouseX - this.guiLeft, mouseY - this.guiTop, this.guiLeft,
				this.guiTop);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack transform, float partialTicks, int mouseX, int mouseY) {
		this.guiInfo.drawInBackground(transform, partialTicks, mouseX - this.guiLeft, mouseY - this.guiTop,
				this.guiLeft, this.guiTop);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		super.mouseClicked(mouseX, mouseY, button);
		return this.guiInfo.handleMouseClicked(mouseX - this.guiLeft, mouseY - this.guiTop, button);
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		super.mouseReleased(mouseX, mouseY, button);
		return this.guiInfo.handleMouseReleased(mouseX - this.guiLeft, mouseY - this.guiTop, button);
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int mouseButton, double dragX, double dragY) {
		super.mouseDragged(mouseX, mouseY, mouseButton, dragX, dragY);
		return this.guiInfo.handleMouseClickMove(mouseX - this.guiLeft, mouseY - this.guiTop, mouseButton, dragX,
				dragY);
	}
}