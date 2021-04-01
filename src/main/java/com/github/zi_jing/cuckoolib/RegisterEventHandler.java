package com.github.zi_jing.cuckoolib;

import java.util.ArrayList;
import java.util.List;

import com.github.zi_jing.cuckoolib.item.ItemBase;
import com.github.zi_jing.cuckoolib.item.MaterialItem;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = CuckooLib.MODID, bus = Bus.MOD)
public class RegisterEventHandler {
	@SubscribeEvent
	public static void registerItem(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();
		MaterialItem.REGISTERED_MATERIAL_ITEM.values().forEach((map) -> {
			List<MaterialItem> list = new ArrayList(map.values());
			list.sort(MaterialItem.COMPARATOR);
			registry.registerAll(list.toArray(new MaterialItem[0]));
		});
		registry.registerAll(ItemBase.REGISTERED_ITEM.toArray(new Item[0]));
	}
}