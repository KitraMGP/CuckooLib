package com.github.zi_jing.cuckoolib.metaitem.module;

import net.minecraft.item.ItemStack;

@FunctionalInterface
public interface IItemNameProvider extends IItemModule {
    String getItemStackDisplayName(ItemStack stack);
}