package com.github.zi_jing.cuckoolib.gui;

import net.minecraft.entity.player.EntityPlayer;

public interface IModularGuiHolder {
    IGuiHolderCodec getCodec();

    ModularGuiInfo createGuiInfo(EntityPlayer player);
}