package com.github.zi_jing.cuckoolib.metaitem.module;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

@FunctionalInterface
public interface IItemTooltipProvider extends IItemModule {
    void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag);
}