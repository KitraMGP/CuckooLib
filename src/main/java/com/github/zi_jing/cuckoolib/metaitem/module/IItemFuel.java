package com.github.zi_jing.cuckoolib.metaitem.module;

import net.minecraft.item.ItemStack;

@FunctionalInterface
public interface IItemFuel extends IItemModule {
	@Override
	default Class<? extends IItemModule> getRegistryID() {
		return IItemFuel.class;
	}

	int getItemBurnTime(ItemStack stack);
}