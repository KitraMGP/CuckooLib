package com.github.zi_jing.cuckoolib.client.util;

import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraftforge.client.event.ModelRegistryEvent;

public interface IStateMapperModel {
	IStateMapper getStateMapper(ModelRegistryEvent e);
}