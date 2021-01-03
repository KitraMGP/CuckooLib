package com.github.zi_jing.cuckoolib.tool;

import net.minecraft.block.material.Material;

public class ToolUtil {
	private ToolUtil() {

	}

	public static boolean canPickaxeHarvest(Material material) {
		return material == Material.IRON || material == Material.ANVIL || material == Material.ROCK;
	}

	public static boolean canAxeHarvest(Material material) {
		return material == Material.WOOD || material == Material.NETHER_WOOD || material == Material.PLANTS
				|| material == Material.TALL_PLANTS || material == Material.BAMBOO || material == Material.GOURD;
	}
}