package com.github.zi_jing.cuckoolib.metaitem.tool;

import com.github.zi_jing.cuckoolib.material.type.Material;
import com.github.zi_jing.cuckoolib.metaitem.MetaItem;
import com.github.zi_jing.cuckoolib.util.NBTAdapter;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class ToolMetaItem extends MetaItem<ToolMetaValueItem> {
    public ToolMetaItem(String modid, String name) {
        super(modid, name);
    }

    public ToolMetaItem(ResourceLocation registryName) {
        super(registryName);
    }

    public static ToolMetaItem getToolMetaItem(ItemStack stack) {
        return (ToolMetaItem) stack.getItem();
    }

    public boolean validateToolMaterial(Material material) {
        return material.hasFlag(Material.GENERATE_TOOL);
    }

    public NBTTagCompound getToolBaseNBT(ItemStack stack) {
        return NBTAdapter.getTagCompound(NBTAdapter.getItemStackCompound(stack), "tool_info", new NBTTagCompound());
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected int getItemColor(ItemStack stack, int tintIndex) {
        if (tintIndex == 0) {
            Material material = this.getToolMaterial(stack);
            if (material != null) {
                return material.getColor();
            }
        }
        return 0xffffff;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        Material material = this.getToolMaterial(stack);
        if (material != null) {
            return I18n.format(this.getUnlocalizedName(stack) + ".name", material.getLocalizedName());
        }
        return I18n.format(this.getUnlocalizedName(stack) + ".name", "");
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos,
                                    EntityLivingBase entity) {
        ToolMetaValueItem toolMetaValueItem = this.getMetaValueItem(stack);
        if (toolMetaValueItem != null) {
            this.damageItem(stack, toolMetaValueItem.getToolInfo().getBlockBreakDamage());
        }
        return true;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        ToolMetaValueItem toolMetaValueItem = this.getMetaValueItem(stack);
        if (toolMetaValueItem != null) {
            IToolInfo toolInfo = toolMetaValueItem.getToolInfo();
            if (toolInfo.canHarvestBlock(state, stack)) {
                return toolInfo.getDestroySpeed(state, stack);
            }
        }
        return 1;
    }

    @Override
    public boolean canHarvestBlock(IBlockState state, ItemStack stack) {
        ToolMetaValueItem toolMetaValueItem = this.getMetaValueItem(stack);
        if (toolMetaValueItem != null) {
            return toolMetaValueItem.getToolInfo().canHarvestBlock(state, stack);
        }
        return false;
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass, EntityPlayer player, IBlockState state) {
        if (state == null) {
            return -1;
        }
        ToolMetaValueItem toolMetaValueItem = this.getMetaValueItem(stack);
        if (toolMetaValueItem == null) {
            return -1;
        }
        IToolInfo toolInfo = toolMetaValueItem.getToolInfo();
        if (toolInfo.canHarvestBlock(state, stack)) {
            return toolInfo.getHarvestLevel(stack);
        }
        return -1;
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        ToolMetaValueItem toolMetaValueItem = this.getMetaValueItem(stack);
        if (toolMetaValueItem == null) {
            return;
        }
        IToolInfo toolInfo = toolMetaValueItem.getToolInfo();
        toolInfo.onToolCreated(stack, world, player);
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        ToolMetaValueItem toolMetaValueItem = this.getMetaValueItem(stack);
        if (toolMetaValueItem != null) {
            IToolInfo toolInfo = toolMetaValueItem.getToolInfo();
            toolInfo.addInformation(stack, world, tooltip, flag);
        }
        super.addInformation(stack, world, tooltip, flag);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        ToolMetaValueItem metaValueItem = this.getMetaValueItem(stack);
        return metaValueItem != null ? "metaitem." + this.modid + ".tool." + metaValueItem.getUnlocalizedName()
                : "metaitem.tool.error";
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return this.getToolDamage(stack) > 0;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return ((double) this.getToolDamage(stack)) / this.getToolMaxDamage(stack);
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        double value = this.getDurabilityForDisplay(stack);
        if (value >= 0.7 && value < 0.9) {
            return 0xffff00;
        }
        if (value >= 0.9) {
            return 0xff0000;
        }
        return 0x00ff00;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        return super.onItemRightClick(world, player, hand);
    }

    public boolean enableItemDamage(ItemStack stack) {
        ToolMetaValueItem toolMetaValueItem = this.getMetaValueItem(stack);
        if (toolMetaValueItem != null) {
            return toolMetaValueItem.getToolInfo().getMaxDamage(stack) >= 0;
        }
        return false;
    }

    public boolean damageItem(ItemStack stack, int value) {
        if (!this.enableItemDamage(stack)) {
            return false;
        }
        NBTTagCompound nbt = this.getToolBaseNBT(stack);
        int maxDamage = this.getToolMaxDamage(stack);
        int damage = this.getToolDamage(stack);
        if (damage + value < maxDamage) {
            nbt.setInteger("damage", damage + value);
            return false;
        }
        try {
            Field fieldItem = Arrays.stream(ItemStack.class.getDeclaredFields())
                    .filter(field -> field.getType() == Item.class).findFirst()
                    .orElseThrow(ReflectiveOperationException::new);
            fieldItem.setAccessible(true);
            fieldItem.set(stack, Items.AIR);
        } catch (SecurityException | ReflectiveOperationException e) {
            e.printStackTrace();
        }
        return true;
    }

    public int getToolMaxDamage(ItemStack stack) {
        return this.getToolInfo(stack).getMaxDamage(stack);
    }

    public int getToolDamage(ItemStack stack) {
        if (!this.enableItemDamage(stack)) {
            return 0;
        }
        NBTTagCompound nbt = this.getToolBaseNBT(stack);
        if (nbt.hasKey("damage")) {
            return nbt.getInteger("damage");
        }
        return 0;
    }

    public Material getToolMaterial(ItemStack stack) {
        NBTTagCompound nbt = this.getToolBaseNBT(stack);
        if (nbt.hasKey("material")) {
            return Material.getMaterialByName(nbt.getString("material"));
        }
        return null;
    }

    @Override
    protected ToolMetaValueItem createMetaValueItem(short id, String unlocalizedName) {
        return new ToolMetaValueItem(this, id, unlocalizedName);
    }

    public IToolInfo getToolInfo(short id) {
        return this.getMetaValueItem(id).getToolInfo();
    }

    public IToolInfo getToolInfo(ItemStack stack) {
        return this.getMetaValueItem(stack).getToolInfo();
    }
}