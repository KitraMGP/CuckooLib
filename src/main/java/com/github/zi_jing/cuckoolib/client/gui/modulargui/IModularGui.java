package com.github.zi_jing.cuckoolib.client.gui.modulargui;

import net.minecraft.client.Minecraft;

public interface IModularGui {

    /**
     * 获取这个Gui的所有控件及其对应的ID。<br><br>
     * Gets all the controls of this Gui and their corresponding IDs.
     */
    ControlMap getControls();

    /**
     * 将一个控件添加至Gui中并返回它本身。<br><br>
     * Adds a control to this Gui and returns itself.
     *
     * @return 添加的控件本身
     */
    GuiControl addControl(GuiControl control);

    /**
     * 将一个控件从Gui中移除。<br><br>
     * Removes a control from this Gui.
     */
    void removeControl(GuiControl control);

    /**
     * 获取游戏窗口的宽。<br><br>
     * Gets the width of the game screen.
     */
    default int getScreenWidth() {
        return Minecraft.getMinecraft().displayWidth;
    }

    /**
     * 获取游戏窗口的高。<br><br>
     * Gets the height of the game screen.
     */
    default int getScreenHeight() {
        return Minecraft.getMinecraft().displayHeight;
    }

}
