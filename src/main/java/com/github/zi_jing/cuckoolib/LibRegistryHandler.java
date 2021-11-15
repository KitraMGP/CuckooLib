package com.github.zi_jing.cuckoolib;

import java.util.function.Predicate;

import com.github.zi_jing.cuckoolib.item.IItemTipInfo;
import com.github.zi_jing.cuckoolib.item.ItemFuelEntry;
import com.github.zi_jing.cuckoolib.item.ItemFuelInfo;
import com.github.zi_jing.cuckoolib.material.MaterialEntry;
import com.github.zi_jing.cuckoolib.material.MatterState;
import com.github.zi_jing.cuckoolib.material.ModMaterials;
import com.github.zi_jing.cuckoolib.material.ModSolidShapes;
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
	public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, CuckooLib.MODID);

	public static final RegistryObject<DataRecipeSerializer> DATA_RECIPE = RECIPE_SERIALIZERS.register("data_recipe", DataRecipeSerializer::new);

	public static final UnorderedRegistry<Predicate<ItemStack>, IItemTipInfo> ITEM_TIP_INFO = new UnorderedRegistry<Predicate<ItemStack>, IItemTipInfo>();
	public static final UnorderedRegistry<String, FuelInfo> FUEL_INFO_REGISTRY = new UnorderedRegistry<String, FuelInfo>();
	public static final UnorderedRegistry<Item, ItemFuelEntry> ITEM_FUEL_REGISTRY = new UnorderedRegistry<Item, ItemFuelEntry>();
	public static final UnorderedRegistry<Item, MaterialEntry> ITEM_MATERIAL_REGISTRY = new UnorderedRegistry<Item, MaterialEntry>();

	public static final FuelInfo COAL = FuelInfo.of(MatterState.SOLID, 630, 64000);
	public static final FuelInfo CHARCOAL = FuelInfo.of(MatterState.SOLID, 630, 64000);
	public static final FuelInfo WOOD = FuelInfo.of(MatterState.SOLID, 430, 16000);
	public static final FuelInfo PLANT = FuelInfo.of(MatterState.SOLID, 400, 16000);

	public static void register() {
		registerFuel();
		ITEM_TIP_INFO.register((stack) -> ITEM_FUEL_REGISTRY.containsKey(stack.getItem()), new ItemFuelInfo());
		ITEM_MATERIAL_REGISTRY.register(Items.IRON_INGOT, MaterialEntry.of(ModSolidShapes.INGOT, ModMaterials.IRON));
		ITEM_MATERIAL_REGISTRY.register(Items.IRON_NUGGET, MaterialEntry.of(ModSolidShapes.NUGGET, ModMaterials.IRON));
		ITEM_MATERIAL_REGISTRY.register(Items.GOLD_INGOT, MaterialEntry.of(ModSolidShapes.INGOT, ModMaterials.GOLD));
		ITEM_MATERIAL_REGISTRY.register(Items.GOLD_NUGGET, MaterialEntry.of(ModSolidShapes.NUGGET, ModMaterials.GOLD));
		ITEM_MATERIAL_REGISTRY.register(Items.REDSTONE, MaterialEntry.of(ModSolidShapes.DUST, ModMaterials.REDSTONE));
	}

	public static void registerFuel() {
		FUEL_INFO_REGISTRY.register("coal", COAL);
		FUEL_INFO_REGISTRY.register("charcoal", CHARCOAL);
		FUEL_INFO_REGISTRY.register("wood", WOOD);
		FUEL_INFO_REGISTRY.register("plant", PLANT);
		ITEM_FUEL_REGISTRY.register(Items.COAL, COAL.toEntry(200, 1.3));
		ITEM_FUEL_REGISTRY.register(Items.CHARCOAL, CHARCOAL.toEntry(200, 1.3));
		ITEM_FUEL_REGISTRY.register(Items.OAK_LOG, WOOD.toEntry(800, 1.5));
		ITEM_FUEL_REGISTRY.register(Items.STICK, WOOD.toEntry(100, 1.5));
	}
}