package com.github.zi_jing.cuckoolib.item;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemStackUtil {
    /**
     * 使用原版方式在世界中生成一个掉落物实体
     *
     * @param world 生成位置所在的世界
     * @param pos   生成位置
     * @param stack 要生成的{@link ItemStack}
     */
    public static void dropItem(World world, BlockPos pos, ItemStack stack) {
        double offX = (double) (world.rand.nextFloat() * 0.5F) + 0.25D;
        double offY = (double) (world.rand.nextFloat() * 0.5F) + 0.25D;
        double offZ = (double) (world.rand.nextFloat() * 0.5F) + 0.25D;
        EntityItem entityitem =
                new EntityItem(
                        world,
                        (double) pos.getX() + offX,
                        (double) pos.getY() + offY,
                        (double) pos.getZ() + offZ,
                        stack);
        entityitem.setDefaultPickupDelay();
        world.spawnEntity(entityitem);
    }
}
