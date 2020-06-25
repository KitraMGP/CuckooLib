package com.github.zi_jing.cuckoolib.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

public class ClientUtils {
    public static int getDisplayWidth() {
        ScaledResolution r = new ScaledResolution(Minecraft.getMinecraft());
        return r.getScaledWidth();
    }

    public static int getDisplayHeight() {
        ScaledResolution r = new ScaledResolution(Minecraft.getMinecraft());
        return r.getScaledHeight();
    }

    public static int getMouseX() {
        return Mouse.getX() * getDisplayWidth() / Minecraft.getMinecraft().displayWidth;
    }

    public static int getMouseY() {
        return getDisplayHeight() - Mouse.getY() * getDisplayHeight() / Minecraft.getMinecraft().displayHeight - 1;
    }
}
