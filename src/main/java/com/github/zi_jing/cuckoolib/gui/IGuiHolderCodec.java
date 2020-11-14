package com.github.zi_jing.cuckoolib.gui;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IGuiHolderCodec {
	ResourceLocation getRegistryName();

	void writeHolder(PacketBuffer buf, IModularGuiHolder holder);

	@SideOnly(Side.CLIENT)
	IModularGuiHolder readHolder(PacketBuffer buf);
}