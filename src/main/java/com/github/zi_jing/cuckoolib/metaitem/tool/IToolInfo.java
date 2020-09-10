package com.github.zi_jing.cuckoolib.metaitem.tool;

import java.util.List;

import com.github.zi_jing.cuckoolib.material.type.Material;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public interface IToolInfo {
	ResourceLocation getRegistryName();

	boolean validateMaterial(Material material);

	int getMaxDamage(ItemStack stack);

	int getBlockBreakDamage();

	int getInteractionDamage();

	int getContainerCraftDamage();

	int getEntityHitDamage();

	int getHarvestLevel(ItemStack stack);

	boolean canHarvestBlock(IBlockState state, ItemStack stack);

	float getDestroySpeed(IBlockState state, ItemStack stack);

	default void onToolCreated(ItemStack stack, World world, EntityPlayer player) {

	}

	default void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {

	}

	default ItemStack getBrokenItemStack(ItemStack stack) {
		return ItemStack.EMPTY;
	}
}