package com.github.zi_jing.cuckoolib.gui.impl;

import com.github.zi_jing.cuckoolib.client.gui.ModularScreen;
import com.github.zi_jing.cuckoolib.gui.IGuiHolderCodec;
import com.github.zi_jing.cuckoolib.gui.IModularGuiHolder;
import com.github.zi_jing.cuckoolib.gui.ModularContainer;
import com.github.zi_jing.cuckoolib.gui.ModularGuiInfo;
import com.github.zi_jing.cuckoolib.util.data.UnorderedRegistry;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PlayerGuiHolder implements IModularGuiHolder {
	public static final UnorderedRegistry<ResourceLocation, IPlayerGuiInfo> REGISTRY = new UnorderedRegistry<ResourceLocation, IPlayerGuiInfo>();

	private IPlayerGuiInfo info;

	public static void register(IPlayerGuiInfo info) {
		REGISTRY.register(info.getInfoRegistryName(), info);
	}

	public static void openPlayerGui(IPlayerGuiInfo info, ServerPlayerEntity player, int... args) {
		ModularGuiInfo.openModularGui(new PlayerGuiHolder(info), player, args);
	}

	public PlayerGuiHolder(ResourceLocation name) {
		if (REGISTRY.containsKey(name)) {
			this.info = REGISTRY.get(name);
		} else {
			throw new IllegalArgumentException("The player gui holder is unregistered");
		}
	}

	public PlayerGuiHolder(IPlayerGuiInfo info) {
		this.info = info;
	}

	public ResourceLocation getInfoRegistryName() {
		return this.info.getInfoRegistryName();
	}

	@Override
	public IGuiHolderCodec getCodec() {
		return PlayerGuiCodec.INSTANCE;
	}

	@Override
	public ITextComponent getTitle(PlayerEntity player) {
		return this.info.getTitle(player);
	}

	@Override
	public ModularGuiInfo createGuiInfo(PlayerEntity player) {
		return this.info.createGuiInfo(player);
	}

	@Override
	public int[] getTasksToExecute(ModularContainer container) {
		return this.info.getTasksToExecute(container);
	}

	@Override
	public void executeTask(ModularContainer container, int id) {
		this.info.executeTask(container, id);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void executeRenderTask(ModularScreen screen) {
		this.info.executeRenderTask(screen);
	}
}