package com.github.zi_jing.cuckoolib.gui.impl;

import com.github.zi_jing.cuckoolib.client.render.IWidgetRenderer;

import net.minecraft.util.math.BlockPos;

public interface IPlanInfoTileEntity {
	int getPlanCount();

	IWidgetRenderer getOverlayRenderer(int count);

	void callback(int count);

	BlockPos getBlockPos();
}