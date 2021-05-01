package com.github.zi_jing.cuckoolib.material;

import com.github.zi_jing.cuckoolib.CuckooLib;
import com.github.zi_jing.cuckoolib.material.type.Material;
import com.github.zi_jing.cuckoolib.util.IRegistrable;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.registry.RegistrySimple;

import java.util.*;
import java.util.function.Predicate;

public class SolidShape implements IRegistrable {
    public static final RegistrySimple<String, SolidShape> REGISTRY = new RegistrySimple<String, SolidShape>();

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
        this.register();
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
        this.generatedMaterials
                .forEach((material) -> this.recipeRegister.forEach((register) -> register.addRecipe(this, material)));
    }

    public String getName() {
        return this.name;
    }

    public int getUnit() {
        return this.unit;
    }

    public void addGeneratedMaterial(Material... material) {
        this.generatedMaterials.addAll(Arrays.asList(material));
    }

    public void addIgnoredMaterial(Material... material) {
        this.ignoredMaterials.addAll(Arrays.asList(material));
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

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public void register() {
        REGISTRY.putObject(this.name, this);
    }
}