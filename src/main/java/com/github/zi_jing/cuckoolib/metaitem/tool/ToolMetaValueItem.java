package com.github.zi_jing.cuckoolib.metaitem.tool;

import org.apache.commons.lang3.Validate;

import com.github.zi_jing.cuckoolib.material.type.Material;
import com.github.zi_jing.cuckoolib.metaitem.MetaValueItem;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ToolMetaValueItem extends MetaValueItem {
	protected IToolInfo toolInfo;

	public ToolMetaValueItem(ToolMetaItem metaItem, short id, String unlocalizedName) {
		super(metaItem, id, unlocalizedName);
		this.stackLimit = 1;
		this.toolInfo = NormalToolInfo.INSTANCE;
	}

	public ToolMetaItem getToolMetaItem() {
		return (ToolMetaItem) this.metaItem;
	}

	public ToolMetaValueItem setToolInfo(IToolInfo info) {
		Validate.notNull(info, "The ToolInfo can't be null");
		this.toolInfo = info;
		return this;
	}

	public IToolInfo getToolInfo() {
		return this.toolInfo;
	}

	public ItemStack getItemStack(Material material) {
		return this.getItemStack(1, material);
	}

	public ItemStack getItemStack(int count, Material material) {
		if (!this.toolInfo.validateMaterial(material)) {
			throw new IllegalArgumentException("Tool material [ " + material + " ] is invalid");
		}
		ItemStack stack = new ItemStack(this.metaItem, count, this.id);
		NBTTagCompound base = this.getToolMetaItem().getToolBaseNBT(stack);
		base.setString("material", material.getName());
		return stack;
	}

	@Override
	public ItemStack getItemStack() {
		return this.getItemStack(1);
	}

	@Override
	public ItemStack getItemStack(int count) {
		return new ItemStack(this.metaItem, count, this.id);
	}
}