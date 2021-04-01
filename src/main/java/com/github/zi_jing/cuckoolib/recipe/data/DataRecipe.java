package com.github.zi_jing.cuckoolib.recipe.data;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class DataRecipe implements IRecipe<IInventory> {
	protected ResourceLocation id;
	protected IRecipeType<DataRecipe> type;
	protected Ingredient input;
	protected OutputItem result;

	public DataRecipe(Ingredient input, OutputItem result) {
		this.input = input;
		this.result = result;
	}

	@Override
	public boolean isDynamic() {
		return true;
	}

	@Override
	public boolean matches(IInventory inv, World worldIn) {
		return false;
	}

	@Override
	public ItemStack getCraftingResult(IInventory inv) {
		return this.result.get();
	}

	@Override
	public boolean canFit(int width, int height) {
		return true;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return this.result.get();
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	@Override
	public IRecipeSerializer getSerializer() {
		return null;
	}

	@Override
	public IRecipeType getType() {
		return this.type;
	}
}