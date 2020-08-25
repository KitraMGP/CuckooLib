package com.github.zi_jing.cuckoolib.metaitem.module;

import net.minecraft.item.ItemStack;

public interface IContainerItemProvider extends IItemModule {
	@Override
	default Class<? extends IItemModule> getRegistryID() {
		return IContainerItemProvider.class;
	}

	ItemStack getContainerItem(ItemStack stack);
}