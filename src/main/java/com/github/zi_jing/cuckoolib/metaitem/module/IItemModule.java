package com.github.zi_jing.cuckoolib.metaitem.module;

public interface IItemModule {
	Class<? extends IItemModule> getRegistryID();
}