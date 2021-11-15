package com.github.zi_jing.cuckoolib.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.zi_jing.cuckoolib.LibRegistryHandler;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public class ItemFuelInfo implements IItemTipInfo {
	@Override
	public String getTitle(ItemStack stack) {
		return I18n.get("cuckoolib.tipinfo.fuel.title");
	}

	@Override
	public List<String> getInfo(ItemStack stack) {
		Item item = stack.getItem();
		if (!LibRegistryHandler.ITEM_FUEL_REGISTRY.containsKey(item)) {
			return new ArrayList<String>();
		}
		ItemFuelEntry entry = LibRegistryHandler.ITEM_FUEL_REGISTRY.get(item);
		return Arrays.asList(TextFormatting.BLUE + I18n.get("cuckoolib.tipinfo.fuel.mass") + TextFormatting.AQUA + String.format("%.2f", entry.getFuelMass()) + TextFormatting.GREEN + "kg",
				TextFormatting.BLUE + I18n.get("cuckoolib.tipinfo.fuel.calorific_value") + TextFormatting.AQUA + String.format("%.2f", entry.getCalorificValue()) + TextFormatting.GREEN + "J/kg",
				TextFormatting.BLUE + I18n.get("cuckoolib.tipinfo.fuel.ignition_point") + TextFormatting.AQUA + String.format("%.2f", entry.getRealIgnitionPoint()) + TextFormatting.GREEN + "K");
	}

	@Override
	public TextFormatting getIndexColor(ItemStack stack) {
		return TextFormatting.GREEN;
	}
}