package com.github.zi_jing.cuckoolib.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class IngredientIndex {
	public static IngredientIndex from(ItemStack stack) {
		return new IngredientIndex(Ingredient.fromStacks(stack), stack.getCount());
	}

	public static IngredientIndex from(ItemStack stack, int count) {
		return new IngredientIndex(Ingredient.fromStacks(stack), count);
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