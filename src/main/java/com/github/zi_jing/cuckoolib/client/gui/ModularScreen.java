package com.github.zi_jing.cuckoolib.client.gui;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.github.zi_jing.cuckoolib.CuckooLib;
import com.github.zi_jing.cuckoolib.client.render.TextureArea;
import com.github.zi_jing.cuckoolib.gui.IModularGuiHolder;
import com.github.zi_jing.cuckoolib.gui.ModularContainer;
import com.github.zi_jing.cuckoolib.gui.ModularGuiInfo;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

public class ModularScreen extends ContainerScreen<ModularContainer> {
	public static final TextureArea TITLE_DEFAULT = TextureArea
			.createFullTexture(new ResourceLocation(CuckooLib.MODID, "textures/gui/title_default.png"));
	public static final TextureArea BACK = TextureArea
			.createFullTexture(new ResourceLocation(CuckooLib.MODID, "textures/gui/back.png"));
	public static final TextureArea REFRESH = TextureArea
			.createFullTexture(new ResourceLocation(CuckooLib.MODID, "textures/gui/refresh.png"));

	protected ModularGuiInfo guiInfo;

	public ModularScreen(int window, ModularGuiInfo guiInfo, IModularGuiHolder[] parentGuiHolders, PlayerInventory inv,
			ITextComponent title) {
		super(new ModularContainer(null, window, guiInfo, parentGuiHolders), inv, title);
		this.guiInfo = guiInfo;
	}

	public ModularGuiInfo getGuiInfo() {
		return this.guiInfo;
	}

	public ModularContainer getContainer() {
		return this.menu;
	}

	@Override
	public void init() {
		this.imageWidth = this.guiInfo.getWidth();
		this.imageHeight = this.guiInfo.getHeight();
		super.init();
	}

	@Override
	public void render(MatrixStack transform, int mouseX, int mouseY, float partialTicks) {
		this.menu.getGuiHolder().executeRenderTask(this);
		this.renderBackground(transform);
		this.guiInfo.getBackground().draw(transform, this.leftPos, this.topPos, this.imageWidth, this.imageHeight);
		List<ITextComponent> titles = Arrays.asList(this.menu.getParentGuiHolders()).stream()
				.map((holder) -> holder.getTitle(this.menu.getGuiInfo().getPlayer())).collect(Collectors.toList());
		String text = "";
		for (int i = 0; i < titles.size(); i++) {
			text += titles.get(i).getString();
			if (i != titles.size() - 1) {
				text += TextFormatting.GREEN + " > " + TextFormatting.RESET;
			}
		}
		drawString(transform, this.font, text, this.leftPos + 1, this.topPos - 11, 0xffffff);
		super.render(transform, mouseX, mouseY, partialTicks);
		this.renderTooltip(transform, mouseX, mouseY);
		this.guiInfo.handleMouseHovered(mouseX, mouseY);
		TITLE_DEFAULT.draw(transform, this.leftPos - 30, this.topPos, 30, 50);
	}

	@Override
	protected void renderLabels(MatrixStack transform, int mouseX, int mouseY) {
		this.guiInfo.drawInForeground(transform, mouseX - this.leftPos, mouseY - this.topPos, this.leftPos,
				this.topPos);
	}

	@Override
	protected void renderBg(MatrixStack transform, float partialTicks, int mouseX, int mouseY) {
		this.guiInfo.drawInBackground(transform, partialTicks, mouseX - this.leftPos, mouseY - this.topPos,
				this.leftPos, this.topPos);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		super.mouseClicked(mouseX, mouseY, button);
		return this.guiInfo.handleMouseClicked(mouseX - this.leftPos, mouseY - this.topPos, button);
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		super.mouseReleased(mouseX, mouseY, button);
		return this.guiInfo.handleMouseReleased(mouseX - this.leftPos, mouseY - this.topPos, button);
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int mouseButton, double dragX, double dragY) {
		super.mouseDragged(mouseX, mouseY, mouseButton, dragX, dragY);
		return this.guiInfo.handleMouseClickMove(mouseX - this.leftPos, mouseY - this.topPos, mouseButton, dragX,
				dragY);
	}
}