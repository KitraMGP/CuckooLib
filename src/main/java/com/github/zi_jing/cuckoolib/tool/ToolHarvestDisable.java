package com.github.zi_jing.cuckoolib.tool;

import com.github.zi_jing.cuckoolib.item.ToolItem;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ToolType;

public abstract class ToolHarvestDisable implements IToolInfo {
	@Override
	public int getBlockBreakDamage() {
		return 0;
	}

	@Override
	public int getHarvestLevel(ItemStack stack, ToolType tool, PlayerEntity player, BlockState blockState) {
		return -1;
	}

	@Override
	public boolean canHarvestBlock(ItemStack stack, BlockState state) {
		return false;
	}

	@Override
	public float getDestroySpeed(ItemStack stack) {
		return 0;
	}

	public ToolItem getToolItem(ItemStack stack) {
		return (ToolItem) stack.getItem();
	}
}