package com.github.zi_jing.cuckoolib.material.type;

public class PlasticityMaterial extends DustMaterial {
    static {
        registerDefaultFlags(PlasticityMaterial.class, GENERATE_DUST, GENERATE_PLATE, GENERATE_ROD, GENERATE_GEAR,
                GENERATE_INGOT, GENERATE_FOIL, GENERATE_SCREW, GENERATE_SPRING, GENERATE_RING, GENERATE_WIRE,
                GENERATE_ROTOR);
    }

    public PlasticityMaterial(int id, String name, int color) {
        super(id, name, color);
    }

    public PlasticityMaterial(int id, String name, int color, float meltingPoint, float boilingPoint,
                              float plasmaPoint) {
        super(id, name, color, meltingPoint, boilingPoint, plasmaPoint);
    }
}