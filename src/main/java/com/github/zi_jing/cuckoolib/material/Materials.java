package com.github.zi_jing.cuckoolib.material;

import com.github.zi_jing.cuckoolib.material.type.DustMaterial;
import com.github.zi_jing.cuckoolib.material.type.PlasticityMaterial;
import com.github.zi_jing.cuckoolib.material.type.StateMaterial;

public class Materials {
  public static final StateMaterial HYDROGEN =
      new StateMaterial(1, "hydrogen", 0x00ffff, -1, 0, -1);
  public static final StateMaterial HELIUM = new StateMaterial(2, "helium", 0xdddd00, -1, 0, -1);

  public static final PlasticityMaterial LITHIUM =
      new PlasticityMaterial(3, "lithium", 0xc8c8c8, -1, -1, -1);
  public static final PlasticityMaterial BERYLLIUM =
      new PlasticityMaterial(4, "beryllium", 0x64b464, -1, -1, -1);
  public static final PlasticityMaterial BORON =
      new PlasticityMaterial(5, "boron", 0xaad2d2, -1, -1, -1);
  public static final DustMaterial CARBON = new DustMaterial(6, "carbon", 0x333333, -1, -1, -1);
  public static final StateMaterial NITROGEN =
      new StateMaterial(7, "nitrogen", 0x50b4a0, -1, 0, -1);
  public static final StateMaterial OXYGEN = new StateMaterial(8, "oxygen", 0xa0c8fa, -1, 0, -1);
  public static final StateMaterial FLUORINE =
      new StateMaterial(9, "fluorine", 0xb4ffaa, -1, 0, -1);
  public static final StateMaterial NEON = new StateMaterial(10, "neon", 0xdd0000, -1, 0, -1);

  public static final PlasticityMaterial SODIUM =
      new PlasticityMaterial(11, "sodium", 0x000096, -1, -1, -1);
  public static final PlasticityMaterial MAGNESIUM =
      new PlasticityMaterial(12, "magnesium", 0xf09696, -1, -1, -1);
  public static final PlasticityMaterial ALUMINIUM =
      new PlasticityMaterial(13, "aluminium", 0x78c8f0, -1, -1, -1);
  public static final PlasticityMaterial SILICON =
      new PlasticityMaterial(14, "silicon", 0x3c3c50, -1, -1, -1);
  public static final DustMaterial PHOSPHOR =
      new DustMaterial(15, "phosphor", 0x821e1e, -1, -1, -1);
  public static final DustMaterial SULFUR = new DustMaterial(16, "sulfur", 0xc8c800, -1, -1, -1);
  public static final StateMaterial CHLORINE =
      new StateMaterial(17, "chlorine", 0xffffaa, -1, 0, -1);
  public static final StateMaterial ARGON = new StateMaterial(18, "argon", 0x32e600, -1, 0, -1);

  public static void register() {}
}
