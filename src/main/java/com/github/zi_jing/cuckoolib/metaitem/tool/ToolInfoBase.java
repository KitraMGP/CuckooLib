package com.github.zi_jing.cuckoolib.metaitem.tool;

import java.util.List;

import com.github.zi_jing.cuckoolib.material.type.Material;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public abstract class ToolInfoBase implements IToolInfo {
	@Override
	public boolean validateMaterial(Material material) {
		return material.hasFlag(Material.GENERATE_TOOL);
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		ToolMetaItem item = ToolMetaItem.getToolMetaItem(stack);
		if (item.getToolMaterial(stack) != null) {
			return item.getToolMaterial(stack).getDurability();
		}
		return 0;
	}

	@Override
	public int getHarvestLevel(ItemStack stack) {
		ToolMetaItem item = ToolMetaItem.getToolMetaItem(stack);
		if (item.getToolMaterial(stack) != null) {
			return item.getToolMaterial(stack).getHarvestLevel();
		}
		return 0;
	}

	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
		ToolMetaItem item = ToolMetaItem.getToolMetaItem(stack);
		Material material = item.getToolMaterial(stack);
		double damageProgress = item.getDurabilityForDisplay(stack);
		TextFormatting colorDamage = damageProgress >= 0.7
				? (damageProgress >= 0.9 ? TextFormatting.RED : TextFormatting.YELLOW)
				: TextFormatting.GREEN;
		int damage = item.getToolDamage(stack);
		int maxDamage = item.getToolMaxDamage(stack);
		tooltip.add(TextFormatting.BLUE + I18n.format("metaitem.cuckoolib.tool.durability") + " " + colorDamage
				+ (maxDamage - damage) + TextFormatting.WHITE + " / " + TextFormatting.GREEN + maxDamage);
		if (material != null) {
			tooltip.add(TextFormatting.BLUE + I18n.format("metaitem.cuckoolib.tool.harvest_level") + " "
					+ TextFormatting.GREEN + material.getHarvestLevel());
		}
	}

	@Override
	public int getBlockBreakDamage() {
		return 200;
	}

	@Override
	public int getInteractionDamage() {
		return 100;
	}

	@Override
	public int getContainerCraftDamage() {
		return 800;
	}

	@Override
	public int getEntityHitDamage() {
		return 100;
	}
}