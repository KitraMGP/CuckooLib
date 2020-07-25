package com.github.zi_jing.cuckoolib.item;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class DurabilityManager {
    public static final DurabilityManager INSTANCE = new DurabilityManager();

    public boolean showsDurabilityBar(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            return false;
        }
        NBTTagCompound nbtDamage = stack.getTagCompound().getCompoundTag("tmc.damage");
        if (nbtDamage == null) {
            return false;
        }
        return nbtDamage.getInteger("damage") != 0;
    }

    public double getDurabilityForDisplay(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            return 0;
        }
        NBTTagCompound nbtDamage = stack.getTagCompound().getCompoundTag("tmc.damage");
        if (nbtDamage == null) {
            return 0;
        }
        int maxDamage = nbtDamage.getInteger("maxDamage");
        if (maxDamage == 0) {
            return 0;
        }
        return ((double) (nbtDamage.getInteger("damage"))) / maxDamage;
    }

    public int getRGBDurabilityForDisplay(ItemStack stack) {
        double durability = this.getDurabilityForDisplay(stack);
        if (durability < 0.7) {
            return 0x00ff00;
        }
        if (durability < 0.9) {
            return 0xffff00;
        }
        return 0xff0000;
    }
}