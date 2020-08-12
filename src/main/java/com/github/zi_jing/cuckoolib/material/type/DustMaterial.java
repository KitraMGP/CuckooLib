package com.github.zi_jing.cuckoolib.material.type;

public class DustMaterial extends StateMaterial {
  public DustMaterial(int id, String name, int color) {
    super(id, name, color);
  }

  public DustMaterial(
      int id, String name, int color, float meltingPoint, float boilingPoint, float plasmaPoint) {
    super(id, name, color, meltingPoint, boilingPoint, plasmaPoint);
  }

  static {
    registerDefaultFlags(DustMaterial.class, GENERATE_DUST);
  }
}
