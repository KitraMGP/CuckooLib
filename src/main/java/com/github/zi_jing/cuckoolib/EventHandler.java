package com.github.zi_jing.cuckoolib;

import java.util.List;

import com.github.zi_jing.cuckoolib.gui.CapabilityListener;
import com.github.zi_jing.cuckoolib.item.IItemTipInfo;
import com.github.zi_jing.cuckoolib.material.MaterialEntry;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
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
			player.containerMenu.addSlotListener(new CapabilityListener(player));
		}
	}

	@SubscribeEvent
	public static void onPlayerRespawn(PlayerRespawnEvent event) {
		if (event.getPlayer() instanceof ServerPlayerEntity) {
			ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
			player.containerMenu.addSlotListener(new CapabilityListener(player));
		}
	}

	@SubscribeEvent
	public static void onContainerOpen(PlayerContainerEvent.Open event) {
		if (event.getPlayer() instanceof ServerPlayerEntity) {
			ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
			player.containerMenu.addSlotListener(new CapabilityListener(player));
		}
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void addItemTooltip(ItemTooltipEvent e) {
		ItemStack stack = e.getItemStack();
		Item item = stack.getItem();
		List<ITextComponent> tooltip = e.getToolTip();
		if (LibRegistryHandler.ITEM_MATERIAL_REGISTRY.containsKey(item)) {
			MaterialEntry entry = LibRegistryHandler.ITEM_MATERIAL_REGISTRY.get(item);
			tooltip.add(new StringTextComponent(TextFormatting.GOLD + I18n.get("cuckoolib.info.material.ore") + TextFormatting.GREEN + entry.getShape().getLocalizedname(entry.getMaterial())));
		}
		LibRegistryHandler.ITEM_TIP_INFO.entrySet().forEach((entry) -> {
			if (entry.getKey().test(stack)) {
				IItemTipInfo info = entry.getValue();
				TextFormatting color = info.getIndexColor(stack);
				List<String> list = info.getInfo(stack);
				tooltip.add(new StringTextComponent(color + info.getTitle(stack)));
				int size = list.size();
				for (int i = 0; i < list.size() - 1; i++) {
					tooltip.add(new StringTextComponent(color + " | " + TextFormatting.RESET + list.get(i)));
				}
				if (size > 0) {
					tooltip.add(new StringTextComponent(color + " | " + TextFormatting.RESET + list.get(list.size() - 1)));
				}
			}
		});
	}
}