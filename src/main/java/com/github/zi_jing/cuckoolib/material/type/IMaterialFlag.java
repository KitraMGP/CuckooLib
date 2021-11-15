package com.github.zi_jing.cuckoolib.material.type;

@FunctionalInterface
public interface IMaterialFlag {
	int getId();

	public default long getValue() {
		return 1 << this.getId();
	}

	public default void setFlagForMaterial(MaterialBase... materials) {
		for (MaterialBase material : materials) {
			material.addFlag(this);
		}
	}
}