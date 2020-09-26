package com.github.zi_jing.cuckoolib.material;

import java.util.function.Predicate;

import com.github.zi_jing.cuckoolib.material.type.IMaterialFlag;
import com.github.zi_jing.cuckoolib.material.type.Material;
import com.github.zi_jing.cuckoolib.util.ArraysUtil;

public class SolidShapes {
	public static final SolidShape DUST = new SolidShape("dust", 144, flag(Material.GENERATE_DUST));

	public static Predicate<Material> flag(IMaterialFlag flag) {
		return (material) -> material.hasFlag(flag);
	}

	public static void register() {
		ArraysUtil.registerAll(DUST);
	}
}