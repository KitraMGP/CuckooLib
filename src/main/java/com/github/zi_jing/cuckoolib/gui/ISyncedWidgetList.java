package com.github.zi_jing.cuckoolib.gui;

import java.util.function.Consumer;

import com.github.zi_jing.cuckoolib.gui.widget.ISlotWidget;

import net.minecraft.network.PacketBuffer;

public interface ISyncedWidgetList {
	void writeToClient(int widgetId, Consumer<PacketBuffer> data);

	void writeToServer(int widgetId, Consumer<PacketBuffer> data);

	void notifySlotChange(ISlotWidget widget, boolean isEnable);
}