package com.github.zi_jing.cuckoolib;

import java.util.function.Predicate;

import com.github.zi_jing.cuckoolib.item.IItemTipInfo;
import com.github.zi_jing.cuckoolib.item.ItemFuelEntry;
import com.github.zi_jing.cuckoolib.item.ItemFuelInfo;
import com.github.zi_jing.cuckoolib.material.MatterState;
import com.github.zi_jing.cuckoolib.recipe.data.DataRecipeSerializer;
import com.github.zi_jing.cuckoolib.util.data.FuelInfo;
import com.github.zi_jing.cuckoolib.util.data.UnorderedRegistry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LibRegistryHandler {
	public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister
			.create(ForgeRegistries.RECIPE_SERIALIZERS, CuckooLib.MODID);

	public static final RegistryObject<DataRecipeSerializer> DATA_RECIPE = RECIPE_SERIALIZERS.register("data_recipe",
			DataRecipeSerializer::new);

	public static final UnorderedRegistry<Predicate<ItemStack>, IItemTipInfo> ITEM_TIP_INFO = new UnorderedRegistry<Predicate<ItemStack>, IItemTipInfo>();
	public static final UnorderedRegistry<String, FuelInfo> FUEL_INFO_REGISTRY = new UnorderedRegistry<String, FuelInfo>();
	public static final UnorderedRegistry<Item, ItemFuelEntry> ITEM_FUEL_REGISTRY = new UnorderedRegistry<Item, ItemFuelEntry>();

	public static final FuelInfo COAL = FuelInfo.of(MatterState.SOLID, 630, 64000);
	public static final FuelInfo CHARCOAL = FuelInfo.of(MatterState.SOLID, 630, 64000);

	public static void register() {
		registerFuel();
		ITEM_TIP_INFO.register((stack) -> ITEM_FUEL_REGISTRY.containsKey(stack.getItem()), new ItemFuelInfo());
	}

	public static void registerFuel() {
		FUEL_INFO_REGISTRY.register("coal", COAL);
		FUEL_INFO_REGISTRY.register("charcoal", CHARCOAL);
		ITEM_FUEL_REGISTRY.register(Items.COAL, COAL.toEntry(200, 1.3f));
		ITEM_FUEL_REGISTRY.register(Items.CHARCOAL, CHARCOAL.toEntry(200, 1.3f));
	}
}