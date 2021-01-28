package com.github.zi_jing.cuckoolib.material;

import org.apache.commons.lang3.Validate;

import com.github.zi_jing.cuckoolib.material.type.Material;

public class MaterialEntry {
	private SolidShape shape;
	private Material material;

	public MaterialEntry(SolidShape shape, Material material) {
		Validate.notNull(shape);
		Validate.notNull(material);
		this.shape = shape;
		this.material = material;
	}

	public SolidShape getShape() {
		return this.shape;
	}

	public Material getMaterial() {
		return this.material;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MaterialEntry)) {
			return false;
		}
		MaterialEntry entry = (MaterialEntry) obj;
		return this.shape == entry.shape && this.material == entry.material;
	}

	@Override
	public int hashCode() {
		return this.shape.hashCode() * 97 ^ this.material.hashCode();
	}

	@Override
	public String toString() {
		return this.shape.getName() + "/" + this.material.getName();
	}
}