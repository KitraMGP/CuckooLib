package com.github.zi_jing.cuckoolib.tool;

import java.util.List;

import com.github.zi_jing.cuckoolib.item.MaterialToolItem;
import com.github.zi_jing.cuckoolib.material.type.MaterialBase;
import com.github.zi_jing.cuckoolib.util.text.GreekAlphabet;

import net.minecraft.block.BlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

public abstract class ToolBase implements IToolInfo {
	@Override
	public boolean validateMaterial(MaterialBase material) {
		return material.hasFlag(MaterialBase.GENERATE_TOOL);
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		MaterialBase material = MaterialToolItem.getToolMaterial(stack, 0);
		return material != null ? material.getDurability() : 0;
	}

	@Override
	public int getBlockBreakDamage() {
		return 200;
	}

	@Override
	public int getInteractionDamage() {
		return 50;
	}

	@Override
	public int getContainerCraftDamage() {
		return 800;
	}

	@Override
	public int getEntityHitDamage() {
		return 100;
	}

	@Override
	public int getHarvestLevel(ItemStack stack, ToolType tool, PlayerEntity player, BlockState blockState) {
		return MaterialToolItem.getToolMaterial(stack, 0).getHarvestLevel();
	}

	@Override
	public float getDestroySpeed(ItemStack stack) {
		return MaterialToolItem.getToolMaterial(stack, 0).getEfficiency();
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
		MaterialToolItem.getToolAllMaterial(stack)
				.forEach((index,
						pair) -> tooltip.add(new StringTextComponent(TextFormatting.BLUE
								+ I18n.format("cuckoolib.item.tool." + pair.getLeft()) + " [" + TextFormatting.AQUA
								+ GreekAlphabet.getLowercase(index + 1) + TextFormatting.BLUE + "] : "
								+ TextFormatting.GREEN + pair.getRight().getLocalizedName())));
		MaterialToolItem item = this.getToolItem(stack);
		int damage = item.getToolDamage(stack);
		int maxDamage = item.getToolMaxDamage(stack);
		float durability = ((float) damage) / maxDamage;
		TextFormatting color = TextFormatting.GREEN;
		if (durability >= 0.7 && durability < 0.9) {
			color = TextFormatting.YELLOW;
		} else if (durability >= 0.9 && durability < 0.97) {
			color = TextFormatting.RED;
		} else if (durability >= 0.97) {
			color = System.currentTimeMillis() % 500 < 250 ? TextFormatting.RED : TextFormatting.WHITE;
		}
		StringBuilder builder = new StringBuilder();
		int num = (int) ((1 - durability) * 20);
		builder.append(color);
		for (int i = 0; i < num; i++) {
			builder.append("=");
		}
		builder.append(">");
		builder.append(TextFormatting.WHITE);
		for (int i = 0; i < 20 - num - 1; i++) {
			builder.append("-");
		}
		tooltip.add(new StringTextComponent(
				color + "" + (maxDamage - damage) + TextFormatting.WHITE + " / " + maxDamage + " " + builder));
	}

	protected MaterialToolItem getToolItem(ItemStack stack) {
		return (MaterialToolItem) stack.getItem();
	}
}