package com.github.zi_jing.cuckoolib.metaitem.module;

import net.minecraft.item.ItemStack;

public interface IContainerItemProvider extends IItemModule {
	ItemStack getContainerItem(ItemStack stack);
}