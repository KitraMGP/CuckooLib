package com.github.zi_jing.cuckoolib.gui.impl;

import com.github.zi_jing.cuckoolib.CuckooLib;
import com.github.zi_jing.cuckoolib.client.render.TextureArea;
import com.github.zi_jing.cuckoolib.gui.IGuiHolderCodec;
import com.github.zi_jing.cuckoolib.gui.IModularGuiHolder;
import com.github.zi_jing.cuckoolib.gui.ModularGuiInfo;
import com.github.zi_jing.cuckoolib.gui.widget.ButtonWidget;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class PlanGuiHolder implements IModularGuiHolder {
	public static final TextureArea BACKGROUND = TextureArea
			.createFullTexture(new ResourceLocation(CuckooLib.MODID, "textures/gui/plan/background.png"));
	public static final TextureArea BUTTON = TextureArea
			.createFullTexture(new ResourceLocation(CuckooLib.MODID, "textures/gui/plan/button.png"));

	private IPlanInfoTileEntity planInfoProvider;

	public PlanGuiHolder() {

	}

	public PlanGuiHolder(IPlanInfoTileEntity planInfoProvider) {
		this.planInfoProvider = planInfoProvider;
	}

	public IPlanInfoTileEntity getPlanInfoProvider() {
		return this.planInfoProvider;
	}

	@Override
	public IGuiHolderCodec getCodec() {
		return PlanGuiCodec.INSTANCE;
	}

	@Override
	public ITextComponent getTitle(PlayerEntity player) {
		return new TranslationTextComponent("modulargui.plan.name");
	}

	@Override
	public ModularGuiInfo createGuiInfo(PlayerEntity player) {
		ModularGuiInfo.Builder builder = ModularGuiInfo.builder(168, 114).setBackground(BACKGROUND);
		for (int i = 0; i < this.planInfoProvider.getPlanCount(); i++) {
			int x = i % 9;
			int y = i / 9;
			builder.addWidget(i, new ButtonWidget(i, x * 18 + 3, y * 18 + 3, 18, 18, (data, container) -> {
				this.planInfoProvider.callback(data.getCount());
				ModularGuiInfo.backToParentGui(container);
			}).setRenderer(BUTTON.merge(this.planInfoProvider.getOverlayRenderer(i))));
		}
		return builder.build(player);
	}
}