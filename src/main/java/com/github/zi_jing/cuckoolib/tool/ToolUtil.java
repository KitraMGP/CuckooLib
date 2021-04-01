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

	public static boolean canSwordHarvest(Material material) {
		return material == Material.PLANTS || material == Material.TALL_PLANTS || material == Material.CORAL
				|| material == Material.GOURD || material == Material.LEAVES || material == Material.WEB
				|| material == Material.CARPET || material == Material.CACTUS || material == Material.CAKE
				|| material == Material.TNT || material == Material.SPONGE;
	}
}