package com.github.zi_jing.cuckoolib.recipe.data;

import java.util.List;
import java.util.function.Supplier;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ITag;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;

public abstract class OutputItem implements Supplier<ItemStack> {
	public abstract JsonElement serialize();

	public static OutputItem fromItemProvider(IItemProvider item) {
		return fromItemStack(new ItemStack(item));
	}

	public static OutputItem fromItemStack(ItemStack stack) {
		return new ItemStackOutputItem(stack);
	}

	public static OutputItem fromTag(ITag<Item> tag, int count) {
		return new TagOutputItem(tag, count);
	}

	public static OutputItem fromJson(JsonElement element) {
		if (element.isJsonPrimitive()) {
			return fromItemProvider(JSONUtils.getItem(element, "item"));
		}
		if (!element.isJsonObject()) {
			throw new JsonSyntaxException("JsonElement for OutputItem must be String or JsonObject");
		}
		JsonObject json = element.getAsJsonObject();
		if (json.has("tag")) {
			String name = JSONUtils.getString(json, "tag");
			ITag<Item> tag = TagCollectionManager.getManager().getItemTags().get(new ResourceLocation(name));
			if (tag == null) {
				throw new JsonSyntaxException("Tag [ " + name + " ] for OutputItem is unknown");
			}
			int count = JSONUtils.getInt(json, "count", 1);
			return fromTag(tag, count);
		}
		return fromItemStack(CraftingHelper.getItemStack(json, true));
	}

	public static OutputItem fromPacketBuffer(PacketBuffer buffer) {
		return fromItemStack(buffer.readItemStack());
	}

	public PacketBuffer writeToPacketBuffer(PacketBuffer buffer) {
		buffer.writeItemStack(this.get());
		return buffer;
	}

	private static class ItemStackOutputItem extends OutputItem {
		private ItemStack stack;

		private ItemStackOutputItem(ItemStack stack) {
			this.stack = stack;
		}

		@Override
		public ItemStack get() {
			return this.stack;
		}

		@Override
		public JsonElement serialize() {
			String item = this.stack.getItem().getRegistryName().toString();
			int count = this.stack.getCount();
			if (count > 1 || this.stack.hasTag()) {
				JsonObject json = new JsonObject();
				json.addProperty("item", item);
				if (count > 1) {
					json.addProperty("count", count);
				}
				CompoundNBT nbt = this.stack.getTag();
				if (nbt != null) {
					json.addProperty("nbt", nbt.toString());
				}
				return json;
			}
			return new JsonPrimitive(item);
		}
	}

	private static class TagOutputItem extends OutputItem {
		private ITag<Item> tag;
		private int count;

		public TagOutputItem(ITag<Item> tag, int count) {
			this.tag = tag;
			this.count = count;
		}

		@Override
		public ItemStack get() {
			List<Item> list = this.tag.getAllElements();
			if (list.isEmpty()) {
				return ItemStack.EMPTY;
			}
			return new ItemStack(list.get(0), this.count);
		}

		@Override
		public JsonElement serialize() {
			JsonObject json = new JsonObject();
			json.addProperty("tag",
					TagCollectionManager.getManager().getItemTags().getValidatedIdFromTag(this.tag).toString());
			if (this.count > 1) {
				json.addProperty("count", this.count);
			}
			return json;
		}
	}
}