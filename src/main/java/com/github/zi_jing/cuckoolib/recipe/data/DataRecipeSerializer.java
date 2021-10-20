package com.github.zi_jing.cuckoolib.recipe.data;

import com.google.gson.JsonObject;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class DataRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>>
		implements IRecipeSerializer<DataRecipe> {
	@Override
	public DataRecipe fromJson(ResourceLocation id, JsonObject json) {
		return new DataRecipe(Ingredient.fromJson(json.get("input")), OutputItem.fromJson(json.get("result")));
	}

	@Override
	public DataRecipe fromNetwork(ResourceLocation id, PacketBuffer buffer) {
		return new DataRecipe(Ingredient.fromNetwork(buffer), OutputItem.fromPacketBuffer(buffer));
	}

	@Override
	public void toNetwork(PacketBuffer buffer, DataRecipe recipe) {
		recipe.input.toNetwork(buffer);
		recipe.result.writeToPacketBuffer(buffer);
	}
}