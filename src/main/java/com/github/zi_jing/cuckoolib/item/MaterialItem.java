package com.github.zi_jing.cuckoolib.item;

import java.util.ArrayList;
import java.util.List;

import com.github.zi_jing.cuckoolib.CuckooLib;
import com.github.zi_jing.cuckoolib.material.SolidShape;
import com.github.zi_jing.cuckoolib.material.type.Material;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class MaterialItem extends ItemBase {
	public static final List<MaterialItem> REGISTERED_MATERIAL_ITEM = new ArrayList<MaterialItem>();

	protected SolidShape shape;
	protected Material material;

	public MaterialItem(String modid, SolidShape shape, Material material) {
		super(modid, "material_item." + shape.getName() + "." + material.getName(), CuckooLib.GROUP_MATERIAL);
		this.shape = shape;
		this.material = material;
		REGISTERED_MATERIAL_ITEM.add(this);
	}

	public static SolidShape getItemShape(ItemStack stack) {
		return ((MaterialItem) stack.getItem()).shape;
	}

	public static Material getItemMaterial(ItemStack stack) {
		return ((MaterialItem) stack.getItem()).material;
	}

	public SolidShape getShape() {
		return this.shape;
	}

	public Material getMaterial() {
		return this.material;
	}

	public ItemStack createItemStack() {
		return this.createItemStack(1);
	}

	public ItemStack createItemStack(int count) {
		return new ItemStack(this, count);
	}

	public int getItemColor(ItemStack stack, int tintIndex) {
		Material material = getItemMaterial(stack);
		if (!material.isEmpty()) {
			return material.getColor();
		}
		return 0xffffff;
	}

	@Override
	public ITextComponent getDisplayName(ItemStack stack) {
		SolidShape shape = getItemShape(stack);
		Material material = getItemMaterial(stack);
		if (shape != null && material != null) {
			return new StringTextComponent(getItemShape(stack).getLocalizedname(getItemMaterial(stack)));
		}
		return StringTextComponent.EMPTY;
	}
}