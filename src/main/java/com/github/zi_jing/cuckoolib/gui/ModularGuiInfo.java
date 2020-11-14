package com.github.zi_jing.cuckoolib.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.github.zi_jing.cuckoolib.client.util.TextureArea;
import com.github.zi_jing.cuckoolib.gui.widget.IWidget;
import com.github.zi_jing.cuckoolib.gui.widget.SlotWidget;
import com.github.zi_jing.cuckoolib.network.MessageModularGuiOpen;
import com.github.zi_jing.cuckoolib.network.NetworkHandler;
import com.github.zi_jing.cuckoolib.util.math.Vector2i;
import com.github.zi_jing.cuckoolib.util.registry.SizeLimitedRegistry;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;

public class ModularGuiInfo {
	public static final SizeLimitedRegistry<ResourceLocation, IGuiHolderCodec> REGISTRY = new SizeLimitedRegistry<ResourceLocation, IGuiHolderCodec>(
			1024);

	protected ModularContainer container;
	protected EntityPlayer player;
	protected Vector2i size;
	protected TIntObjectMap<IWidget> widgets;
	protected List<Consumer<ModularContainer>> openListeners, closeListeners;

	public ModularGuiInfo(Vector2i size, TIntObjectMap<IWidget> widgets, List<Consumer<ModularContainer>> openListeners,
			List<Consumer<ModularContainer>> closeListeners, EntityPlayer player) {
		this.size = size;
		this.widgets = widgets;
		this.openListeners = openListeners;
		this.closeListeners = closeListeners;
		this.player = player;
	}

	public static void openModularGui(IModularGuiHolder holder, EntityPlayerMP player) {
		if (player instanceof FakePlayer) {
			throw new IllegalArgumentException("The player of the gui info can't be fake");
		}
		IGuiHolderCodec codec = holder.getCodec();
		if (!REGISTRY.hasObject(codec)) {
			throw new IllegalArgumentException("The gui holder codec is unregistered");
		}
		ModularGuiInfo guiInfo = holder.createGuiInfo(player);
		guiInfo.initWidgets();
		ModularContainer container = new ModularContainer(guiInfo);
		player.closeContainer();
		player.getNextWindowId();
		container.windowId = player.currentWindowId;
		container.setDataBlocked(true);
		container.detectAndSendChanges();
		List<PacketBuffer> updateData = new ArrayList<PacketBuffer>(container.getBlockedData());
		container.setDataBlocked(false);
		PacketBuffer holderBuf = new PacketBuffer(Unpooled.buffer());
		holderBuf.writeResourceLocation(codec.getRegistryName());
		codec.writeHolder(holderBuf, holder);
		NetworkHandler.NETWORK.sendTo(new MessageModularGuiOpen(holderBuf, updateData, player.currentWindowId), player);
		player.openContainer = container;
		container.addListener(player);
		MinecraftForge.EVENT_BUS.post(new PlayerContainerEvent.Open(player, container));
	}

	@SideOnly(Side.CLIENT)
	public static void openClientModularGui(int window, IModularGuiHolder holder, List<PacketBuffer> updateData) {
		Minecraft minecraft = Minecraft.getMinecraft();
		EntityPlayerSP player = minecraft.player;
		ModularGuiInfo guiInfo = holder.createGuiInfo(player);
		guiInfo.initWidgets();
		ModularGuiContainer guiContainer = new ModularGuiContainer(guiInfo);
		guiContainer.inventorySlots.windowId = window;
		updateData.forEach((data) -> {
			IWidget widget = ((ModularContainer) guiContainer.inventorySlots).getGuiInfo().getWidget(data.readInt());
			widget.receiveMessageFromServer(data);
		});
		minecraft.addScheduledTask(() -> {
			minecraft.displayGuiScreen(guiContainer);
			minecraft.player.openContainer.windowId = window;
		});
	}

	public void setContainer(ModularContainer container) {
		this.container = container;
	}

	public ModularContainer getContainer() {
		return this.container;
	}

