package com.github.zi_jing.cuckoolib.common.util;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import java.io.*;

public class DataUtils {
    public static ItemStack NBTToItemStack(NBTTagCompound compound) {
        ItemStack stack = ItemStack.EMPTY;
        stack.deserializeNBT(compound);
        return stack;
    }

    public static NBTTagCompound ItemStackToNBT(ItemStack stack) {
        return stack.serializeNBT();
    }

    public static InputStream getResource(ResourceLocation location) throws IOException {
        return Minecraft.getMinecraft().getResourceManager().getResource(location).getInputStream();
    }

    public static NBTTagCompound readTagFromStream(InputStream stream) throws IOException {
        return CompressedStreamTools.readCompressed(stream);
    }

    public static void writeTagToStream(OutputStream stream, NBTTagCompound compound) throws IOException {
        CompressedStreamTools.writeCompressed(compound, stream);
    }

    public static NBTTagCompound readTagFromFile(File file) throws FileNotFoundException, IOException {
        return readTagFromStream(new FileInputStream(file));
    }

    public static void writeTagToFile(File file, NBTTagCompound compound) throws FileNotFoundException, IOException {
        writeTagToStream(new FileOutputStream(file), compound);
    }
}
