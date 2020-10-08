package com.github.zi_jing.cuckoolib.recipe;

import com.github.zi_jing.cuckoolib.material.SolidShape;
import com.github.zi_jing.cuckoolib.material.type.Material;
import com.google.common.base.CaseFormat;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.oredict.OreIngredient;

public class IngredientIndex {
	public static IngredientIndex from(ItemStack stack) {
		return new IngredientIndex(Ingredient.fromStacks(stack), stack.getCount());
	}

	public static IngredientIndex from(ItemStack stack, int count) {
		return new IngredientIndex(Ingredient.fromStacks(stack), count);
	}

	public static IngredientIndex from(String oreDict) {
		return new IngredientIndex(new OreIngredient(oreDict), 1);
	}

	public static IngredientIndex from(String oreDict, int count) {
		return new IngredientIndex(new OreIngredient(oreDict), count);
	}

	public static IngredientIndex from(SolidShape shape, Material material) {
		return from(shape, material, 1);
	}

	public static IngredientIndex from(SolidShape shape, Material material, int count) {
		return new IngredientIndex(
				new OreIngredient(
						shape.getName() + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, material.getName())),
				count);
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