package com.github.zi_jing.cuckoolib.gui;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IGuiHolderCodec<T extends IModularGuiHolder> {
	ResourceLocation getRegistryName();

	void writeHolder(PacketBuffer buf, T holder);

	@OnlyIn(Dist.CLIENT)
	T readHolder(PacketBuffer buf);
}