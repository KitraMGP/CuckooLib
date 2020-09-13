package com.github.zi_jing.cuckoolib.metaitem.tool;

import com.github.zi_jing.cuckoolib.CuckooLib;
import com.github.zi_jing.cuckoolib.material.type.Material;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class NormalToolInfo implements IToolInfo {
	public static final NormalToolInfo INSTANCE = new NormalToolInfo();

	public static final ResourceLocation REGISTRY_NAME = new ResourceLocation(CuckooLib.MODID, "normal");

	private NormalToolInfo() {

	}

	@Override
	public ResourceLocation getRegistryName() {
		return REGISTRY_NAME;
	}

	@Override
	public boolean validateMaterial(Material material) {
		return false;
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		return 0;
	}

	@Override
	public int getBlockBreakDamage() {
		return 0;
	}

	@Override
	public int getInteractionDamage() {
		return 0;
	}

	@Override
	public int getContainerCraftDamage() {
		return 0;
	}

	@Override
	public int getEntityHitDamage() {
		return 0;
	}

	@Override
	public int getHarvestLevel(ItemStack stack) {
		return -1;
	}

	@Override
	public boolean canHarvestBlock(IBlockState state, ItemStack stack) {
		return false;
	}

	@Override
	public float getDestroySpeed(IBlockState state, ItemStack stack) {
		return 0;
	}
}