package com.github.zi_jing.cuckoolib.item;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import com.github.zi_jing.cuckoolib.tool.IToolInfo;

import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

public class ToolItem extends Item {
	protected String modid, name;
	protected IToolInfo toolInfo;

	public ToolItem(String modid, String name, Properties properties, IToolInfo toolInfo) {
		super(properties);
		this.modid = modid;
		this.name = name;
		this.toolInfo = toolInfo;
		this.setRegistryName(modid, name);
	}

	public IToolInfo getToolInfo() {
		return this.toolInfo;
	}

	public static CompoundNBT getToolBaseNBT(ItemStack stack) {
		return stack.getOrCreateChildTag("tool_info");
	}

	public ItemStack createItemStack() {
		return this.createItemStack(1);
	}

	public ItemStack createItemStack(int count) {
		return new ItemStack(this, count);
	}

	@Override
	public int getItemStackLimit(ItemStack stack) {
		return 1;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, BlockState state, BlockPos pos,
			LivingEntity entityLiving) {
		this.createItemStack(1);
		this.damageItem(stack, this.toolInfo.getBlockBreakDamage());
		return true;
	}

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		if (this.toolInfo.canHarvestBlock(state)) {
			return this.toolInfo.getDestroySpeed(stack);
		}
		return 1;
	}

	@Override
	public boolean canHarvestBlock(BlockState state) {
		return this.toolInfo.canHarvestBlock(state);
	}

	@Override
	public int getHarvestLevel(ItemStack stack, ToolType tool, PlayerEntity player, BlockState blockState) {
		return this.toolInfo.getHarvestLevel(stack, tool, player, blockState);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
		this.toolInfo.addInformation(stack, world, tooltip, flag);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return this.getToolDamage(stack) > 0;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return ((double) this.getToolDamage(stack)) / this.getToolMaxDamage(stack);
	}

	@Override
	public int getRGBDurabilityForDisplay(ItemStack stack) {
		double value = this.getDurabilityForDisplay(stack);
		if (value >= 0.7 && value < 0.9) {
			return 0xffff00;
		}
		if (value >= 0.9) {
			return 0xff0000;
		}
		return 0x00ff00;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		return super.onItemRightClick(world, player, hand);
	}

	public boolean enableItemDamage(ItemStack stack) {
		return this.toolInfo.getMaxDamage(stack) > 0;
	}

	public boolean damageItem(ItemStack stack, int value) {
		if (!this.enableItemDamage(stack)) {
			return false;
		}
		CompoundNBT nbt = getToolBaseNBT(stack);
		int maxDamage = this.getToolMaxDamage(stack);
		int damage = this.getToolDamage(stack);
		if (damage + value < maxDamage) {
			nbt.putInt("damage", damage + value);
			return false;
		}
		try {
			Field fieldItem = Arrays.stream(ItemStack.class.getDeclaredFields())
					.filter(field -> field.getType() == Item.class).findFirst()
					.orElseThrow(ReflectiveOperationException::new);
			fieldItem.setAccessible(true);
			fieldItem.set(stack, Items.AIR);
		} catch (SecurityException | ReflectiveOperationException e) {
			e.printStackTrace();
		}
		return true;
	}

	public int getToolMaxDamage(ItemStack stack) {
		return this.toolInfo.getMaxDamage(stack);
	}

	public int getToolDamage(ItemStack stack) {
		if (!this.enableItemDamage(stack)) {
			return 0;
		}
		CompoundNBT nbt = getToolBaseNBT(stack);
		if (nbt.contains("damage")) {
			return nbt.getInt("damage");
		}
		return 0;
	}
}