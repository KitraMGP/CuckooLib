package com.github.zi_jing.cuckoolib.recipe;

import java.util.List;

import net.minecraft.item.ItemStack;

public class RecipeMatcher {
	public static final RecipeMatcher INSTANCE = new RecipeMatcher();

	private boolean[][] map;
	private boolean[] flag;
	private int[][] match;
	private int[] matchCount, inputCount, itemCount;

	private RecipeMatcher() {

	}

	public boolean validate(boolean isConsume, List<IngredientIndex> inputs, List<ItemStack> items) {
		this.init(inputs, items);
		for (int i = 0; i < inputs.size(); i++) {
			for (int j = 0; j < items.size(); j++) {
				if (inputs.get(i).getIngredient().apply(items.get(j))) {
					this.map[i][j] = true;
				}
			}
			this.inputCount[i] = inputs.get(i).getCount();
		}
		for (int j = 0; j < items.size(); j++) {
			this.itemCount[j] = items.get(j).getCount();
		}
		for (int i = 0; i < inputs.size(); i++) {
			this.flag = new boolean[items.size()];
			if (this.match(i, inputs.get(i).getCount()) > 0) {
				return false;
			}
		}
		if (isConsume) {
			for (int j = 0; j < items.size(); j++) {
				items.get(j).shrink(this.matchCount[j]);
			}
		}
		return true;
	}

	private int match(int i, int num) {
		if (num == 0) {
			for (int j = 0; j < this.matchCount.length; j++) {
				if (this.map[i][j]) {
					return 0;
				}
			}
			return 1;
		}
		for (int j = 0; j < this.matchCount.length; j++) {
			if (this.map[i][j] && !this.flag[j]) {
				if (this.itemCount[j] >= this.matchCount[j] + num) {
					if (this.itemCount[j] == this.matchCount[j] + num) {
						this.flag[j] = true;
					}
					this.match[j][i] += num;
					this.matchCount[j] += num;
					return 0;
				}
				this.flag[j] = true;
				int consume = this.itemCount[j] - this.matchCount[j];
				this.match[j][i] += consume;
				this.matchCount[j] += consume;
				num -= consume;
				for (int k = 0; k < this.match[j].length && num > 0; k++) {
					int oldMatch = this.match[j][k];
					if (oldMatch == 0 || k == i) {
						continue;
					}
					int move = oldMatch - this.match(k, Math.min(num, oldMatch));
					this.match[j][k] -= move;
					this.match[j][i] += move;
					num -= move;
				}
				if (num == 0) {
					break;
				}
			}
		}
		return num;
	}

	private void init(List<IngredientIndex> inputs, List<ItemStack> items) {
		this.map = new boolean[inputs.size()][items.size()];
		this.match = new int[items.size()][inputs.size()];
		this.matchCount = new int[items.size()];
		this.inputCount = new int[inputs.size()];
		this.itemCount = new int[items.size()];
	}
}