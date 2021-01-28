package com.github.zi_jing.cuckoolib;

import com.github.zi_jing.cuckoolib.gui.CapabilityListener;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = CuckooLib.MODID, bus = Bus.FORGE)
public class EventHandler {
	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
		if (event.getPlayer() instanceof ServerPlayerEntity) {
			ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
			player.container.addListener(new CapabilityListener(player));
		}
	}

	@SubscribeEvent
	public static void onPlayerRespawn(PlayerRespawnEvent event) {
		if (event.getPlayer() instanceof ServerPlayerEntity) {
			ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
			player.container.addListener(new CapabilityListener(player));
		}
	}

	@SubscribeEvent
	public static void onContainerOpen(PlayerContainerEvent.Open event) {
		if (event.getPlayer() instanceof ServerPlayerEntity) {
			ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
			player.openContainer.addListener(new CapabilityListener(player));
		}
	}
}