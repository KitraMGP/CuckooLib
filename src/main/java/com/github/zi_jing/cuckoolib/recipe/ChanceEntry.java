package com.github.zi_jing.cuckoolib.recipe;

import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class ChanceEntry {
	private ItemStack stack;
	private double chance;

	public ChanceEntry(ItemStack stack) {
		this(stack, 1);
	}

	public ChanceEntry(ItemStack stack, double chance) {
		this.stack = stack.copy();
		this.chance = MathHelper.clamp(chance, 0, 1);
	}

	public ItemStack getItemStack() {
		return this.stack;
	}

	public double getChance() {
		return this.chance;
	}

	public boolean apply(Random rand) {
		return rand.nextDouble() < this.chance;
	}
}