package com.github.zi_jing.cuckoolib.command.debug;

import com.github.zi_jing.cuckoolib.material.type.Material;
import com.github.zi_jing.cuckoolib.metaitem.tool.ToolMetaItem;
import com.github.zi_jing.cuckoolib.metaitem.tool.ToolMetaValueItem;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.server.command.CommandTreeBase;

import java.util.Collections;
import java.util.List;

public class CuckooLibCommand extends CommandTreeBase {
    public CuckooLibCommand() {
        this.addSubcommand(new CommandDebug());
    }

    @Override
    public String getName() {
        return "cuckoolib";
    }

    @Override
    public List<String> getAliases() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "cuckoolib.command.usage";
    }

    public static class CommandDebug extends CommandTreeBase {
        public CommandDebug() {
            this.addSubcommand(new CommandTool());
        }

        @Override
        public String getName() {
            return "debug";
        }

        @Override
        public String getUsage(ICommandSender sender) {
            return "cuckoolib.command.usage.debug";
        }

        public static class CommandTool extends CommandBase {
            @Override
            public String getName() {
                return "tool";
            }

            @Override
            public String getUsage(ICommandSender sender) {
                return "cuckoolib.command.usage.debug.tool";
            }

            @Override
            public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
                if (args.length == 3 && sender.getCommandSenderEntity() instanceof EntityPlayer
                        && ((EntityPlayer) sender.getCommandSenderEntity()).isCreative()) {
                    Item item = Item.getByNameOrId(args[0]);
                    if (item != null && item instanceof ToolMetaItem) {
                        ToolMetaItem toolItem = (ToolMetaItem) item;
                        ToolMetaValueItem metaValueItem = toolItem.getMetaValueItem(args[1]);
                        Material material = Material.getMaterialByName(args[2]);
                        if (metaValueItem != null && material != null
                                && metaValueItem.getToolInfo().validateMaterial(material)) {
                            ItemStack stack = metaValueItem.getItemStack(material);
                            ((EntityPlayer) sender.getCommandSenderEntity()).addItemStackToInventory(stack);
                            return;
                        }
                    }
                }
                sender.sendMessage(new TextComponentTranslation("cuckoolib.command.usage.debug.tool"));
            }
        }
    }
}