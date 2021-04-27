package com.github.zi_jing.cuckoolib.material;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import com.github.zi_jing.cuckoolib.CuckooLib;
import com.github.zi_jing.cuckoolib.material.type.MaterialBase;
import com.github.zi_jing.cuckoolib.util.data.UnorderedRegistry;

import net.minecraft.client.resources.I18n;

public class SolidShape {
	public static final UnorderedRegistry<String, SolidShape> REGISTRY = new UnorderedRegistry<String, SolidShape>();

	private String name;
	private int unit;
	private Predicate<MaterialBase> materialPredicate;
	private Set<MaterialBase> ignoredMaterials, generatedMaterials;

	static {
		ModSolidShapes.register();
	}

	public SolidShape(String name, int unit, Predicate<MaterialBase> materialPredicate) {
		if (REGISTRY.containsKey(name)) {
			throw new IllegalStateException("Solid shape [ " + name + " ] has registered");
		}
		this.name = name;
		this.unit = unit;
		this.materialPredicate = materialPredicate;
		this.ignoredMaterials = new HashSet<MaterialBase>();
		this.generatedMaterials = new HashSet<MaterialBase>();
		this.register();
	}

	public static SolidShape getShapeByName(String name) {
		if (REGISTRY.containsKey(name)) {
			return REGISTRY.getValue(name);
		}
		return null;
	}

	public String getName() {
		return this.name;
	}

	public int getUnit() {
		return this.unit;
	}

	public void addGeneratedMaterial(MaterialBase... material) {
		this.generatedMaterials.addAll(Arrays.asList(material));
	}

	public void addIgnoredMaterial(MaterialBase... material) {
		this.ignoredMaterials.addAll(Arrays.asList(material));
	}

	public boolean generateMaterial(MaterialBase material) {
		return this.materialPredicate.test(material) && !this.ignoredMaterials.contains(material);
	}

	public String getUnlocalizedName() {
		return CuckooLib.MODID + ".shape." + this.name + ".name";
	}

	public String getLocalizedname(MaterialBase material) {
		return I18n.format(this.getUnlocalizedName(), material.getLocalizedName());
	}

	@Override
	public String toString() {
		return this.name;
	}

	public void register() {
		REGISTRY.register(this.name, this);
	}
}