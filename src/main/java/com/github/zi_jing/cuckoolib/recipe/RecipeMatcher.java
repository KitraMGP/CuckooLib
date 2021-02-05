package com.github.zi_jing.cuckoolib.recipe;

import java.util.List;

import net.minecraft.item.ItemStack;

public class RecipeMatcher {
	public static final RecipeMatcher INSTANCE = new RecipeMatcher();

	private boolean[][] map;
	private boolean[] flag;
	private int[][] match;
	private int[] matchCount, itemCount;

	private RecipeMatcher() {

	}

	public boolean validate(boolean isConsume, List<IngredientIndex> inputs, List<ItemStack> items) {
		this.init(inputs, items);
		for (int i = 0; i < inputs.size(); i++) {
			for (int j = 0; j < items.size(); j++) {
				if (inputs.get(i).getIngredient().test(items.get(j))) {
					this.map[i][j] = true;
				}
			}
		}
		for (int j = 0; j < items.size(); j++) {
			this.itemCount[j] = items.get(j).getCount();
		}
		for (int i = 0; i < inputs.size(); i++) {
			int size = inputs.get(i).getCount();
			if (size == 0) {
				boolean result = false;
				for (int j = 0; j < this.matchCount.length; j++) {
					if (this.map[i][j]) {
						result = true;
					}
				}
				if (!result) {
					return false;
				}
			} else {
				for (int k = 0; k < size; k++) {
					this.flag = new boolean[items.size()];
					if (!this.match(i)) {
						return false;
					}
				}
			}
		}
		if (isConsume) {
			for (int j = 0; j < items.size(); j++) {
				items.get(j).shrink(this.matchCount[j]);
			}
		}
		return true;
	}

	private boolean match(int i) {
		for (int j = 0; j < this.matchCount.length; j++) {
			if (this.map[i][j] && !this.flag[j]) {
				this.flag[j] = true;
				if (this.itemCount[j] > this.matchCount[j] || this.match(j)) {
					this.match[j][i]++;
					this.matchCount[j]++;
					return true;
				}
			}
		}
		return false;
	}

	private void init(List<IngredientIndex> inputs, List<ItemStack> items) {
		this.map = new boolean[inputs.size()][items.size()];
		this.match = new int[items.size()][inputs.size()];
		this.matchCount = new int[items.size()];
		this.itemCount = new int[items.size()];
	}
}