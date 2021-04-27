package com.github.zi_jing.cuckoolib.material;

import java.util.List;
import java.util.Map;

import com.github.zi_jing.cuckoolib.item.MaterialItem;
import com.github.zi_jing.cuckoolib.material.type.MaterialBase;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.ResourceLocation;

public class MaterialUtil {
	public static Item getMaterialItem(SolidShape shape, MaterialBase material) {
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

	public static ItemStack getMaterialItemStack(SolidShape shape, MaterialBase material) {
		return getMaterialItemStack(shape, material, 1);
	}

	public static ItemStack getMaterialItemStack(SolidShape shape, MaterialBase material, int count) {
		Item item = getMaterialItem(shape, material);
		if (item != null) {
			return new ItemStack(item, count);
		}
		return ItemStack.EMPTY;
	}

	public static ITag<Item> getMaterialTag(SolidShape shape) {
		return getItemTag(new ResourceLocation("forge", shape.getName()));
	}

	public static ITag<Item> getMaterialTag(MaterialEntry entry) {
		return getItemTag(entry.toString());
	}

	public static ITag<Item> getMaterialTag(SolidShape shape, MaterialBase material) {
		return getMaterialTag(new MaterialEntry(shape, material));
	}

	public static ITag<Item> getItemTag(String name) {
		return getItemTag(new ResourceLocation("forge", name));
	}

	public static ITag<Item> getItemTag(ResourceLocation id) {
		return TagCollectionManager.getManager().getItemTags().get(id);
	}

	public static INamedTag<Item> createForgeItemTag(String name) {
		return ItemTags.makeWrapperTag(new ResourceLocation("forge", name).toString());
	}
}