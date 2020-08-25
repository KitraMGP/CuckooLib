package com.github.zi_jing.cuckoolib.metaitem.module;

import net.minecraft.item.ItemStack;

public interface IToolDamage extends IItemModule {
	@Override
	default Class<? extends IItemModule> getRegistryID() {
		return IToolDamage.class;
	}

	void damageItem(ItemStack stack, int damage);

	int getItemDamage(ItemStack stack);

	int getItemMaxDamage(ItemStack stack);
}