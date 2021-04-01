package com.github.zi_jing.cuckoolib.recipe.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.github.zi_jing.cuckoolib.recipe.IngredientIndex;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;

public class DataRecipeBuilder implements IFinishedRecipe {
	protected ResourceLocation id, advancementId;
	protected List<Consumer<JsonElement>> properties;
	protected List<IngredientIndex> inputs;
	protected List<OutputItemChanceEntry> outputs;
	protected Advancement.Builder advancement;

	private DataRecipeBuilder() {
		this.properties = new ArrayList<Consumer<JsonElement>>();
		this.inputs = new ArrayList<IngredientIndex>();
		this.outputs = new ArrayList<OutputItemChanceEntry>();
		this.advancement = Advancement.Builder.builder();
	}

	public static DataRecipeBuilder builder() {
		return new DataRecipeBuilder();
	}

	public DataRecipeBuilder addCriterion(String name, ICriterionInstance criterion) {
		this.advancement.withCriterion(name, criterion);
		return this;
	}

	public DataRecipeBuilder input(IngredientIndex... input) {
		return this.input(Arrays.asList(input));
	}

	public DataRecipeBuilder input(Collection<IngredientIndex> input) {
		this.inputs.addAll(input);
		return this;
	}

	public DataRecipeBuilder output(OutputItem... output) {
		return this.output(Arrays.asList(output));
	}

	public DataRecipeBuilder output(Collection<OutputItem> output) {
		this.outputs.addAll(output.stream().map((outputItem) -> new OutputItemChanceEntry(outputItem))
				.collect(Collectors.toList()));
		return this;
	}

	@Override
	public void serialize(JsonObject json) {
		JsonArray inputArray = new JsonArray();
		JsonArray outputArray = new JsonArray();
		this.inputs.forEach((index) -> inputArray.add(index.serialize()));
		this.outputs.forEach((entry) -> outputArray.add(entry.serialize()));
		json.add("inputs", inputArray);
		json.add("outputs", outputArray);
	}

	@Override
	public ResourceLocation getID() {
		return this.id;
	}

	@Override
	public IRecipeSerializer getSerializer() {
		return DataRecipeSerializer.INSTANCE.get();
	}

	@Override
	public JsonObject getAdvancementJson() {
		return this.advancement.serialize();
	}

	@Override
	public ResourceLocation getAdvancementID() {
		return new ResourceLocation(this.id.getNamespace(), "recipes/test/" + this.id.getPath());
	}

	public DataRecipeBuilder addProperty(Consumer<JsonElement> property) {
		this.properties.add(property);
		return this;
	}

	public void build(Consumer<IFinishedRecipe> out, ResourceLocation id) {
		this.build(out, id, "default");
	}

	public void build(Consumer<IFinishedRecipe> out, ResourceLocation id, String folder) {
		this.id = id;
		this.advancementId = new ResourceLocation(id.getNamespace(), "recipes/" + folder + "/" + id.getPath());
		this.advancement.withParentId(new ResourceLocation("recipes/root"))
				.withCriterion("has_recipe", RecipeUnlockedTrigger.create(id))
				.withRewards(AdvancementRewards.Builder.recipe(id)).withRequirementsStrategy(IRequirementsStrategy.OR);
		out.accept(this);
	}
}