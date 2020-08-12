package com.github.zi_jing.cuckoolib.metaitem;

import net.minecraft.util.ResourceLocation;

public class ToolMetaItem extends MetaItem {
	public ToolMetaItem(String modid, String name) {
		super(modid, name);
	}

	public ToolMetaItem(ResourceLocation registryName) {
		super(registryName);
	}
}