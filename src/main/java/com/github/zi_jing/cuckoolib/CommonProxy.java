package com.github.zi_jing.cuckoolib;

import com.github.zi_jing.cuckoolib.gui.ModularGuiInfo;
import com.github.zi_jing.cuckoolib.gui.TEGuiHolderCodec;
import com.github.zi_jing.cuckoolib.material.Materials;
import com.github.zi_jing.cuckoolib.material.SolidShapes;
import com.github.zi_jing.cuckoolib.network.NetworkHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CommonProxy {
	public void preInit(FMLPreInitializationEvent e) {
		Materials.register();
		SolidShapes.register();
		NetworkHandler.register();
		ModularGuiInfo.REGISTRY.register(0, TEGuiHolderCodec.REGISTRY_NAME, TEGuiHolderCodec.INSTANCE);
	}

	public void init(FMLInitializationEvent e) {

	}

	public void postInit(FMLPostInitializationEvent e) {

	}

	public IThreadListener getThreadListener(MessageContext context) {
		if (context.side.isServer()) {
			return context.getServerHandler().player.getServer();
		}
		throw new RuntimeException("Tried to get the client side IThreadListener from the server side");
	}

	public EntityPlayer getPlayer(MessageContext ctx) {
		if (ctx.side.isServer()) {
			return ctx.getServerHandler().player;
		}
		throw new RuntimeException("Tried to get the client side player from the server side");
	}
}