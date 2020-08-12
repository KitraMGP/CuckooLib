package com.github.zi_jing.cuckoolib.metaitem;

import java.util.ArrayList;
import java.util.List;

import com.github.zi_jing.cuckoolib.material.SolidShape;
import com.github.zi_jing.cuckoolib.material.type.Material;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MaterialMetaItem extends MetaItem {
  private TIntObjectMap<SolidShape> solidShapeMap;
  private boolean isMetadataGenerated;
  private List<Short> validMetadata;

  public MaterialMetaItem(String modid, String name, SolidShape... shape) {
    super(modid, name);
    this.solidShapeMap = new TIntObjectHashMap<SolidShape>();
    this.validMetadata = new ArrayList<Short>();
    if (shape.length > 31) {
      throw new IllegalArgumentException("The maximum Solid Shape count is 31");
    }
    for (int i = 0; i < shape.length; i++) {
      this.solidShapeMap.put(i, shape[i]);
    }
  }

  @Override
  public MetaValueItem addItem(int id, String unlocalizedName) {
    throw new UnsupportedOperationException("Don't add MetaValueItem to MaterialMetaItem");
  }

  @Override
  public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
    if (this.isInCreativeTab(tab)) {
      this.getValidMetadata().forEach((metadata) -> items.add(new ItemStack(this, 1, metadata)));
    }
  }

  @Override
  @SideOnly(Side.CLIENT)
  protected int getItemColor(ItemStack stack, int tintIndex) {
    if (tintIndex == 0) {
      Material material = this.getItemMaterial(stack);
      if (material != null) {
        return material.getColor();
      }
    }
    return 0xffffff;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void registerItemModel() {
    this.solidShapeMap.forEachEntry(
        (id, shape) -> {
          ModelBakery.registerItemVariants(
              this, new ResourceLocation(this.modid, "metaitem/material/" + shape.getName()));
          return true;
        });
    ModelLoader.setCustomMeshDefinition(
        this,
        (stack) -> {
          short metadata = (short) stack.getMetadata();
          if (!this.getValidMetadata().contains(metadata)) {
            return ModelBakery.MODEL_MISSING;
          }
          SolidShape shape = this.getItemSolidShape(metadata);
          return new ModelResourceLocation(
              new ResourceLocation(this.modid, "metaitem/material/" + shape.getName()),
              "inventory");
        });
  }

  public SolidShape getItemSolidShape(short metadata) {
    return this.solidShapeMap.get(metadata / 1024);
  }

  public SolidShape getItemSolidShape(ItemStack stack) {
    return this.getItemSolidShape((short) stack.getMetadata());
  }

  public Material getItemMaterial(short metadata) {
    return Material.REGISTRY.getObjectById(metadata % 1024);
  }

  public Material getItemMaterial(ItemStack stack) {
    return this.getItemMaterial((short) stack.getMetadata());
  }

  public List<Short> getValidMetadata() {
    if (!this.isMetadataGenerated) {
      this.validMetadata.clear();
      this.solidShapeMap.forEachEntry(
          (id, shape) -> {
            if (shape != null) {
              for (Material material : Material.REGISTRY) {
                if (shape.generateMaterial(material)) {
                  this.validMetadata.add((short) (id * 1024 + material.getId()));
                }
              }
            }
            return true;
          });
      this.isMetadataGenerated = true;
    }
    return this.validMetadata;
  }

  @Override
  public String getUnlocalizedName(ItemStack stack) {
    return "meta_item." + this.modid + ".material";
  }

  @Override
  public String getItemStackDisplayName(ItemStack stack) {
    SolidShape shape = this.getItemSolidShape(stack);
    Material material = this.getItemMaterial(stack);
    if (shape != null && material != null) {
      return this.getItemSolidShape(stack).getLocalizedname(this.getItemMaterial(stack));
    }
    return "";
  }
}
