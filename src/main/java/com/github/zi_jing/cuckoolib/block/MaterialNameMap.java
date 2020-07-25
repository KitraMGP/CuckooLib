package com.github.zi_jing.cuckoolib.block;

import com.github.zi_jing.cuckoolib.CuckooLib;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.block.material.Material;

public class MaterialNameMap {
    public static final BiMap<Material, String> MAP = HashBiMap.create(40);
    public static final BiMap<String, Material> INVERSED_MAP = MAP.inverse();

    static {
        MAP.put(Material.AIR, "air");
        MAP.put(Material.ANVIL, "anvil");
        MAP.put(Material.BARRIER, "barrier");
        MAP.put(Material.CACTUS, "cactus");
        MAP.put(Material.CAKE, "cake");
        MAP.put(Material.CARPET, "carpet");
        MAP.put(Material.CIRCUITS, "circuits");
        MAP.put(Material.CLAY, "clay");
        MAP.put(Material.CLOTH, "cloth");
        MAP.put(Material.CORAL, "coral");
        MAP.put(Material.CRAFTED_SNOW, "crafted_snow");
        MAP.put(Material.DRAGON_EGG, "dragon_egg");
        MAP.put(Material.FIRE, "fire");
        MAP.put(Material.GLASS, "glass");
        MAP.put(Material.GROUND, "ground");
        MAP.put(Material.ICE, "ice");
        MAP.put(Material.IRON, "iron");
        MAP.put(Material.LAVA, "lava");
        MAP.put(Material.LEAVES, "leaves");
        MAP.put(Material.PACKED_ICE, "packed_ice");
        MAP.put(Material.PISTON, "piston");
        MAP.put(Material.PLANTS, "plants");
        MAP.put(Material.PORTAL, "portal");
        MAP.put(Material.REDSTONE_LIGHT, "redstone_light");
        MAP.put(Material.ROCK, "rock");
        MAP.put(Material.SAND, "sand");
        MAP.put(Material.SNOW, "snow");
        MAP.put(Material.SPONGE, "sponge");
        MAP.put(Material.STRUCTURE_VOID, "structure_void");
        MAP.put(Material.TNT, "tnt");
        MAP.put(Material.VINE, "vine");
        MAP.put(Material.WATER, "water");
        MAP.put(Material.WEB, "web");
        MAP.put(Material.WOOD, "wood");
        CuckooLib.getLogger().info(MAP.size() + " material names mapped");
    }

    public static String getMaterialName(Material material) {
        return MAP.get(material);
    }

    public static Material getMaterial(String name) {
        return INVERSED_MAP.get(name);
    }
}