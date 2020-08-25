package com.github.zi_jing.cuckoolib.metaitem;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import com.github.zi_jing.cuckoolib.metaitem.module.IItemModule;

import net.minecraft.item.ItemStack;

public class MetaValueItem {
	protected short id;
	protected int modelCount, stackLimit;
	protected MetaItem metaItem;
	protected String unlocalizedName;

	protected Map<Class<? extends IItemModule>, IItemModule> itemModules;

	public MetaValueItem(MetaItem metaItem, short id, String unlocalizedName) {
		Validate.inclusiveBetween(0, Short.MAX_VALUE - 1, id, "MetaValueItem ID [ " + id + " ] is invalid");
		this.metaItem = metaItem;
		this.id = id;
		this.unlocalizedName = unlocalizedName;
		this.modelCount = 1;
		this.stackLimit = 64;
		this.itemModules = new HashMap<Class<? extends IItemModule>, IItemModule>();
	}

	public MetaValueItem addModule(IItemModule module) {
		Class<? extends IItemModule> type = module.getRegistryID();
		if (this.itemModules.containsKey(type)) {
			throw new IllegalArgumentException("Module type [ " + type.getName() + " ] has existed");
		}
		Validate.notNull(module);
		this.itemModules.put(type, module);
		return this;
	}

	public boolean containsModule(Class<? extends IItemModule> type) {
		return this.itemModules.containsKey(type);
	}

	public <T extends IItemModule> T getModule(Class<T> type) {
		return (T) this.itemModules.get(type);
	}

	public MetaValueItem setModelCount(int count) {
		if (count < 1) {
			throw new IllegalArgumentException("Model count must be a positive number");
		}
		this.modelCount = count;
		return this;
	}

	public int getModelCount() {
		return this.modelCount;
	}

	public MetaValueItem setItemStackLimit(int limit) {
		this.stackLimit = limit;
		return this;
	}

	public int getItemStackLimit() {
		return this.stackLimit;
	}

	public boolean isItemEqual(ItemStack stack) {
		return stack.getItem() == this.metaItem && stack.getMetadata() == this.id;
	}

	public ItemStack getItemStack() {
		return this.getItemStack(1);
	}

	public ItemStack getItemStack(int count) {
		return new ItemStack(this.metaItem, count, this.id);
	}

	public short getId() {
		return this.id;
	}

	public String getUnlocalizedName() {
		return this.unlocalizedName;
	}
}
