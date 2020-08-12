package com.github.zi_jing.cuckoolib.material;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import com.github.zi_jing.cuckoolib.CuckooLib;
import com.github.zi_jing.cuckoolib.material.type.Material;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.registry.RegistrySimple;

public class SolidShape {
  public static final RegistrySimple<String, SolidShape> REGISTRY =
      new RegistrySimple<String, SolidShape>();

  private String name;
  private int unit;
  private Predicate<Material> materialPredicate;
  private Set<Material> ignoredMaterials, generatedMaterials;
  private List<IMaterialRecipeRegister> recipeRegister;

  public SolidShape(String name, int unit, Predicate<Material> materialPredicate) {
    if (REGISTRY.containsKey(name)) {
      throw new IllegalStateException("Solid shape [ " + name + " ] has registered");
    }
    this.name = name;
    this.unit = unit;
    this.materialPredicate = materialPredicate;
    this.ignoredMaterials = new HashSet<Material>();
    this.generatedMaterials = new HashSet<Material>();
    this.recipeRegister = new ArrayList<IMaterialRecipeRegister>();
    REGISTRY.putObject(name, this);
  }

  public static SolidShape getShapeByName(String name) {
    if (REGISTRY.containsKey(name)) {
      return REGISTRY.getObject(name);
    }
    return null;
  }

  public void addRecipeRegister(IMaterialRecipeRegister register) {
    this.recipeRegister.add(register);
  }

  public void applyRecipe() {
    this.generatedMaterials.forEach(
        (material) ->
            this.recipeRegister.forEach((register) -> register.addRecipe(this, material)));
  }

  public String getName() {
    return this.name;
  }

  public int getUnit() {
    return this.unit;
  }

  public void addGeneratedMaterial(Material material) {
    this.generatedMaterials.add(material);
  }

  public void addIgnoredMaterial(Material material) {
    this.ignoredMaterials.add(material);
  }

  public boolean generateMaterial(Material material) {
    return this.materialPredicate.test(material) && !this.ignoredMaterials.contains(material);
  }

  public String getUnlocalizedName() {
    return CuckooLib.MODID + ".shape." + this.name + ".name";
  }

  public String getLocalizedname(Material material) {
    return I18n.format(this.getUnlocalizedName(), material.getLocalizedName());
  }
}
