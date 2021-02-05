package com.github.zi_jing.cuckoolib.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;

public interface IModularGuiHolder {
	IGuiHolderCodec getCodec();

	ITextComponent getTitle(PlayerEntity player);

	ModularGuiInfo createGuiInfo(PlayerEntity player);
}