package com.github.zi_jing.cuckoolib.metaitem.module;

import net.minecraft.item.ItemStack;

@FunctionalInterface
public interface IItemModelProvider extends IItemModule {
    int getModelIndex(ItemStack stack);
}