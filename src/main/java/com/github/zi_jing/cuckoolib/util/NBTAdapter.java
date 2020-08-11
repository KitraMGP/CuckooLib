package com.github.zi_jing.cuckoolib.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class NBTAdapter {
  public static NBTTagCompound getItemStackCompound(ItemStack stack) {
    if (stack.isEmpty()) {
      return null;
    }
    if (!stack.hasTagCompound()) {
      stack.setTagCompound(new NBTTagCompound());
    }
    return stack.getTagCompound();
  }

  public static NBTBase getTag(NBTTagCompound nbt, String key, NBTBase value) {
    if (!nbt.hasKey(key)) {
      nbt.setTag(key, value);
    }
    return nbt.getTag(key);
  }

  public static byte getByte(NBTTagCompound nbt, String key, byte value) {
    if (!nbt.hasKey(key, 1)) {
      nbt.setByte(key, value);
    }
    return nbt.getByte(key);
  }

  public static short getShort(NBTTagCompound nbt, String key, short value) {
    if (!nbt.hasKey(key, 2)) {
      nbt.setShort(key, value);
    }
    return nbt.getShort(key);
  }

  public static int getInteger(NBTTagCompound nbt, String key, int value) {
    if (!nbt.hasKey(key, 3)) {
      nbt.setInteger(key, value);
    }
    return nbt.getInteger(key);
  }

  public static long getLong(NBTTagCompound nbt, String key, long value) {
    if (!nbt.hasKey(key, 4)) {
      nbt.setLong(key, value);
    }
    return nbt.getLong(key);
  }

  public static float getFloat(NBTTagCompound nbt, String key, float value) {
    if (!nbt.hasKey(key, 5)) {
      nbt.setFloat(key, value);
    }
    return nbt.getFloat(key);
  }

  public static double getDouble(NBTTagCompound nbt, String key, double value) {
    if (!nbt.hasKey(key, 6)) {
      nbt.setDouble(key, value);
    }
    return nbt.getDouble(key);
  }

  public static byte[] getByteArray(NBTTagCompound nbt, String key, byte[] value) {
    if (!nbt.hasKey(key, 7)) {
      nbt.setByteArray(key, value);
    }
    return nbt.getByteArray(key);
  }

  public static String getString(NBTTagCompound nbt, String key, String value) {
    if (!nbt.hasKey(key, 8)) {
      nbt.setString(key, value);
    }
    return nbt.getString(key);
  }

  public static NBTTagList getTagList(NBTTagCompound nbt, String key, NBTTagList value) {
    if (!nbt.hasKey(key, 9)) {
      nbt.setTag(key, value);
    }
    return (NBTTagList) nbt.getTag(key);
  }

  public static NBTTagCompound getTagCompound(
      NBTTagCompound nbt, String key, NBTTagCompound value) {
    if (!nbt.hasKey(key, 10)) {
      nbt.setTag(key, value);
    }
    return nbt.getCompoundTag(key);
  }

  public static int[] getIntArray(NBTTagCompound nbt, String key, int[] value) {
    if (!nbt.hasKey(key, 11)) {
      nbt.setIntArray(key, value);
    }
    return nbt.getIntArray(key);
  }
}
