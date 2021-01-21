package com.github.zi_jing.cuckoolib.recipe;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class RecipeMap {
	private String unlocalizedName;
	private int minInput, maxInput, minOutput, maxOutput, minFluidInput, maxFluidInput, minFluidOutput, maxFluidOutput;
	private RecipeBuilder builder;
	private Map<Integer, Recipe> recipes;

	public RecipeMap(String unlocalizedName, int minInput, int maxInput, int minOutput, int maxOutput,
			int minFluidInput, int maxFluidInput, int minFluidOutput, int maxFluidOutput) {
		this.unlocalizedName = unlocalizedName;
		this.minInput = minInput;
		this.maxInput = Math.max(minInput, maxInput);
		this.minOutput = minOutput;
		this.maxOutput = Math.max(minOutput, maxOutput);
		this.minFluidInput = minFluidInput;
		this.maxFluidInput = Math.max(minFluidInput, maxFluidInput);
		this.minFluidOutput = minFluidOutput;
		this.maxFluidOutput = Math.max(minFluidOutput, maxFluidOutput);
		this.recipes = new HashMap<Integer, Recipe>();
	}

	public RecipeMap(String unlocalizedName, int minInput, int maxInput, int minOutput, int maxOutput,
			int minFluidInput, int maxFluidInput, int minFluidOutput, int maxFluidOutput, RecipeBuilder builder) {
		this(unlocalizedName, minInput, maxInput, minOutput, maxOutput, minFluidInput, maxFluidInput, minFluidOutput,
				maxFluidOutput);
		this.builder = builder;
		builder.recipeMap = this;
	}

	public String getUnlocalizedName() {
		return this.unlocalizedName;
	}

	public Collection<Recipe> getRecipes() {
		return Collections.unmodifiableCollection(this.recipes.values());
	}

	public int getMinInput() {
		return this.minInput;
	}

	public int getMaxInput() {
		return this.maxInput;
	}

	public int getMinOutput() {
		return this.minOutput;
	}

	public int getMaxOutput() {
		return this.maxOutput;
	}

	public int getMinFluidInput() {
		return this.minFluidInput;
	}

	public int getMaxFluidInput() {
		return this.maxFluidInput;
	}

	public int getMinFluidOutput() {
		return this.minFluidOutput;
	}

	public int getMaxFluidOutput() {
		return this.maxFluidOutput;
	}

	public void addRecipe(int id, Recipe recipe) {
		this.recipes.put(id, recipe);
	}

	public void removeRecipe(int id) {
		if (this.recipes.containsKey(id)) {
			this.recipes.remove(id);
		}
	}

	public Recipe findRecipe(boolean isConsume, List<ItemStack> items, List<FluidStack> fluids) {
		Iterator<Recipe> ite = this.recipes.values().iterator();
		while (ite.hasNext()) {
			Recipe recipe = ite.next();
			if (recipe.matches(isConsume, items, fluids)) {
				return recipe;
			}
		}
		return null;
	}

	public RecipeBuilder builder() {
		return this.builder.copy();
	}
}