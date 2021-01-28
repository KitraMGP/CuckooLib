package com.github.zi_jing.cuckoolib.recipe;

import java.util.function.Predicate;

import net.minecraft.util.ResourceLocation;

public class NormalReicpeProperty<T> implements IRecipeProperty<T> {
	private ResourceLocation unlocalizedName;
	private Predicate<T> validate;
	private T defaultValue;

	public NormalReicpeProperty(ResourceLocation unlocalizedName, T defaultValue) {
		this(unlocalizedName, (value) -> true, defaultValue);
	}

	public NormalReicpeProperty(ResourceLocation unlocalizedName, Predicate<T> validate, T defaultValue) {
		if (!validate.test(defaultValue)) {
			throw new IllegalArgumentException("The default value for RecipeProperty is invalid");
		}
		this.unlocalizedName = unlocalizedName;
		this.validate = validate;
		this.defaultValue = defaultValue;
	}

	@Override
	public ResourceLocation getUnlocalizedName() {
		return this.unlocalizedName;
	}

	@Override
	public boolean validateValue(T value) {
		return this.validate.test(value);
	}

	@Override
	public T getDefaultValue() {
		return this.getDefaultValue();
	}
}