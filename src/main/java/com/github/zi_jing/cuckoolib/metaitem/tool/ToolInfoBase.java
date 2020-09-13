package com.github.zi_jing.cuckoolib.metaitem.tool;

import com.github.zi_jing.cuckoolib.material.type.Material;

public abstract class ToolInfoBase implements IToolInfo {
	@Override
	public boolean validateMaterial(Material material) {
		return material.hasFlag(Material.GENERATE_TOOL);
	}

	@Override
	public int getBlockBreakDamage() {
		return 200;
	}

	@Override
	public int getInteractionDamage() {
		return 100;
	}

	@Override
	public int getContainerCraftDamage() {
		return 800;
	}

	@Override
	public int getEntityHitDamage() {
		return 100;
	}
}