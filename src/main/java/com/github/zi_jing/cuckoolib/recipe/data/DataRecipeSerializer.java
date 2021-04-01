package com.github.zi_jing.cuckoolib.recipe.data;

import com.github.zi_jing.cuckoolib.CuckooLib;
import com.google.gson.JsonObject;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class DataRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>>
		implements IRecipeSerializer<DataRecipe> {
	public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister
			.create(ForgeRegistries.RECIPE_SERIALIZERS, CuckooLib.MODID);

	public static final RegistryObject<DataRecipeSerializer> INSTANCE = RECIPE_SERIALIZERS.register("data_recipe",
			DataRecipeSerializer::new);

	@Override
	public DataRecipe read(ResourceLocation id, JsonObject json) {
		return new DataRecipe(Ingredient.deserialize(json.get("input")), OutputItem.fromJson(json.get("result")));
	}

	@Override
	public DataRecipe read(ResourceLocation id, PacketBuffer buffer) {
		return new DataRecipe(Ingredient.read(buffer), OutputItem.fromPacketBuffer(buffer));
	}

	@Override
	public void write(PacketBuffer buffer, DataRecipe recipe) {
		recipe.input.write(buffer);
		recipe.result.writeToPacketBuffer(buffer);
	}
}