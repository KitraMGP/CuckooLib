package com.github.zi_jing.cuckoolib.recipe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class RecipeMap {
	private String unlocalizedName;
	private int minInput, maxInput, minOutput, maxOutput, minFluidInput, maxFluidInput, minFluidOutput, maxFluidOutput;
	private RecipeBuilder builder;
	private Map<ResourceLocation, Recipe> recipes;

	public RecipeMap(String unlocalizedName, int minInput, int maxInput, int minOutput, int maxOutput,
			int minFluidInput, int maxFluidInput, int minFluidOutput, int maxFluidOutput) {
		this(unlocalizedName, minInput, maxInput, minOutput, maxOutput, minFluidInput, maxFluidInput, minFluidOutput,
				maxFluidOutput, new RecipeBuilder());
	}

	public RecipeMap(String unlocalizedName, int minInput, int maxInput, int minOutput, int maxOutput,
			int minFluidInput, int maxFluidInput, int minFluidOutput, int maxFluidOutput, RecipeBuilder builder) {
		this.unlocalizedName = unlocalizedName;
		this.minInput = minInput;
		this.maxInput = Math.max(minInput, maxInput);
		this.minOutput = minOutput;
		this.maxOutput = Math.max(minOutput, maxOutput);
		this.minFluidInput = minFluidInput;
		this.maxFluidInput = Math.max(minFluidInput, maxFluidInput);
		this.minFluidOutput = minFluidOutput;
		this.maxFluidOutput = Math.max(minFluidOutput, maxFluidOutput);
		this.recipes = new HashMap<ResourceLocation, Recipe>();
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

	public void addRecipe(ResourceLocation id, Recipe recipe) {
		this.recipes.put(id, recipe);
	}

	public void removeRecipe(ResourceLocation id) {
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

	public List<Recipe> findAllRecipes(List<ItemStack> items, List<FluidStack> fluids) {
		List<Recipe> list = new ArrayList<Recipe>();
		Iterator<Recipe> ite = this.recipes.values().iterator();
		while (ite.hasNext()) {
			Recipe recipe = ite.next();
			if (recipe.matches(false, items, fluids)) {
				list.add(recipe);
			}
		}
		return list;
	}

	public RecipeBuilder builder() {
		return this.builder.copy();
	}
}