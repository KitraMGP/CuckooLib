package com.github.zi_jing.cuckoolib.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemIndex {
    private Item item;
    private int meta;

    public ItemIndex(Item item) {
        this.item = item;
    }

    public ItemIndex(Item item, int meta) {
        this.item = item;
        this.meta = meta;
    }

    public ItemIndex(Block block) {
        this(Item.getItemFromBlock(block));
    }

    public ItemIndex(Block block, int meta) {
        this(Item.getItemFromBlock(block), meta);
    }

    public ItemIndex(ItemStack stack) {
        this.item = stack.getItem();
        this.meta = stack.getItemDamage();
    }

    public boolean validate(ItemStack stack) {
        return this.item == stack.getItem() && this.meta == stack.getItemDamage();
    }

    public ItemStack createStack() {
        return this.createStack(1);
    }

    public ItemStack createStack(int size) {
        return new ItemStack(this.item, size, this.meta);
    }

    public Item getItem() {
        return this.item;
    }

    public int getMeta() {
        return this.meta;
    }

    @Override
    public int hashCode() {
        return this.item.hashCode() * 31 + this.meta;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ItemIndex)) {
            return false;
        }
        ItemIndex idx = (ItemIndex) obj;
        return this.item == idx.item && this.meta == idx.meta;
    }
}
