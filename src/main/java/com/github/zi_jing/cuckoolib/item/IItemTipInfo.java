package com.github.zi_jing.cuckoolib.item;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public interface IItemTipInfo {
	String getTitle(ItemStack stack);

	List<String> getInfo(ItemStack stack);

	TextFormatting getIndexColor(ItemStack stack);
}