package com.github.zi_jing.cuckoolib.gui;

import net.minecraft.client.gui.inventory.GuiContainer;

public class ModularGuiContainer extends GuiContainer {
	public ModularGuiContainer(ModularGuiInfo info) {
		super(new ModularContainer(info));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

	}
}