package com.github.zi_jing.cuckoolib.item;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.github.zi_jing.cuckoolib.material.type.MaterialBase;
import com.github.zi_jing.cuckoolib.tool.IToolInfo;
import com.github.zi_jing.cuckoolib.util.data.NBTAdapter;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;

public class MaterialToolItem extends ToolItem {
	public MaterialToolItem(String modid, String name, ItemGroup group, IToolInfo toolInfo) {
		super(modid, name, group, toolInfo);
	}

	public static Map<Integer, Pair<String, MaterialBase>> getToolAllMaterial(ItemStack stack) {
		CompoundNBT nbt = getToolBaseNBT(stack);
		if (nbt.contains("material")) {
			ListNBT list = nbt.getList("material", 10);
			Iterator<INBT> ite = list.iterator();
			Map<Integer, Pair<String, MaterialBase>> map = new HashMap<Integer, Pair<String, MaterialBase>>();
			while (ite.hasNext()) {
				CompoundNBT compound = (CompoundNBT) ite.next();
				map.put(compound.getInt("index"), Pair.of(compound.getString("part"),
						MaterialBase.getMaterialByName(compound.getString("material"))));
			}
			return map;
		}
		return Collections.emptyMap();
	}

	public static MaterialBase getToolMaterial(ItemStack stack, int index) {
		CompoundNBT nbt = getToolBaseNBT(stack);
		if (nbt.contains("material")) {
			ListNBT list = nbt.getList("material", 10);
			Iterator<INBT> ite = list.iterator();
			while (ite.hasNext()) {
				CompoundNBT compound = (CompoundNBT) ite.next();
				if (compound.getInt("index") == index) {
					return MaterialBase.getMaterialByName(compound.getString("material"));
				}
			}
		}
		return null;
	}

	public static MaterialBase getToolMaterial(ItemStack stack, String part) {
		CompoundNBT nbt = getToolBaseNBT(stack);
		if (nbt.contains("material")) {
			ListNBT list = nbt.getList("material", 10);
			Iterator<INBT> ite = list.iterator();
			while (ite.hasNext()) {
				CompoundNBT compound = (CompoundNBT) ite.next();
				if (compound.getString("part").equals(part)) {
					return MaterialBase.getMaterialByName(compound.getString("material"));
				}
			}
		}
		return null;
	}

	public static ItemStack setToolMaterial(ItemStack stack, int index, String part, MaterialBase material) {
		CompoundNBT nbt = getToolBaseNBT(stack);
		ListNBT list = NBTAdapter.getList(nbt, "material", 10);
		CompoundNBT compound = new CompoundNBT();
		compound.putInt("index", index);
		compound.putString("part", part);
		compound.putString("material", material.getName());
		list.add(compound);
		return stack;
	}

	public ItemStack createItemStack() {
		return this.createItemStack(1);
	}

	public ItemStack createItemStack(int count) {
		ItemStack stack = new ItemStack(this, count);
		this.toolInfo.getDefaultMaterial()
				.forEach((index, pair) -> setToolMaterial(stack, index, pair.getLeft(), pair.getRight()));
		CompoundNBT nbt = getToolBaseNBT(stack);
		return stack;
	}

	public ItemStack createItemStack(int count, Map<Integer, Pair<String, MaterialBase>> materials) {
		ItemStack stack = new ItemStack(this, count);
		CompoundNBT nbt = getToolBaseNBT(stack);
		materials.forEach((index, pair) -> setToolMaterial(stack, index, pair.getLeft(), pair.getRight()));
		return stack;
	}

	public boolean validateToolMaterial(MaterialBase material) {
		return material.hasFlag(MaterialBase.GENERATE_TOOL);
	}

	public int getItemColor(ItemStack stack, int tintIndex) {
		MaterialBase material = getToolMaterial(stack, tintIndex);
		if (material != null) {
			return material.getColor();
		}
		return 0xffffff;
	}

	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		if (this.isInGroup(group)) {
			items.add(this.createItemStack());
		}
	}
}