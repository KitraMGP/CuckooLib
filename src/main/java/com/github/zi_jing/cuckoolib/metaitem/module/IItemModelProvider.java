package com.github.zi_jing.cuckoolib.metaitem.module;

import net.minecraft.item.ItemStack;

@FunctionalInterface
public interface IItemModelProvider extends IItemModule {
	@Override
	default Class<? extends IItemModule> getRegistryID() {
		return IItemModelProvider.class;
	}

	int getModelIndex(ItemStack stack);
}