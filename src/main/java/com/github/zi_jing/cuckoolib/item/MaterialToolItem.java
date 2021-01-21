package com.github.zi_jing.cuckoolib.item;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.github.zi_jing.cuckoolib.material.ModMaterials;
import com.github.zi_jing.cuckoolib.material.type.Material;
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

	public static Map<Integer, Pair<String, Material>> getToolAllMaterial(ItemStack stack) {
		CompoundNBT nbt = getToolBaseNBT(stack);
		if (nbt.contains("material")) {
			ListNBT list = nbt.getList("material", 10);
			Iterator<INBT> ite = list.iterator();
			Map<Integer, Pair<String, Material>> map = new HashMap<Integer, Pair<String, Material>>();
			while (ite.hasNext()) {
				CompoundNBT compound = (CompoundNBT) ite.next();
				map.put(compound.getInt("index"), Pair.of(compound.getString("part"),
						Material.getMaterialByName(compound.getString("material"))));
			}
			return map;
		}
		return Collections.emptyMap();
	}

	public static Material getToolMaterial(ItemStack stack, int index) {
		CompoundNBT nbt = getToolBaseNBT(stack);
		if (nbt.contains("material")) {
			ListNBT list = nbt.getList("material", 10);
			Iterator<INBT> ite = list.iterator();
			while (ite.hasNext()) {
				CompoundNBT compound = (CompoundNBT) ite.next();
				if (compound.getInt("index") == index) {
					return Material.getMaterialByName(compound.getString("material"));
				}
			}
		}
		return ModMaterials.EMPTY;
	}

	public static Material getToolMaterial(ItemStack stack, String part) {
		CompoundNBT nbt = getToolBaseNBT(stack);
		if (nbt.contains("material")) {
			ListNBT list = nbt.getList("material", 10);
			Iterator<INBT> ite = list.iterator();
			while (ite.hasNext()) {
				CompoundNBT compound = (CompoundNBT) ite.next();
				if (compound.getString("part").equals(part)) {
					return Material.getMaterialByName(compound.getString("material"));
				}
			}
		}
		return ModMaterials.EMPTY;
	}

	public static void setToolMaterial(ItemStack stack, int index, String part, Material material) {
		CompoundNBT nbt = getToolBaseNBT(stack);
		ListNBT list = NBTAdapter.getList(nbt, "material", 10);
		CompoundNBT compound = new CompoundNBT();
		compound.putInt("index", index);
		compound.putString("part", part);
		compound.putString("material", material.getName());
		list.add(compound);
	}

	public ItemStack createItemStack() {
		return this.createItemStack(1);
	}

	public ItemStack createItemStack(int count) {
		ItemStack stack = new ItemStack(this, count);
		this.toolInfo.getDefaultMaterial()
				.forEach((index, pair) -> setToolMaterial(stack, index, pair.getLeft(), pair.getRight()));
		CompoundNBT nbt = getToolBaseNBT(stack);
		ListNBT list = nbt.getList("material", 10);
		return stack;
	}

	public boolean validateToolMaterial(Material material) {
		return material.hasFlag(Material.GENERATE_TOOL);
	}

	public int getItemColor(ItemStack stack, int tintIndex) {
		Material material = getToolMaterial(stack, tintIndex);
		if (!material.isEmpty()) {
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