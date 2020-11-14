package com.github.zi_jing.cuckoolib.gui;

import com.github.zi_jing.cuckoolib.CuckooLib;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TEGuiHolderCodec implements IGuiHolderCodec {
	public static final TEGuiHolderCodec INSTANCE = new TEGuiHolderCodec();

	public static final ResourceLocation REGISTRY_NAME = new ResourceLocation(CuckooLib.MODID, "tile_entity");

	private TEGuiHolderCodec() {

	}

	@Override
	public ResourceLocation getRegistryName() {
		return REGISTRY_NAME;
	}

	@Override
	public void writeHolder(PacketBuffer buf, IModularGuiHolder holder) {
		if (holder instanceof TileEntity) {
			buf.writeBlockPos(((TileEntity) holder).getPos());
		} else {
			throw new IllegalArgumentException("The modular gui holder isn't TileEntity");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IModularGuiHolder readHolder(PacketBuffer buf) {
		return (IModularGuiHolder) Minecraft.getMinecraft().world.getTileEntity(buf.readBlockPos());
	}
}