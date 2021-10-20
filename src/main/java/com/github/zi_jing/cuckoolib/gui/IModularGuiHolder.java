package com.github.zi_jing.cuckoolib.gui;

import com.github.zi_jing.cuckoolib.client.gui.ModularScreen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IModularGuiHolder {
	IGuiHolderCodec getCodec();

	ITextComponent getTitle(PlayerEntity player);

	ModularGuiInfo createGuiInfo(PlayerEntity player);

	default int[] getTasksToExecute(ModularContainer container) {
		return new int[0];
	}

	default void executeTask(ModularContainer container, int id) {

	}

	@OnlyIn(Dist.CLIENT)
	default void executeRenderTask(ModularScreen screen) {

	}
}