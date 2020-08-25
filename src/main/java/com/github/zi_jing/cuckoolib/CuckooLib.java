package com.github.zi_jing.cuckoolib;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = CuckooLib.MODID, name = CuckooLib.NAME, version = CuckooLib.VERSION, useMetadata = true)
public class CuckooLib {
	public static final String MODID = "cuckoolib";
	public static final String NAME = "CuckooLib";
	public static final String VERSION = "@CUCKOO_LIB_VERSION@";

	@Mod.Instance
	public static CuckooLib instance;

	@SidedProxy(clientSide = "com.github.zi_jing.cuckoolib.ClientProxy", serverSide = "com.github.zi_jing.cuckoolib.CommonProxy")
	public static CommonProxy proxy = new CommonProxy();

	private static Logger logger;

	public static Logger getLogger() {
		return logger;
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		proxy.preInit(event);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);

	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}
}
