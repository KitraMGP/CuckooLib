package com.github.zi_jing.cuckoolib.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.commons.lang3.ArrayUtils;

import com.github.zi_jing.cuckoolib.CuckooLib;
import com.github.zi_jing.cuckoolib.client.gui.ModularScreen;
import com.github.zi_jing.cuckoolib.client.render.EmptyWidgetRenderer;
import com.github.zi_jing.cuckoolib.client.render.IWidgetRenderer;
import com.github.zi_jing.cuckoolib.gui.widget.ButtonWidget;
import com.github.zi_jing.cuckoolib.gui.widget.IWidget;
import com.github.zi_jing.cuckoolib.gui.widget.SlotWidget;
import com.github.zi_jing.cuckoolib.network.MessageModularGuiOpen;
import com.github.zi_jing.cuckoolib.util.data.UnorderedRegistry;
import com.github.zi_jing.cuckoolib.util.math.Vector2i;
import com.mojang.blaze3d.matrix.MatrixStack;

import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;

public class ModularGuiInfo {
	public static final UnorderedRegistry<ResourceLocation, IGuiHolderCodec> REGISTRY = new UnorderedRegistry<ResourceLocation, IGuiHolderCodec>();

	protected ModularContainer container;
	protected PlayerEntity player;
	protected Vector2i size;
	protected ITextComponent title;
	protected IWidgetRenderer background;
	protected Map<Integer, IWidget> widgets;
	protected List<Consumer<ModularContainer>> openListeners, closeListeners;

	public ModularGuiInfo(Vector2i size, IWidgetRenderer background, Map<Integer, IWidget> widgets,
			List<Consumer<ModularContainer>> openListeners, List<Consumer<ModularContainer>> closeListeners,
			PlayerEntity player) {
		this.size = size;
		this.background = background;
		this.widgets = widgets;
		this.openListeners = openListeners;
		this.closeListeners = closeListeners;
		this.player = player;
	}

	public static void openModularGui(IModularGuiHolder holder, ServerPlayerEntity player) {
		openModularGui(holder, player, new IModularGuiHolder[0]);
	}

