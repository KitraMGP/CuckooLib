package com.github.zi_jing.cuckoolib.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class ItemBase extends Item {
	public ItemBase(ItemGroup group) {
		this(new Properties(), group);
	}

	public ItemBase(Properties properties, ItemGroup group) {
		super(properties.tab(group));
	}
}