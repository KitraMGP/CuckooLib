package com.github.zi_jing.cuckoolib.gui.widget;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public interface ISlotWidget extends IWidget {
    public static final ItemStack TAG = ItemStack.EMPTY.copy();

    Slot getSlot();

    int getSlotCount();

    void setSlotCount(int count);

    default boolean canMergeSlot(ItemStack stack) {
        return false;
    }

    default ItemStack onItemTake(EntityPlayer player, ItemStack stack) {
        return TAG;
    }
}