	public EntityPlayer getPlayer() {
		return this.player;
	}

	public int getWidth() {
		return this.size.getX();
	}

	public int getHeight() {
		return this.size.getY();
	}

	public IWidget getWidget(int id) {
		return this.widgets.get(id);
	}

	public List<IWidget> getWidgets() {
		List<IWidget> list = new ArrayList<IWidget>();
		this.widgets.forEachValue((widget) -> {
			list.add(widget);
			return true;
		});
		return list;
	}

	public void onGuiOpen() {
		this.openListeners.forEach((listener) -> listener.accept(this.container));
	}

	public void onGuiClosed() {
		this.openListeners.forEach((listener) -> listener.accept(this.container));
	}

	@SideOnly(Side.CLIENT)
	public void drawInBackground(float partialTicks, int mouseX, int mouseY) {
		this.widgets.forEachValue((widget) -> {
			GlStateManager.pushMatrix();
			GlStateManager.color(1, 1, 1, 1);
			GlStateManager.enableBlend();
			widget.drawInBackground(partialTicks, mouseX, mouseY);
			GlStateManager.popMatrix();
			return true;
		});
	}

	@SideOnly(Side.CLIENT)
	public void drawInForeground(int mouseX, int mouseY) {
		this.widgets.forEachValue((widget) -> {
			GlStateManager.pushMatrix();
			GlStateManager.color(1, 1, 1, 1);
			GlStateManager.enableBlend();
			widget.drawInForeground(mouseX, mouseY);
			GlStateManager.popMatrix();
			return true;
		});
	}

	public void handleMouseHovered(int mouseX, int mouseY) {

	}

	public void handleMouseClicked(int mouseX, int mouseY, int mouseButton) {

	}

	public void handleMouseReleased(int mouseX, int mouseY, int mouseButton) {

	}

	public void handleMouseClickMove(int mouseX, int mouseY, int mouseButton, long timeSinceLastClick) {

	}

	public void initWidgets() {
		this.widgets.forEachValue((widget) -> {
			widget.setGuiInfo(this);
			widget.initWidget();
			return true;
		});
	}

	public static Builder builder() {
		return new Builder(176, 166);
	}

	public static Builder builder(int width, int height) {
		return new Builder(width, height);
	}

	public static class Builder {
		private Vector2i size;
		private TIntObjectMap<IWidget> widgets;
		private List<Consumer<ModularContainer>> openListeners, closeListeners;

		public Builder(int width, int height) {
			this.size = new Vector2i(width, height);
			this.widgets = new TIntObjectHashMap<IWidget>();
			this.openListeners = new ArrayList<Consumer<ModularContainer>>();
			this.closeListeners = new ArrayList<Consumer<ModularContainer>>();
		}

		public Builder addWidget(IWidget widget) {
			int id = this.widgets.size();
			this.widgets.put(id, widget);
			widget.setWidgetId(id);
			return this;
		}

		public Builder addPlayerInventory(InventoryPlayer inventory, TextureArea area, int x, int y) {
			for (int i = 0; i < 9; i++) {
				this.addWidget(
						new SlotWidget(new PlayerMainInvWrapper(inventory), i, new Vector2i(x + i * 18, y + 58)));
			}
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 9; j++) {
					this.addWidget(new SlotWidget(new PlayerMainInvWrapper(inventory), (i + 1) * 9 + j,
							new Vector2i(x + j * 18, y + i * 18)));
				}
			}
			return this;
		}

		public Builder addOpenListener(Consumer<ModularContainer> listener) {
			this.openListeners.add(listener);
			return this;
		}

		public Builder addCloseListener(Consumer<ModularContainer> listener) {
			this.closeListeners.add(listener);
			return this;
		}

		public ModularGuiInfo build(EntityPlayer player) {
			if (player instanceof FakePlayer) {
				throw new IllegalArgumentException("The player of the gui info can't be fake");
			}
			return new ModularGuiInfo(this.size, this.widgets, this.openListeners, this.closeListeners, player);
		}
	}
}