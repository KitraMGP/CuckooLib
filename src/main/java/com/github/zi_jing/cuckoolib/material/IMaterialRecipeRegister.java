package com.github.zi_jing.cuckoolib.material;

import com.github.zi_jing.cuckoolib.material.type.Material;

public interface IMaterialRecipeRegister {
	void addRecipe(SolidShape shape, Material material);
}