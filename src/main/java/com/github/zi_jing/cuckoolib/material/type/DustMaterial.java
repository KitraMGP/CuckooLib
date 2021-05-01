package com.github.zi_jing.cuckoolib.material.type;

public class DustMaterial extends StateMaterial {
    static {
        registerDefaultFlags(DustMaterial.class, GENERATE_DUST);
    }

    public DustMaterial(int id, String name, int color) {
        super(id, name, color);
    }

    public DustMaterial(int id, String name, int color, float meltingPoint, float boilingPoint, float plasmaPoint) {
        super(id, name, color, meltingPoint, boilingPoint, plasmaPoint);
    }
}