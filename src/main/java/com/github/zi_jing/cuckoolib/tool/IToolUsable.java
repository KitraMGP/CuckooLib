package com.github.zi_jing.cuckoolib.tool;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public interface IToolUsable {
	public static final int MAX_USE_TICK = 72000;

	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand);

	default int getUseDuration(ItemStack stack) {
		return MAX_USE_TICK;
	}

	default UseAction getUseAnimation(ItemStack stack) {
		return UseAction.NONE;
	}

	default ActionResultType useOn(ItemUseContext context) {
		return ActionResultType.CONSUME;
	}

	default void releaseUsing(ItemStack stack, World world, LivingEntity entity, int timeLeft) {

	}
}