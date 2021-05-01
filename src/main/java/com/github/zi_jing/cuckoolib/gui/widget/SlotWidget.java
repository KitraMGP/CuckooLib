package com.github.zi_jing.cuckoolib.gui.widget;

import com.github.zi_jing.cuckoolib.util.math.Vector2i;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotWidget extends WidgetBase implements ISlotWidget {
    protected SlotItemHandler slot;
    protected int slotCount;
    protected boolean canTakeItems, canPutItems;

    public SlotWidget(IItemHandler inventory, int index, Vector2i position) {
        this(inventory, index, position, true, true);
    }

    public SlotWidget(IItemHandler inventory, int index, Vector2i position, boolean canPutItems, boolean canTakeItems) {
        super(new Vector2i(18, 18), position);
        this.slot = new WidgetSlotItemHandler(inventory, index, position.getX(), position.getY());
        this.canPutItems = canPutItems;
        this.canTakeItems = canTakeItems;
    }

    @Override
    public int getSlotCount() {
        return this.slotCount;
    }

    @Override
    public void setSlotCount(int count) {
        this.slotCount = count;
    }

    @Override
    public Slot getSlot() {
        return this.slot;
    }

    @Override
    public void setEnable(boolean enable) {
        if (this.isEnable ^ enable && this.widgetList != null) {
            this.widgetList.notifySlotChange(this, enable);
        }
        this.isEnable = enable;
    }

    public boolean canTakeItem(EntityPlayer player) {
        return this.isEnable && this.canTakeItems;
    }

    public boolean canPutItem(ItemStack stack) {
        return this.isEnable && this.canPutItems;
    }

    public void onSlotChanged() {

    }

    protected class WidgetSlotItemHandler extends SlotItemHandler {
        public WidgetSlotItemHandler(IItemHandler inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return super.isItemValid(stack) && SlotWidget.this.canPutItem(stack);
        }

        @Override
        public boolean canTakeStack(EntityPlayer player) {
            return super.canTakeStack(player) && SlotWidget.this.canTakeItem(player);
        }

        @Override
        public boolean isEnabled() {
            return SlotWidget.this.isEnable;
        }

        @Override
        public void onSlotChanged() {
            super.onSlotChanged();
            SlotWidget.this.onSlotChanged();
        }
    }
}