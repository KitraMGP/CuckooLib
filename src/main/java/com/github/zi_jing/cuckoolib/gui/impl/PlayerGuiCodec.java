package com.github.zi_jing.cuckoolib.gui.impl;

import com.github.zi_jing.cuckoolib.CuckooLib;
import com.github.zi_jing.cuckoolib.gui.IGuiHolderCodec;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class PlayerGuiCodec implements IGuiHolderCodec<PlayerGuiHolder> {
	public static final PlayerGuiCodec INSTANCE = new PlayerGuiCodec();

	public static final ResourceLocation REGISTRY_NAME = new ResourceLocation(CuckooLib.MODID, "player");

	@Override
	public ResourceLocation getRegistryName() {
		return REGISTRY_NAME;
	}

	@Override
	public void writeHolder(PacketBuffer buf, PlayerGuiHolder holder) {
		buf.writeResourceLocation(holder.getInfoRegistryName());
	}

	@Override
	public PlayerGuiHolder readHolder(PacketBuffer buf) {
		return new PlayerGuiHolder(buf.readResourceLocation());
	}
}