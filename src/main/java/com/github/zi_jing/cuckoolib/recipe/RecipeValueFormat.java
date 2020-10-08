package com.github.zi_jing.cuckoolib.recipe;

import java.util.function.Predicate;

public class RecipeValueFormat<T> {
	private String name;
	private Predicate<T> predicate;
	private T defaultValue;

	public RecipeValueFormat(String name) {
		this(name, (value) -> true, null);
	}

	public RecipeValueFormat(String name, Predicate<T> predicate, T defaultValue) {
		this.name = name;
		this.predicate = predicate;
		this.defaultValue = defaultValue;
	}

	public boolean match(String str) {
		return this.name.equals(str);
	}

	public boolean validate(T value) {
		return this.predicate.test(value);
	}

	public T getDefaultValue() {
		return this.defaultValue;
	}

	public String getName() {
		return this.name;
	}
}