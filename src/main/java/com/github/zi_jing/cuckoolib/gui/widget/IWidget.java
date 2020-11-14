package com.github.zi_jing.cuckoolib.gui.widget;

import com.github.zi_jing.cuckoolib.gui.ISyncedWidgetList;
import com.github.zi_jing.cuckoolib.gui.ModularGuiInfo;
import com.github.zi_jing.cuckoolib.util.math.Vector2i;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IWidget {
	ModularGuiInfo getGuiInfo();

	void setGuiInfo(ModularGuiInfo info);

	ISyncedWidgetList getWidgetList();

	void setWidgetList(ISyncedWidgetList list);

	int getWidgetId();

	void setWidgetId(int id);

	Vector2i getSize();

	void setSize(Vector2i size);

	Vector2i getPosition();

	void setPosition(Vector2i position);

	boolean isEnable();

	void setEnable(boolean enable);

	default void initWidget() {

	}

	default void receiveMessageFromServer(PacketBuffer data) {

	}

	default void receiveMessageFromClient(PacketBuffer data) {

	}

	default void detectAndSendChanges() {

	}

	@SideOnly(Side.CLIENT)
	default void updateScreen() {

	}

	@SideOnly(Side.CLIENT)
	default void drawInForeground(int mouseX, int mouseY) {

	}

	@SideOnly(Side.CLIENT)
	default void drawInBackground(float partialTicks, int mouseX, int mouseY) {

	}
}