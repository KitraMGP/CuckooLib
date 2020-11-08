package com.github.zi_jing.cuckoolib.gui.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class EasyList extends GuiListExtended {
    private ResourceLocation TEXTURE_ITEM;
    private List<EasyListEntry> itemEntries;

    public EasyList(Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
        super(mcIn, widthIn, heightIn, topIn, bottomIn, slotHeightIn);
    }

    @Override
    public EasyListEntry getListEntry(int index) {
        return this.itemEntries.get(index);
    }

    @Override
    protected int getSize() {
        return this.itemEntries.size();
    }

    public void setItemTexture(ResourceLocation textureItem){
        this.TEXTURE_ITEM = textureItem;
    }

    public void addEntry(ItemStack stack){
        EasyListEntry entry = new EasyListEntry(this.mc, stack);
        this.itemEntries.add(entry);
    }

    public class EasyListEntry implements IGuiListEntry{
        private ItemStack stack;
        private Minecraft mc;

        public EasyListEntry(Minecraft mc, ItemStack stack){
            this.stack = stack;
            this.mc = mc;
        }

        @Override
        public void updatePosition(int slotIndex, int x, int y, float partialTicks) {

        }

        @Override
        public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected, float partialTicks) {
            mc.getRenderItem().renderItemIntoGUI(stack, x, y);
        }

        @Override
        public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int relativeX, int relativeY) {
            return false;
        }

        @Override
        public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {

        }

        public ItemStack getStack() {
            return stack;
        }
    }
}
