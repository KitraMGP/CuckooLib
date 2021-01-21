package com.github.zi_jing.cuckoolib.recipe;

import net.minecraft.util.ResourceLocation;

public interface IRecipeProperty<T> {
	ResourceLocation getUnlocalizedName();

	boolean validateValue(T value);

	T getDefaultValue();
}