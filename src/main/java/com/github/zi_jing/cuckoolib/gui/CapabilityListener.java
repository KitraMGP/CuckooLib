package com.github.zi_jing.cuckoolib.gui;

import java.util.HashMap;
import java.util.Map;

import com.github.zi_jing.cuckoolib.CuckooLib;
import com.github.zi_jing.cuckoolib.network.MessageCapabilityUpdate;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkDirection;

public class CapabilityListener implements IContainerListener {
	private static final Map<ResourceLocation, Capability> REGISTRY = new HashMap<ResourceLocation, Capability>();

	private ServerPlayerEntity player;

	public CapabilityListener(ServerPlayerEntity player) {
		this.player = player;
	}

	public static void register(ResourceLocation key, Capability cap) {
		REGISTRY.put(key, cap);
	}

	public static CompoundNBT readCapabilityData(ItemStack stack) {
		CompoundNBT nbt = new CompoundNBT();
		REGISTRY.forEach((key, cap) -> {
			LazyOptional optional = stack.getCapability(cap, null);
			if (optional.isPresent()) {
				INBTSerializable ser = (INBTSerializable) optional.orElse(null);
				nbt.put(key.toString(), ser.serializeNBT());
			}
		});
		return nbt;
	}

	public static void applyCapabilityData(ItemStack stack, CompoundNBT nbt) {
		REGISTRY.forEach((key, cap) -> {
			LazyOptional optional = stack.getCapability(cap, null);
			if (optional.isPresent()) {
				INBTSerializable ser = (INBTSerializable) optional.orElse(null);
				ser.deserializeNBT(nbt.get(key.toString()));
			}
		});
	}

	public static boolean shouldSync(ItemStack stack) {
		for (Capability cap : REGISTRY.values()) {
			if (stack.getCapability(cap, null).isPresent()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void refreshContainer(Container container, NonNullList<ItemStack> items) {
		NonNullList<ItemStack> sync = NonNullList.withSize(items.size(), ItemStack.EMPTY);
		for (int i = 0; i < items.size(); i++) {
			ItemStack stack = items.get(i);
			if (shouldSync(stack)) {
				sync.set(i, stack);
			} else {
				sync.set(i, ItemStack.EMPTY);
			}
		}
		MessageCapabilityUpdate msg = new MessageCapabilityUpdate(container.containerId, sync);
		if (msg.hasData()) {
			CuckooLib.CHANNEL.sendTo(msg, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
		}
	}

	@Override
	public void slotChanged(Container container, int slot, ItemStack stack) {
		if (shouldSync(stack)) {
			MessageCapabilityUpdate msg = new MessageCapabilityUpdate(container.containerId, slot, stack);
			if (msg.hasData()) {
				CuckooLib.CHANNEL.sendTo(msg, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
			}
		}
	}

	@Override
	public void setContainerData(Container container, int varToUpdate, int newValue) {

	}
}