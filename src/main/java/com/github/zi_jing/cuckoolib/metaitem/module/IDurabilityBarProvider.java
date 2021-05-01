package com.github.zi_jing.cuckoolib.metaitem.module;

import net.minecraft.item.ItemStack;

public interface IDurabilityBarProvider extends IItemModule {
    @Override
    default Class<? extends IItemModule> getRegistryID() {
        return IDurabilityBarProvider.class;
    }

    boolean showDurabilityBar(ItemStack stack);

    double getDurabilityForDisplay(ItemStack stack);

    int getRGBDurabilityForDisplay(ItemStack stack);
}