package com.github.zi_jing.cuckoolib.recipe;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;

import java.util.*;
import java.util.Map.Entry;

public class Recipe {
    protected RecipeMap recipeMap;
    protected List<IngredientIndex> inputs;
    protected List<ChanceEntry> outputs;
    protected List<FluidStack> fluidInputs, fluidOutputs;
    protected Map<RecipeValueFormat, Object> values;

    public Recipe(RecipeMap recipeMap, List<IngredientIndex> inputs, List<ChanceEntry> outputs,
                  List<FluidStack> fluidInputs, List<FluidStack> fluidOutputs) {
        this.recipeMap = recipeMap;
        this.inputs = NonNullList.<IngredientIndex>create();
        this.inputs.addAll(inputs);
        this.outputs = NonNullList.<ChanceEntry>create();
        this.outputs.addAll(outputs);
        this.fluidInputs = ImmutableList.<FluidStack>copyOf(fluidInputs);
        this.fluidOutputs = ImmutableList.<FluidStack>copyOf(fluidOutputs);
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

    public Map<RecipeValueFormat, Object> getValues() {
        return this.values;
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
            fluidUnit[i] = fluid == null ? 0 : fluid.amount;
        }
        for (FluidStack input : this.fluidInputs) {
            int require = input.amount;
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
                if (fluid == null || fluid.amount == unit) {
                    continue;
                }
                fluid.amount = unit;
                if (fluid.amount == 0) {
                    fluids.set(i, null);
                }
            }
        }
        return true;
    }

    public RecipeValueFormat findFormat(String name) {
        Iterator<Entry<RecipeValueFormat, Object>> ite = this.values.entrySet().iterator();
        while (ite.hasNext()) {
            Entry<RecipeValueFormat, Object> entry = ite.next();
            if (entry.getKey().match(name)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public <T> T getValue(RecipeValueFormat<T> format) {
        if (this.values.containsKey(format)) {
            return (T) this.values.get(format);
        }
        return null;
    }

    public <T> T getValue(String name) {
        RecipeValueFormat format = this.findFormat(name);
        if (format == null) {
            return null;
        }
        if (this.values.containsKey(format)) {
            return (T) this.values.get(format);
        }
        return null;
    }
}