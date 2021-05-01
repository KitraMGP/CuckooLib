package com.github.zi_jing.cuckoolib.network;

import com.github.zi_jing.cuckoolib.CuckooLib;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {
    public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(CuckooLib.MODID);

    public static void register() {
        NETWORK.registerMessage(new MessageGuiClient.Handler(), MessageGuiClient.class, 0, Side.SERVER);
        NETWORK.registerMessage(new MessageGuiServer.Handler(), MessageGuiServer.class, 1, Side.CLIENT);
        NETWORK.registerMessage(new MessageModularGuiOpen.Handler(), MessageModularGuiOpen.class, 2, Side.CLIENT);
    }
}