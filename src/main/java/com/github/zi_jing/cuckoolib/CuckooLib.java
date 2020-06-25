package com.github.zi_jing.cuckoolib;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = CuckooLib.MODID, name = CuckooLib.NAME, version = CuckooLib.VERSION, useMetadata = true)
public class CuckooLib {
    public static final String MODID = "cuckoolib";
    public static final String NAME = "CuckooLib";
    public static final String VERSION = "@CUCKOO_LIB_VERSION@";

    @Mod.Instance
    public static CuckooLib instance;

    private static Logger logger;

    public static Logger getLogger() {
        return logger;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }
}
