package com.github.zi_jing.cuckoolib;

import com.github.zi_jing.cuckoolib.material.Materials;
import com.github.zi_jing.cuckoolib.material.SolidShapes;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	public void preInit(FMLPreInitializationEvent e) {
		Materials.register();
		SolidShapes.register();
	}

	public void init(FMLInitializationEvent e) {

	}

	public void postInit(FMLPostInitializationEvent e) {

	}
}