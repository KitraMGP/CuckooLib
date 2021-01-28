package com.github.zi_jing.cuckoolib.tag;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockTagsProvider extends BlockTagsProvider {
	public ModBlockTagsProvider(DataGenerator generatorIn, String modId, ExistingFileHelper existingFileHelper) {
		super(generatorIn, modId, existingFileHelper);
	}

	@Override
	protected void registerTags() {

	}
}