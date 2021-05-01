package com.github.zi_jing.cuckoolib.gui;

import com.github.zi_jing.cuckoolib.gui.widget.ISlotWidget;
import com.github.zi_jing.cuckoolib.network.MessageGuiClient;
import com.github.zi_jing.cuckoolib.network.MessageGuiServer;
import com.github.zi_jing.cuckoolib.network.NetworkHandler;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.Consumer;

public class ModularContainer extends Container implements ISyncedWidgetList {
    protected ModularGuiInfo guiInfo;
    protected HashMap<ISlotWidget, Slot> slotMap;
    protected List<PacketBuffer> blockedData;
    protected boolean isDataBlocked;

    public ModularContainer(ModularGuiInfo info) {
        this.guiInfo = info;
        info.container = this;
        this.slotMap = new HashMap<ISlotWidget, Slot>();
        this.blockedData = new ArrayList<PacketBuffer>();
        this.isDataBlocked = false;
        info.getWidgets().forEach((widget) -> {
            widget.setWidgetList(this);
            if (widget instanceof ISlotWidget && widget.isEnable()) {
                Slot slot = ((ISlotWidget) widget).getSlot();
                this.slotMap.put((ISlotWidget) widget, slot);
                this.addSlotToContainer(slot);
                ((ISlotWidget) widget).setSlotCount(slot.slotNumber);
            }
        });
        info.onGuiOpen();
    }

    public ModularGuiInfo getGuiInfo() {
        return this.guiInfo;
    }

    public void setDataBlocked(boolean blocked) {
        this.isDataBlocked = blocked;
    }

    public List<PacketBuffer> getBlockedData() {
        return this.blockedData;
    }

    public void clearBlockedData() {
        this.blockedData.clear();
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        this.guiInfo.widgets.forEachValue((widget) -> {
            widget.detectAndSendChanges();
            return true;
        });
    }

    @Override
    public void writeToClient(int widgetId, Consumer<PacketBuffer> data) {
        PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
        buffer.writeInt(widgetId);
        data.accept(buffer);
        if (this.isDataBlocked) {
            this.blockedData.add(buffer);
        } else {
            NetworkHandler.NETWORK.sendTo(new MessageGuiServer(buffer, this.windowId),
                    (EntityPlayerMP) this.guiInfo.getPlayer());
        }
    }

    @Override
    public void writeToServer(int widgetId, Consumer<PacketBuffer> data) {
        PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
        buffer.writeInt(widgetId);
        data.accept(buffer);
        NetworkHandler.NETWORK.sendToServer(new MessageGuiClient(buffer, this.windowId));
    }

    @Override
    public void notifySlotChange(ISlotWidget widget, boolean isEnable) {
        if (isEnable && !this.slotMap.containsKey(widget)) {
            Slot slotAdd = widget.getSlot();
            this.slotMap.put((ISlotWidget) widget, slotAdd);
            OptionalInt optional = this.inventorySlots.stream().filter((slot) -> slot instanceof EmptySlot)
                    .mapToInt((slot) -> slot.slotNumber).findFirst();
            if (optional.isPresent()) {
                int idx = optional.getAsInt();
                slotAdd.slotNumber = idx;
                this.inventorySlots.set(idx, slotAdd);
                this.inventoryItemStacks.set(idx, ItemStack.EMPTY);
                widget.setSlotCount(idx);
            } else {
                this.addSlotToContainer(slotAdd);
                widget.setSlotCount(slotAdd.slotNumber);
            }
        } else if (!isEnable && this.slotMap.containsKey(widget)) {
            Slot slotRemove = widget.getSlot();
            this.slotMap.remove(widget);
            EmptySlot emptySlot = new EmptySlot();
            emptySlot.slotNumber = slotRemove.slotNumber;
            this.inventorySlots.set(slotRemove.slotNumber, emptySlot);
            this.inventoryItemStacks.set(slotRemove.slotNumber, ItemStack.EMPTY);
        }
    }

    @Override
    public void notifySlotItemUpdate(ISlotWidget widget) {
        Slot slot = widget.getSlot();
        for (IContainerListener listener : this.listeners) {
            listener.sendSlotContents(this, slot.slotNumber, slot.getStack());
        }
    }

    @Override
    public ItemStack notifyTransferStack(ItemStack stack, boolean simulate) {
        return stack;
    }

    public static class EmptySlot extends Slot {
        private static final IInventory EMPTY_INVENTORY = new InventoryBasic("[Null]", false, 0);

        public EmptySlot() {
            super(EMPTY_INVENTORY, 0, -0, -0);
        }

        @Override
        public ItemStack getStack() {
            return ItemStack.EMPTY;
        }

        @Override
        public void putStack(ItemStack stack) {

        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return false;
        }

        @Override
        public boolean canTakeStack(EntityPlayer playerIn) {
            return false;
        }

        @Override
        public boolean isEnabled() {
            return false;
        }
    }
}