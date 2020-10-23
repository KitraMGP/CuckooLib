package com.github.zi_jing.cuckoolib.material.type;

public class GemMaterial extends DustMaterial {
	public GemMaterial(int id, String name, int color) {
		super(id, name, color);
	}

	public GemMaterial(int id, String name, int color, float meltingPoint, float boilingPoint, float plasmaPoint) {
		super(id, name, color, meltingPoint, boilingPoint, plasmaPoint);
	}

	static {
		registerDefaultFlags(GemMaterial.class, GENERATE_DUST, GENERATE_CRYSTAL, GENERATE_ROD, GENERATE_GEAR,
				GENERATE_SCREW, GENERATE_RING);
	}
}
