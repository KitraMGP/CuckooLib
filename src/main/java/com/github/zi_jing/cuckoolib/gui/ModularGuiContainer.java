package com.github.zi_jing.cuckoolib.gui;

import java.io.IOException;

import com.github.zi_jing.cuckoolib.gui.widget.IWidget;

import net.minecraft.client.gui.inventory.GuiContainer;

public class ModularGuiContainer extends GuiContainer {
	protected ModularGuiInfo guiInfo;

	public ModularGuiContainer(ModularGuiInfo info) {
		super(new ModularContainer(info));
		this.guiInfo = info;
	}

	public ModularGuiInfo getGuiInfo() {
		return this.guiInfo;
	}

	@Override
	public void initGui() {
		this.xSize = this.guiInfo.getWidth();
		this.ySize = this.guiInfo.getHeight();
		super.initGui();
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		this.guiInfo.getWidgets().forEach(IWidget::updateScreen);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.guiInfo.drawInForeground(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		this.guiInfo.drawInBackground(partialTicks, mouseX, mouseY);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		this.guiInfo.handleMouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		super.mouseReleased(mouseX, mouseY, state);
		this.guiInfo.handleMouseReleased(mouseX, mouseY, state);
	}

	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int mouseButton, long timeSinceLastClick) {
		super.mouseClickMove(mouseX, mouseY, mouseButton, timeSinceLastClick);
		this.guiInfo.handleMouseClickMove(mouseX, mouseY, mouseButton, timeSinceLastClick);
	}
}