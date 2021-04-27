package com.github.zi_jing.cuckoolib.data;

import java.util.Map;

import com.github.zi_jing.cuckoolib.item.MaterialItem;
import com.github.zi_jing.cuckoolib.material.MaterialEntry;
import com.github.zi_jing.cuckoolib.material.MaterialUtil;
import com.github.zi_jing.cuckoolib.material.SolidShape;
import com.github.zi_jing.cuckoolib.material.type.MaterialBase;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.data.TagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemTagsProvider extends ItemTagsProvider {
	public ModItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, String modId,
			ExistingFileHelper existingFileHelper) {
		super(dataGenerator, blockTagProvider, modId, existingFileHelper);
	}

	@Override
	protected void registerTags() {
		if (MaterialItem.REGISTERED_MATERIAL_ITEM.containsKey(this.modId)) {
			Map<MaterialEntry, MaterialItem> map = MaterialItem.REGISTERED_MATERIAL_ITEM.get(this.modId);
			SolidShape.REGISTRY.values().forEach((shape) -> {
				INamedTag<Item> tagShape = MaterialUtil.createForgeItemTag(shape.getName());
				TagsProvider.Builder<Item> shapeBuilder = this.getOrCreateBuilder(tagShape);
				MaterialBase.REGISTRY.values().forEach((material) -> {
					MaterialEntry entry = new MaterialEntry(shape, material);
					INamedTag<Item> tagEntry = MaterialUtil.createForgeItemTag(entry.toString());
					if (map.containsKey(entry)) {
						this.getOrCreateBuilder(tagEntry).addItemEntry(map.get(entry));
						shapeBuilder.addTag(tagEntry);
					}
				});
			});
		}
	}
}