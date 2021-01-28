package com.github.zi_jing.cuckoolib.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class ItemBase extends Item {
	public static final List<Item> REGISTERED_ITEM = new ArrayList<Item>();

	protected String modid, name;

	public ItemBase(String modid, String name) {
		this(modid, name, ItemGroup.MISC);
	}

	public ItemBase(String modid, String name, ItemGroup group) {
		this(modid, name, new Properties(), group);
	}

	public ItemBase(String modid, String name, Properties properties, ItemGroup group) {
		this(modid, name, properties, group, true);
	}

	public ItemBase(String modid, String name, Properties properties, ItemGroup group, boolean register) {
		super(properties.group(group));
		this.modid = modid;
		this.name = name;
		this.setRegistryName(modid, name);
		if (register) {
			REGISTERED_ITEM.add(this);
		}
	}

	public String getItemModid() {
		return this.modid;
	}

	public String getItemName() {
		return this.name;
	}
}