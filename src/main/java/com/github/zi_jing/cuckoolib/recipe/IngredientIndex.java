package com.github.zi_jing.cuckoolib.recipe;

import com.github.zi_jing.cuckoolib.material.MaterialUtil;
import com.github.zi_jing.cuckoolib.material.SolidShape;
import com.github.zi_jing.cuckoolib.material.type.Material;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;

public class IngredientIndex {
	public static IngredientIndex from(ItemStack stack) {
		return new IngredientIndex(Ingredient.fromStacks(stack), stack.getCount());
	}

	public static IngredientIndex from(ItemStack stack, int count) {
		return new IngredientIndex(Ingredient.fromStacks(stack), count);
	}

	public static IngredientIndex from(ITag<Item> tag) {
		return from(tag, 1);
	}

	public static IngredientIndex from(ITag<Item> tag, int count) {
		return new IngredientIndex(Ingredient.fromTag(tag), count);
	}

	public static IngredientIndex from(SolidShape shape, Material material) {
		return from(shape, material, 1);
	}

	public static IngredientIndex from(SolidShape shape, Material material, int count) {
		return from(MaterialUtil.getMaterialTag(shape, material), count);
	}

	private Ingredient ingredient;
	private int count;

	public IngredientIndex(Ingredient ingredient, int count) {
		this.ingredient = ingredient;
		this.count = Math.max(count, 0);
	}

	public Ingredient getIngredient() {
		return this.ingredient;
	}

	public int getCount() {
		return this.count;
	}
}