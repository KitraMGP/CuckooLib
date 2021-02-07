package com.github.zi_jing.cuckoolib.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;

public class Recipe {
	protected RecipeMap recipeMap;
	protected List<IngredientIndex> inputs;
	protected List<ChanceEntry> outputs;
	protected List<FluidStack> fluidInputs, fluidOutputs;
	protected Map<IRecipeProperty, Object> properties;

	public Recipe(RecipeMap recipeMap, List<IngredientIndex> inputs, List<ChanceEntry> outputs,
			List<FluidStack> fluidInputs, List<FluidStack> fluidOutputs, Map<IRecipeProperty, Object> properties) {
		this.recipeMap = recipeMap;
		this.inputs = NonNullList.<IngredientIndex>create();
		this.inputs.addAll(inputs);
		this.outputs = NonNullList.<ChanceEntry>create();
		this.outputs.addAll(outputs);
		this.fluidInputs = ImmutableList.<FluidStack>copyOf(fluidInputs);
		this.fluidOutputs = ImmutableList.<FluidStack>copyOf(fluidOutputs);
		this.properties = ImmutableMap.<IRecipeProperty, Object>copyOf(properties);
	}

	public RecipeMap getRecipeMap() {
		return this.recipeMap;
	}

	public List<IngredientIndex> getInputs() {
		return this.inputs;
	}

	public List<ChanceEntry> getOutputs() {
		return this.outputs;
	}

	public List<FluidStack> getFluidInputs() {
		return this.fluidInputs;
	}

	public List<FluidStack> getFluidOutputs() {
		return this.fluidOutputs;
	}

	public Map<IRecipeProperty, Object> getPropertyValues() {
		return this.properties;
	}

	public List<ItemStack> createOutput() {
		return this.createOutput(new Random());
	}

	public List<ItemStack> createOutput(Random rand) {
		List<ItemStack> output = new ArrayList<ItemStack>();
		for (ChanceEntry entry : this.outputs) {
			if (entry.getChance() == 1 || (entry.getChance() != 0 && entry.apply(rand))) {
				output.add(entry.getItemStack().copy());
			}
		}
		return output;
	}

	public boolean matches(boolean isConsume, List<ItemStack> items, List<FluidStack> fluids) {
		int[] fluidUnit = new int[fluids.size()];
		for (int i = 0; i < fluids.size(); i++) {
			FluidStack fluid = fluids.get(i);
			fluidUnit[i] = fluid == null ? 0 : fluid.getAmount();
		}
		for (FluidStack input : this.fluidInputs) {
			int require = input.getAmount();
			if (require == 0) {
				require = -1;
			}
			for (int i = 0; i < fluids.size() && require != 0; i++) {
				FluidStack fluid = fluids.get(i);
				if (fluid == null || !input.isFluidEqual(fluid)) {
					continue;
				}
				if (require < 0) {
					require = 0;
					break;
				}
				int consume = Math.min(require, fluidUnit[i]);
				require -= consume;
				fluidUnit[i] -= consume;
			}
			if (require != 0) {
				return false;
			}
		}
		if (!RecipeMatcher.INSTANCE.validate(isConsume, this.inputs, items)) {
			return false;
		}
		if (isConsume) {
			for (int i = 0; i < fluids.size(); i++) {
				FluidStack fluid = fluids.get(i);
				int unit = fluidUnit[i];
				if (fluid == null || fluid.getAmount() == unit) {
					continue;
				}
				fluid.setAmount(unit);
				if (fluid.getAmount() == 0) {
					fluids.set(i, null);
				}
			}
		}
		return true;
	}

	public <T> boolean containsProperty(IRecipeProperty<T> property) {
		return this.properties.containsKey(property);
	}

	public <T> T getPropertyValue(IRecipeProperty<T> property) {
		if (this.properties.containsKey(property)) {
			return (T) this.properties.get(property);
		}
		return null;
	}
}