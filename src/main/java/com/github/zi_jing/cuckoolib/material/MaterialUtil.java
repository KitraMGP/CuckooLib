package com.github.zi_jing.cuckoolib.material;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.zi_jing.cuckoolib.item.MaterialItem;
import com.github.zi_jing.cuckoolib.material.type.Material;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class MaterialUtil {
	public static final Map<MaterialEntry, ITag<Item>> MATERIAL_TAG_CACHE = new HashMap<MaterialEntry, ITag<Item>>();

	public static Item getMaterialItem(SolidShape shape, Material material) {
		MaterialEntry entry = new MaterialEntry(shape, material);
		for (Map<MaterialEntry, MaterialItem> map : MaterialItem.REGISTERED_MATERIAL_ITEM.values()) {
			if (map.containsKey(entry)) {
				return map.get(entry);
			}
		}
		ITag<Item> tag = getMaterialTag(entry);
		if (tag != null) {
			List<Item> list = tag.getAllElements();
			if (!list.isEmpty()) {
				return list.get(0);
			}
		}
		return null;
	}

	public static ItemStack getMaterialItemStack(SolidShape shape, Material material) {
		return getMaterialItemStack(shape, material, 1);
	}

	public static ItemStack getMaterialItemStack(SolidShape shape, Material material, int count) {
		MaterialEntry entry = new MaterialEntry(shape, material);
		for (Map<MaterialEntry, MaterialItem> map : MaterialItem.REGISTERED_MATERIAL_ITEM.values()) {
			if (map.containsKey(entry)) {
				return new ItemStack(map.get(entry), count);
			}
		}
		return ItemStack.EMPTY;
	}

	public static ITag<Item> getMaterialTag(MaterialEntry entry) {
		if (!MATERIAL_TAG_CACHE.containsKey(entry)) {
			MATERIAL_TAG_CACHE.put(entry, getItemTag(entry.toString()));
		}
		return MATERIAL_TAG_CACHE.get(entry);
	}

	public static ITag<Item> getMaterialTag(SolidShape shape, Material material) {
		return getMaterialTag(new MaterialEntry(shape, material));
	}

	public static ITag<Item> getItemTag(String name) {
		return getItemTag(new ResourceLocation("forge", name));
	}

	public static ITag<Item> getItemTag(ResourceLocation id) {
		return ItemTags.getCollection().get(id);
	}

	public static INamedTag<Item> createForgeItemTag(String name) {
		return ItemTags.makeWrapperTag(new ResourceLocation("forge", name).toString());
	}
}