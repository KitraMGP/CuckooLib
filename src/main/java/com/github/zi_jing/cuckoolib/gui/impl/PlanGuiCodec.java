package com.github.zi_jing.cuckoolib.gui.impl;

import com.github.zi_jing.cuckoolib.CuckooLib;
import com.github.zi_jing.cuckoolib.gui.IGuiHolderCodec;
import com.github.zi_jing.cuckoolib.gui.IModularGuiHolder;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class PlanGuiCodec implements IGuiHolderCodec {
	public static final PlanGuiCodec INSTANCE = new PlanGuiCodec();

	public static final ResourceLocation REGISTRY_NAME = new ResourceLocation(CuckooLib.MODID, "plan");

	@Override
	public ResourceLocation getRegistryName() {
		return REGISTRY_NAME;
	}

	@Override
	public void writeHolder(PacketBuffer buf, IModularGuiHolder holder) {
		if (holder instanceof PlanGuiHolder) {
			buf.writeBlockPos(((PlanGuiHolder) holder).getPlanInfoProvider().getBlockPos());
		} else {
			throw new IllegalArgumentException("The modular gui holder isn't PlanGuiHolder");
		}
	}

	@Override
	public IModularGuiHolder readHolder(PacketBuffer buf) {
		return new PlanGuiHolder((IPlanInfoTileEntity) Minecraft.getInstance().world.getTileEntity(buf.readBlockPos()));
	}
}