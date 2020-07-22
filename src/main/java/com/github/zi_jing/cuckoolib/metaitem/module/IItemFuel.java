package com.github.zi_jing.cuckoolib.metaitem.module;

import net.minecraft.item.ItemStack;

@FunctionalInterface
public interface IItemFuel extends IItemModule {
	int getItemBurnTime(ItemStack stack);
}