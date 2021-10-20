package com.github.zi_jing.cuckoolib.tool;

import net.minecraft.block.material.Material;

public class ToolUtil {
	private ToolUtil() {

	}

	public static boolean canPickaxeHarvest(Material material) {
		return material == Material.METAL || material == Material.HEAVY_METAL || material == Material.STONE;
	}

	public static boolean canAxeHarvest(Material material) {
		return material == Material.WOOD || material == Material.NETHER_WOOD || material == Material.PLANT
				|| material == Material.REPLACEABLE_PLANT || material == Material.WATER_PLANT
				|| material == Material.BAMBOO;
	}

	public static boolean canSwordHarvest(Material material) {
		return material == Material.PLANT || material == Material.REPLACEABLE_PLANT || material == Material.CORAL
				|| material == Material.LEAVES || material == Material.WEB || material == Material.WOOL
				|| material == Material.CACTUS || material == Material.CAKE || material == Material.EXPLOSIVE
				|| material == Material.SPONGE;
	}
}