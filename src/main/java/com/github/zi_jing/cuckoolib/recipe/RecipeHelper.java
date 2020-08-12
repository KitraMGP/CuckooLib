package com.github.zi_jing.cuckoolib.recipe;

import com.github.zi_jing.cuckoolib.CuckooLib;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.lang.reflect.Field;

public class RecipeHelper {
  public static void addShapedCraftingRecipe(
      String modid, String name, ItemStack output, Object... input) {
    addShapedCraftingRecipe(modid, name, false, output, input);
  }

  public static void addShapedCraftingRecipe(
      String modid, String name, boolean isMirrored, ItemStack output, Object... input) {
    if (output.isEmpty()) {
      CuckooLib.getLogger().error("Crafting recipe result of [ " + name + " ] is empty");
      return;
    }

    IRecipe recipe =
        new ShapedOreRecipe(null, output.copy(), input)
            .setMirrored(isMirrored)
            .setRegistryName(modid, name);
    ForgeRegistries.RECIPES.register(recipe);
  }

  public static void addShapelessRecipe(
      String modid, String name, ItemStack output, Object... input) {
    if (output.isEmpty()) {
      CuckooLib.getLogger().error("Crafting recipe result of [ " + name + " ] is empty");
      return;
    }
    IRecipe recipe =
        new ShapelessOreRecipe(null, output.copy(), input).setRegistryName(modid, name);
    try {
      Field field = ShapelessOreRecipe.class.getDeclaredField("isSimple");
      field.setAccessible(true);
      field.setBoolean(recipe, false);
    } catch (ReflectiveOperationException e) {
      CuckooLib.getLogger().warn("Failed to set shapeless recipe [ " + name + " ] complex", e);
    }
    ForgeRegistries.RECIPES.register(recipe);
  }
}
