package com.github.zi_jing.cuckoolib;

import com.github.zi_jing.cuckoolib.item.ItemBase;
import com.github.zi_jing.cuckoolib.item.MaterialItem;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = CuckooLib.MODID, bus = Bus.MOD)
public class RegisterEventHandler {
	@SubscribeEvent
	public static void registerItem(RegistryEvent.Register<Item> event) {
		MaterialItem.REGISTERED_MATERIAL_ITEM.values()
				.forEach((map) -> event.getRegistry().registerAll(map.values().toArray(new MaterialItem[0])));
		event.getRegistry().registerAll(ItemBase.REGISTERED_ITEM.toArray(new Item[0]));
	}
}