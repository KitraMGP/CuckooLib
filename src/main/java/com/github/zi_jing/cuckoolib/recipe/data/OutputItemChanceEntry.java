package com.github.zi_jing.cuckoolib.recipe.data;

import java.util.Random;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.util.JSONUtils;
import net.minecraft.util.math.MathHelper;

public class OutputItemChanceEntry {
	private OutputItem output;
	private float chance;

	public OutputItemChanceEntry(OutputItem output) {
		this(output, 1);
	}

	public OutputItemChanceEntry(OutputItem output, float chance) {
		this.output = output;
		this.chance = MathHelper.clamp(chance, 0, 1);
	}

	public static OutputItemChanceEntry deserialize(JsonElement element) {
		JsonObject json = element.getAsJsonObject();
		return new OutputItemChanceEntry(OutputItem.fromJson(json.get("output")), JSONUtils.getFloat(json, "chance"));
	}

	public OutputItem getOutputItem() {
		return this.output;
	}

	public double getChance() {
		return this.chance;
	}

	public boolean apply(Random rand) {
		return rand.nextDouble() < this.chance;
	}

	public JsonElement serialize() {
		JsonObject json = new JsonObject();
		json.add("output", this.output.serialize());
		json.addProperty("chance", this.chance);
		return json;
	}
}