package com.github.zi_jing.cuckoolib.gui.impl;

import com.github.zi_jing.cuckoolib.CuckooLib;
import com.github.zi_jing.cuckoolib.gui.IGuiHolderCodec;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class PlanGuiCodec implements IGuiHolderCodec<PlanGuiHolder> {
	public static final PlanGuiCodec INSTANCE = new PlanGuiCodec();

	public static final ResourceLocation REGISTRY_NAME = new ResourceLocation(CuckooLib.MODID, "plan");

	@Override
	public ResourceLocation getRegistryName() {
		return REGISTRY_NAME;
	}

	@Override
	public void writeHolder(PacketBuffer buf, PlanGuiHolder holder) {
		buf.writeBlockPos(((PlanGuiHolder) holder).getPlanInfoProvider().getBlockPos());
	}

	@Override
	public PlanGuiHolder readHolder(PacketBuffer buf) {
		return new PlanGuiHolder((IPlanInfoTileEntity) Minecraft.getInstance().level.getBlockEntity(buf.readBlockPos()));
	}
}