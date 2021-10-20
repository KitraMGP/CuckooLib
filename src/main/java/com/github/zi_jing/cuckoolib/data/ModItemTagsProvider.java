package com.github.zi_jing.cuckoolib.data;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemTagsProvider extends ItemTagsProvider {
	public ModItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, String modId,
			ExistingFileHelper existingFileHelper) {
		super(dataGenerator, blockTagProvider, modId, existingFileHelper);
	}

	@Override
	protected void addTags() {

	}
}