	public static void openModularGui(IModularGuiHolder holder, ServerPlayerEntity player,
			IModularGuiHolder[] parentHolders) {
		if (player.world.isRemote) {
			return;
		}
		if (player instanceof FakePlayer) {
			throw new IllegalArgumentException("The player of the gui info can't be fake");
		}
		IGuiHolderCodec codec = holder.getCodec();
		if (!REGISTRY.containsValue(codec)) {
			throw new IllegalArgumentException("The gui holder codec is unregistered");
		}
		player.closeContainer();
		player.getNextWindowId();
		ModularGuiInfo guiInfo = holder.createGuiInfo(player);
		guiInfo.title = holder.getTitle(player);
		guiInfo.initWidgets();
		ModularContainer container = new ModularContainer(null, player.currentWindowId, guiInfo,
				ArrayUtils.add(parentHolders, holder));
		container.setDataBlocked(true);
		container.detectAndSendChanges();
		List<PacketBuffer> updateData = new ArrayList<PacketBuffer>(container.getBlockedData());
		container.clearBlockedData();
		container.setDataBlocked(false);
		PacketBuffer holderBuf = new PacketBuffer(Unpooled.buffer());
		holderBuf.writeResourceLocation(codec.getRegistryName());
		codec.writeHolder(holderBuf, holder);
		PacketBuffer[] parentHolderBuf = new PacketBuffer[parentHolders.length];
		for (int i = 0; i < parentHolderBuf.length; i++) {
			IGuiHolderCodec parentCodec = parentHolders[i].getCodec();
			if (!REGISTRY.containsValue(parentCodec)) {
				throw new IllegalArgumentException("The gui holder codec is unregistered");
			}
			parentHolderBuf[i] = new PacketBuffer(Unpooled.buffer());
			parentHolderBuf[i].writeResourceLocation(parentCodec.getRegistryName());
			parentCodec.writeHolder(parentHolderBuf[i], parentHolders[i]);
		}
		CuckooLib.CHANNEL.sendTo(
				new MessageModularGuiOpen(holderBuf, parentHolderBuf, updateData, player.currentWindowId),
				player.connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
		player.openContainer = container;
		player.openContainer.addListener(player);
		MinecraftForge.EVENT_BUS.post(new PlayerContainerEvent.Open(player, container));
	}

	public static void backToParentGui(ModularContainer container) {
		IModularGuiHolder[] parentHolders = container.getParentGuiHolders();
		if (parentHolders.length >= 2) {
			openModularGui(parentHolders[parentHolders.length - 2], (ServerPlayerEntity) container.guiInfo.player,
					Arrays.copyOf(parentHolders, parentHolders.length - 2));
		}
	}

	public static void refreshGui(ModularContainer container) {
		IModularGuiHolder[] parentHolders = container.parentGuiHolders;
		openModularGui(parentHolders[parentHolders.length - 1], (ServerPlayerEntity) container.guiInfo.player,
				Arrays.copyOf(parentHolders, parentHolders.length - 1));
	}

	@OnlyIn(Dist.CLIENT)
	public static void openClientModularGui(int window, IModularGuiHolder holder, IModularGuiHolder[] parentHolders,
			List<PacketBuffer> updateData) {
		Minecraft minecraft = Minecraft.getInstance();
		ClientPlayerEntity player = minecraft.player;
		ModularGuiInfo guiInfo = holder.createGuiInfo(player);
		guiInfo.title = holder.getTitle(player);
		guiInfo.initWidgets();
		ModularScreen screen = new ModularScreen(window, guiInfo, ArrayUtils.add(parentHolders, holder),
				player.inventory, guiInfo.title);
		updateData.forEach((data) -> {
			IWidget widget = guiInfo.container.getGuiInfo().getWidget(data.readInt());
			widget.receiveMessageFromServer(data);
		});
		player.openContainer = guiInfo.container;
		Minecraft.getInstance().displayGuiScreen(screen);
	}

	public static void registerGuiHolderCodec(IGuiHolderCodec codec) {
		REGISTRY.register(codec.getRegistryName(), codec);
	}

	public IWidgetRenderer getBackground() {
		return this.background;
	}

	public void setContainer(ModularContainer container) {
		this.container = container;
	}

	public ModularContainer getContainer() {
		return this.container;
	}

	public PlayerEntity getPlayer() {
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
		this.widgets.forEach((id, widget) -> list.add(widget));
		return list;
	}

	public void onGuiOpen() {
		this.openListeners.forEach((listener) -> listener.accept(this.container));
	}

	public void onGuiClosed() {
		this.openListeners.forEach((listener) -> listener.accept(this.container));
	}

	@OnlyIn(Dist.CLIENT)
	public void drawInBackground(MatrixStack transform, float partialTicks, int mouseX, int mouseY, int guiLeft,
			int guiTop) {
		this.widgets.forEach((id, widget) -> {
			if (widget.isEnable()) {
				widget.drawInBackground(transform, partialTicks, mouseX, mouseY, guiLeft, guiTop);
			}
		});
	}

	@OnlyIn(Dist.CLIENT)
	public void drawInForeground(MatrixStack transform, int mouseX, int mouseY, int guiLeft, int guiTop) {
		this.widgets.forEach((id, widget) -> {
			if (widget.isEnable()) {
				widget.drawInForeground(transform, mouseX, mouseY, guiLeft, guiTop);
			}
		});
	}

	public void handleMouseHovered(int mouseX, int mouseY) {
		this.container.addMouseHoveredData(mouseX, mouseY);
		this.widgets.forEach((id, widget) -> {
			if (widget.isEnable() && widget.isInRange(mouseX, mouseY)) {
				widget.onMouseHovered(mouseX, mouseY);
			}
		});
	}

	public boolean handleMouseClicked(double mouseX, double mouseY, int button) {
		this.widgets.forEach((id, widget) -> {
			if (widget.isEnable() && widget.isInRange(mouseX, mouseY)) {
				widget.onMouseClicked(mouseX, mouseY, button);
			}
		});
		return true;
	}

	public boolean handleMouseReleased(double mouseX, double mouseY, int button) {
		this.widgets.forEach((id, widget) -> {
			if (widget.isEnable() && widget.isInRange(mouseX, mouseY)) {
				widget.onMouseReleased(mouseX, mouseY, button);
			}
		});
		return true;
	}

	public boolean handleMouseClickMove(double mouseX, double mouseY, int button, double dragX, double dragY) {
		this.widgets.forEach((id, widget) -> {
			if (widget.isEnable() && widget.isInRange(mouseX, mouseY)) {
				widget.onMouseClickMove(mouseX, mouseY, button, dragX, dragY);
			}
		});
		return true;
	}

	public void initWidgets() {
		this.widgets.forEach((id, widget) -> {
			widget.initWidget(this);
		});
	}

	public static Builder builder() {
		return new Builder(176, 166);
	}

	public static Builder builder(int width, int height) {
		return new Builder(width, height);
	}

	public static class Builder {
		private int internalCount;
		private Vector2i size;
		private IWidgetRenderer background = EmptyWidgetRenderer.INSTANCE;
		private Map<Integer, IWidget> widgets;
		private List<Consumer<ModularContainer>> openListeners, closeListeners;

		public Builder(int width, int height) {
			this.internalCount = -1;
			this.size = new Vector2i(width, height);
			this.widgets = new HashMap<Integer, IWidget>();
			this.openListeners = new ArrayList<Consumer<ModularContainer>>();
			this.closeListeners = new ArrayList<Consumer<ModularContainer>>();
			this.addInternalWidget(new ButtonWidget(-1, this.size.getX() - 19, -9, 9, 9, (data, container) -> {
				backToParentGui(container);
			}).setRenderer(ModularScreen.BACK));
			this.addInternalWidget(new ButtonWidget(-2, this.size.getX() - 9, -9, 9, 9, (data, container) -> {
				refreshGui(container);
			}).setRenderer(ModularScreen.REFRESH));
		}

		public Builder setBackground(IWidgetRenderer background) {
			this.background = background;
			return this;
		}

		public Builder addWidget(int id, IWidget widget) {
			this.widgets.put(id, widget);
			widget.setWidgetId(id);
			return this;
		}

		protected Builder addInternalWidget(IWidget widget) {
			this.widgets.put(this.internalCount, widget);
			widget.setWidgetId(this.internalCount);
			this.internalCount--;
			return this;
		}

		public Builder addPlayerInventory(PlayerInventory inventory) {
			return this.addPlayerInventory(inventory, 8, 84);
		}

		public Builder addPlayerInventory(PlayerInventory inventory, int x, int y) {
			for (int i = 0; i < 9; i++) {
				this.addInternalWidget(new SlotWidget(x + i * 18, y + 58, new PlayerMainInvWrapper(inventory), i));
			}
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 9; j++) {
					this.addInternalWidget(new SlotWidget(x + j * 18, y + i * 18, new PlayerMainInvWrapper(inventory),
							(i + 1) * 9 + j));
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

		public ModularGuiInfo build(PlayerEntity player) {
			if (player instanceof FakePlayer) {
				throw new IllegalArgumentException("The player of the gui info can't be fake");
			}
			return new ModularGuiInfo(this.size, this.background, this.widgets, this.openListeners, this.closeListeners,
					player);
		}
	}
}