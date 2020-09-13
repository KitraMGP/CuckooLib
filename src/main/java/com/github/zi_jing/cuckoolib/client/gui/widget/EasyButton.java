package com.github.zi_jing.cuckoolib.client.gui.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class EasyButton extends GuiButton {
    /** Texture path of the button */
    private ResourceLocation TEXTURE_BACK;
    /** Tooltips to be shown when mouse hover on the button */
    private String containTips;
    /** The X position of the tooltips */
    private int containTipsX;
    /** The Y position of the tooltips */
    private int containTipsY;
    /** The font color of the tooltips */
    private int containTipsColor;
    /** The ItemStack to be rendered on the button */
    private ItemStack containStack;
    /** The X position of the ItemStack */
    private int containStackX;
    /** The Y position of the ItemStack */
    private int containStackY;
    private boolean isActive;
    private boolean hasHoverTexture = false;
    private boolean hasActiveTexture = false;
    private int commonTextureU = 0;
    private int commonTextureV = 0;
    private int hoverTextureU = 0;
    private int hoverTextureV = 0;
    private int activeTextureU = 0;
    private int activeTextureV = 0;

    public EasyButton(int buttonId) {
        super(buttonId, 0, 0, 1, 1, "");
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if(this.visible) {
            updateButton(mc, mouseX, mouseY);
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            int relX = mouseX - this.x, relY = mouseY - this.y;
            if(TEXTURE_BACK != null) {
                mc.getTextureManager().bindTexture(TEXTURE_BACK);
                if(hasHoverTexture){
                    if (relX >= 0 && relY >= 0 && relX < this.width && relY < this.height)
                        this.drawTexturedModalRect(x, y, hoverTextureU, hoverTextureV, width, height);
                    else {
                        if(hasActiveTexture && isActive){
                            this.drawTexturedModalRect(x, y, activeTextureU, activeTextureV, width, height);
                        }
                        else{
                            this.drawTexturedModalRect(x, y, commonTextureU, commonTextureV, width, height);
                        }
                    }
                }
                else{
                    if(hasActiveTexture && isActive) {
                        this.drawTexturedModalRect(x, y, activeTextureU, activeTextureV, width, height);
                    }
                    else{
                        this.drawTexturedModalRect(x, y, commonTextureU, commonTextureV, width, height);
                    }
                }
            }
            if(containStack != null){
                drawItemStack(mc, containStack, containStackX, containStackY);
            }
            if(containTips != null){
                if (relX >= 0 && relY >= 0 && relX < this.width && relY < this.height){
                    mc.fontRenderer.drawString(containTips, containTipsX, containTipsY, containTipsColor);
                }
            }
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glPopMatrix();
        }
    }

    public void updateButton(Minecraft mc, int mouseX, int mouseY){

    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public void flipActive(){
        this.isActive = !this.isActive;
    }

    public void setPos(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setSize(int width, int height){
        this.width = width;
        this.height = height;
    }

    public void setTexture(ResourceLocation textureLocation){
        this.TEXTURE_BACK = textureLocation;
    }

    public void setCommonTextureUV(int u, int v){
        this.commonTextureU = u;
        this.commonTextureV = v;
    }

    public void setHoverTextureUV(int u, int v){
        this.hasHoverTexture = true;
        this.hoverTextureU = u;
        this.hoverTextureV = v;
    }

    public void setActiveTextureUV(int u, int v){
        this.hasActiveTexture = true;
        this.activeTextureU = u;
        this.activeTextureV = v;
    }

    public void setHoverTips(String tips, int hoverX, int hoverY, int color){
        this.containTips = tips;
        this.containTipsX = hoverX;
        this.containTipsY = hoverY;
        this.containTipsColor = color;
    }

    public void setNullHoverTips(){
        this.containTips = null;
    }

    public void setItemStack(ItemStack stack, int x, int y){
        this.containStack = stack;
        this.containStackX = x;
        this.containStackY = y;
    }

    public void setNullItemStack(){
        this.containStack = null;
    }

    private void drawItemStack(Minecraft mc, ItemStack stack, int x, int y){
        GlStateManager.translate(0.0F, 0.0F, 32.0F);
        this.zLevel = 200.0F;
        mc.getRenderItem().zLevel = 200.0F;
        net.minecraft.client.gui.FontRenderer font = stack.getItem().getFontRenderer(stack);
        if (font == null) font = mc.fontRenderer;
        mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
        mc.getRenderItem().renderItemOverlayIntoGUI(font, stack, x, y, "");
        this.zLevel = 0.0F;
        mc.getRenderItem().zLevel = 0.0F;
    }
}
