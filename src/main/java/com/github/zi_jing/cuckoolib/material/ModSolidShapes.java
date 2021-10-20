package com.github.zi_jing.cuckoolib.material;

import java.util.function.Predicate;

import com.github.zi_jing.cuckoolib.material.type.IMaterialFlag;
import com.github.zi_jing.cuckoolib.material.type.MaterialBase;

public class ModSolidShapes {
	public static final SolidShape DUST = new SolidShape("dusts", 144, flag(MaterialBase.GENERATE_DUST));
	public static final SolidShape INGOT = new SolidShape("ingots", 144, flag(MaterialBase.GENERATE_INGOT));
	public static final SolidShape NUGGET = new SolidShape("nuggets", 16, flag(MaterialBase.GENERATE_NUGGET));
	public static final SolidShape PLATE = new SolidShape("plates", 144, flag(MaterialBase.GENERATE_PLATE));

	public static final SolidShape KNIFE_HEAD = new SolidShape("knife_heads", 144, flag(MaterialBase.GENERATE_TOOL));
	public static final SolidShape HAMMER_HEAD = new SolidShape("hammer_heads", 144, flag(MaterialBase.GENERATE_TOOL));
	public static final SolidShape CHISEL_HEAD = new SolidShape("chisel_heads", 144, flag(MaterialBase.GENERATE_TOOL));
	public static final SolidShape JAVELIN_HEAD = new SolidShape("javelin_heads", 144,
			flag(MaterialBase.GENERATE_TOOL));

	public static Predicate<MaterialBase> flag(IMaterialFlag flag) {
		return (material) -> material.hasFlag(flag);
	}

	public static void register() {
		DUST.addIgnoredMaterial(ModMaterials.REDSTONE);
		INGOT.addIgnoredMaterial(ModMaterials.IRON);
		INGOT.addIgnoredMaterial(ModMaterials.GOLD);
		NUGGET.addIgnoredMaterial(ModMaterials.IRON);
		NUGGET.addIgnoredMaterial(ModMaterials.GOLD);
	}
}