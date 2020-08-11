package com.github.zi_jing.cuckoolib.metaitem.module;

import net.minecraft.item.ItemStack;

public interface IToolDamage extends IItemModule {
  void damageItem(ItemStack stack, int damage);

  int getItemDamage(ItemStack stack);

  int getItemMaxDamage(ItemStack stack);
}
