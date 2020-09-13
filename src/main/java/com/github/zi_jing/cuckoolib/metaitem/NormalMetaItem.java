package com.github.zi_jing.cuckoolib.metaitem;

public class NormalMetaItem extends MetaItem<MetaValueItem> {
	public NormalMetaItem(String modid, String name) {
		super(modid, name);
	}

	@Override
	protected MetaValueItem createMetaValueItem(short id, String unlocalizedName) {
		return new MetaValueItem(this, id, unlocalizedName);
	}
}