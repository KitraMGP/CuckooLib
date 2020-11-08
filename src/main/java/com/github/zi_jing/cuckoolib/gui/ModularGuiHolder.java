package com.github.zi_jing.cuckoolib.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public interface ModularGuiHolder {
	ResourceLocation getRegistryName();

	ModularGuiInfo createGuiInfo(EntityPlayer player);
}