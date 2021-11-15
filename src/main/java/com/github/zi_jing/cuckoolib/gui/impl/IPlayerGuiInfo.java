package com.github.zi_jing.cuckoolib.gui.impl;

import com.github.zi_jing.cuckoolib.client.gui.ModularScreen;
import com.github.zi_jing.cuckoolib.gui.ModularContainer;
import com.github.zi_jing.cuckoolib.gui.ModularGuiInfo;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IPlayerGuiInfo {
	ResourceLocation getInfoRegistryName();

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