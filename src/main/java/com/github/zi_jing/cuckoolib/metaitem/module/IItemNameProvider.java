package com.github.zi_jing.cuckoolib.metaitem.module;

import net.minecraft.item.ItemStack;

@FunctionalInterface
public interface IItemNameProvider extends IItemModule {
    @Override
    default Class<? extends IItemModule> getRegistryID() {
        return IItemNameProvider.class;
    }

    String getItemStackDisplayName(ItemStack stack);
}