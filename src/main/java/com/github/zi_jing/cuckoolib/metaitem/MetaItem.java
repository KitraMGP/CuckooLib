package com.github.zi_jing.cuckoolib.metaitem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.Validate;

import com.github.zi_jing.cuckoolib.CuckooLib;
import com.github.zi_jing.cuckoolib.metaitem.module.IContainerItemProvider;
import com.github.zi_jing.cuckoolib.metaitem.module.IDurabilityBarProvider;
import com.github.zi_jing.cuckoolib.metaitem.module.IItemFuel;
import com.github.zi_jing.cuckoolib.metaitem.module.IItemInteraction;
import com.github.zi_jing.cuckoolib.metaitem.module.IItemModelProvider;
import com.github.zi_jing.cuckoolib.metaitem.module.IItemNameProvider;
import com.github.zi_jing.cuckoolib.metaitem.module.IItemTooltipProvider;
import com.github.zi_jing.cuckoolib.metaitem.module.IItemUse;
import com.github.zi_jing.cuckoolib.util.IRegistrable;

import gnu.trove.iterator.TShortObjectIterator;
import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber(modid = CuckooLib.MODID)
public abstract class MetaItem<T extends MetaValueItem> extends Item implements IRegistrable {
	protected static final List<MetaItem> META_ITEMS = new ArrayList<>();

	protected final String modid;

	/** 用于存储MetaValueItem的注册表 */
	protected final TShortObjectMap<T> metaItem;

	/** 用于存储MetaValueItem模型的注册表 */
	protected final TShortObjectMap<List<ModelResourceLocation>> itemModel;

	public MetaItem(String modid, String name) {
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setNoRepair();
		this.setRegistryName(modid, name);
		this.modid = modid;
		this.metaItem = new TShortObjectHashMap<T>();
		this.itemModel = new TShortObjectHashMap<List<ModelResourceLocation>>();
		META_ITEMS.add(this);
	}

	public MetaItem(ResourceLocation registryName) {
		this(registryName.getResourceDomain(), registryName.getResourcePath());
	}

	@Override
	public void register() {
		META_ITEMS.add(this);
	}

	public static List<MetaItem> getMetaItems() {
		return Collections.unmodifiableList(META_ITEMS);
	}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		META_ITEMS.forEach(MetaItem::registerItemModel);
	}

	@SubscribeEvent
	public static void registerMetaItems(Register<Item> e) {
		e.getRegistry().registerAll(META_ITEMS.toArray(new MetaItem[0]));
	}

	public static void registerItemColor() {
		META_ITEMS.forEach(
				(item) -> Minecraft.getMinecraft().getItemColors().registerItemColorHandler(item::getItemColor, item));
	}

	@Override
	public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			this.metaItem.forEachValue((metaValueItem) -> {
				items.add(metaValueItem.getItemStack());
				return true;
			});
		}
	}

	@SideOnly(Side.CLIENT)
	protected int getItemColor(ItemStack stack, int tintIndex) {
		return 0xffffff;
	}

	@SideOnly(Side.CLIENT)
	public void registerItemModel() {
		this.metaItem.forEachEntry((id, metaValueItem) -> {
			int modelCount = metaValueItem.modelCount;
			if (modelCount == 1) {
				ResourceLocation location = this.getItemModel(metaValueItem);
				ModelBakery.registerItemVariants(this, location);
				this.itemModel.put(id, Arrays.asList(new ModelResourceLocation(location, "inventory")));
			} else if (modelCount > 1) {
				List<ModelResourceLocation> models = new ArrayList<>();
				for (int i = 0; i < modelCount; i++) {
					ResourceLocation location = this.getItemModel(metaValueItem, i);
					ModelBakery.registerItemVariants(this, location);
					models.add(new ModelResourceLocation(location, "inventory"));
				}
				this.itemModel.put(id, models);
			}
			return true;
		});
		ModelLoader.setCustomMeshDefinition(this, (stack) -> {
			short metadata = (short) stack.getMetadata();
			MetaValueItem metaValueItem = this.getMetaValueItem(stack);
			if (metaValueItem == null) {
				return ModelBakery.MODEL_MISSING;
			}
			int modelCount = metaValueItem.modelCount;
			List<ModelResourceLocation> models = this.itemModel.get(metadata);
			if (modelCount == 1 && !models.isEmpty()) {
				return models.get(0);
			} else if (modelCount > 1 && !models.isEmpty()) {
				if (metaValueItem.containsModule(IItemModelProvider.class)) {
					return models.get(metaValueItem.getModule(IItemModelProvider.class).getModelIndex(stack));
				}
				return models.get(0);
			}
			return ModelBakery.MODEL_MISSING;
		});
	}

	public ResourceLocation getItemModel(T metaValueItem, int id) {
		return new ResourceLocation(this.modid, "metaitem/" + metaValueItem.unlocalizedName + "." + id);
	}

	public ResourceLocation getItemModel(T metaValueItem) {
		return new ResourceLocation(this.modid, "metaitem/" + metaValueItem.unlocalizedName);
	}

	protected abstract T createMetaValueItem(short id, String unlocalizedName);

	/**
	 * 给这个MetaItem添加子物品
	 *
	 * @param id              物品所对应的metadata值，范围为[0, {@code Short.MAX_VALUE} - 1]。
	 *                        该值在同一个{@link MetaItem}中必须是唯一且不变的，它不应该随着版本更新而改变。
	 * @param unlocalizedName 物品的本地化键(无需添加modid，modid会自动添加)。
	 *                        例子：当该参数为{@code "eok_symbol"}时且{@code
	 *     modid}          为{@code "eok"}时，
	 *                        物品名字的本地化键将为{@code "metaitem.eok.eok_symbol.name"}
	 * @return 方法创建的{@link MetaValueItem}实例。你可以保留它，也可以使用
	 *         {@link #getMetaValueItem(short)}获取它。
	 */
	public T addItem(int id, String unlocalizedName) {
		Validate.inclusiveBetween(0, Short.MAX_VALUE - 1, id,
				"MetaValueItem ID [ " + id + " ] of item [ " + unlocalizedName + " ] is invalid");
		if (this.metaItem.containsKey((short) id)) {
			throw new IllegalArgumentException(
					"MetaValueItem ID [ " + id + " ] of item [ " + unlocalizedName + " ] is registered");
		}
		T metaValueItem = this.createMetaValueItem((short) id, unlocalizedName);
		this.metaItem.put((short) id, metaValueItem);
		return metaValueItem;
	}

	public T getMetaValueItem(short id) {
		if (this.metaItem.containsKey(id)) {
			return this.metaItem.get(id);
		}
		return null;
	}

	public T getMetaValueItem(ItemStack stack) {
		return this.getMetaValueItem((short) stack.getMetadata());
	}

	public T getMetaValueItem(String unlocalizedName) {
		TShortObjectIterator<T> ite = this.metaItem.iterator();
		while (ite.hasNext()) {
			ite.advance();
			T metaValueItem = ite.value();
			if (metaValueItem.getUnlocalizedName().equals(unlocalizedName)) {
				return metaValueItem;
			}
		}
		return null;
	}

	@Nonnull
	@Override
	public String getUnlocalizedName(@Nonnull ItemStack stack) {
		MetaValueItem metaValueItem = this.getMetaValueItem(stack);
		return metaValueItem != null ? "metaitem." + this.modid + "." + metaValueItem.getUnlocalizedName()
				: "metaitem.error";
	}

	@Override
	public int getItemStackLimit(@Nonnull ItemStack stack) {
		MetaValueItem metaValueItem = this.getMetaValueItem(stack);
		return metaValueItem != null ? metaValueItem.getItemStackLimit() : 64;
	}

	@Nonnull
	@Override
	public String getItemStackDisplayName(@Nonnull ItemStack stack) {
		MetaValueItem metaValueItem = this.getMetaValueItem(stack);
		if (metaValueItem != null && metaValueItem.containsModule(IItemNameProvider.class)) {
			return metaValueItem.getModule(IItemNameProvider.class).getItemStackDisplayName(stack);
		}
		return super.getItemStackDisplayName(stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(@Nonnull ItemStack stack, World world, @Nonnull List<String> tooltip,
			@Nonnull ITooltipFlag flag) {
		MetaValueItem metaValueItem = this.getMetaValueItem(stack);
		if (metaValueItem != null && metaValueItem.containsModule(IItemTooltipProvider.class)) {
			metaValueItem.getModule(IItemTooltipProvider.class).addInformation(stack, world, tooltip, flag);
			return;
		}
		super.getItemStackDisplayName(stack);
	}

	/* ---------- ItemContainerModule ---------- */

	/** @see net.minecraft.item.Item#getContainerItem */
	@Nonnull
	@Override
	public ItemStack getContainerItem(@Nonnull ItemStack stack) {
		MetaValueItem metaValueItem = this.getMetaValueItem(stack);
		if (metaValueItem != null && metaValueItem.containsModule(IContainerItemProvider.class)) {
			return metaValueItem.getModule(IContainerItemProvider.class).getContainerItem(stack);
		}
		return super.getContainerItem(stack);
	}

	/* ---------- ItemDurabilityBarModule ---------- */

	/** @see net.minecraft.item.Item#showDurabilityBar */
	@Override
	public boolean showDurabilityBar(@Nonnull ItemStack stack) {
		MetaValueItem metaValueItem = this.getMetaValueItem(stack);
		if (metaValueItem != null && metaValueItem.containsModule(IDurabilityBarProvider.class)) {
			return metaValueItem.getModule(IDurabilityBarProvider.class).showDurabilityBar(stack);
		}
		return super.showDurabilityBar(stack);
	}

	/** @see net.minecraft.item.Item#getDurabilityForDisplay */
	@Override
	public double getDurabilityForDisplay(@Nonnull ItemStack stack) {
		MetaValueItem metaValueItem = this.getMetaValueItem(stack);
		if (metaValueItem != null && metaValueItem.containsModule(IDurabilityBarProvider.class)) {
			return metaValueItem.getModule(IDurabilityBarProvider.class).getDurabilityForDisplay(stack);
		}
		return super.getDurabilityForDisplay(stack);
	}

	/** @see net.minecraft.item.Item#getRGBDurabilityForDisplay */
	@Override
	public int getRGBDurabilityForDisplay(@Nonnull ItemStack stack) {
		MetaValueItem metaValueItem = this.getMetaValueItem(stack);
		if (metaValueItem != null && metaValueItem.containsModule(IDurabilityBarProvider.class)) {
			return metaValueItem.getModule(IDurabilityBarProvider.class).getRGBDurabilityForDisplay(stack);
		}
		return super.getRGBDurabilityForDisplay(stack);
	}

	/* ---------- ItemInteractionModule ---------- */

	/** @see net.minecraft.item.Item#itemInteractionForEntity */
	@Override
	public boolean itemInteractionForEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer playerIn,
			@Nonnull EntityLivingBase target, @Nonnull EnumHand hand) {
		MetaValueItem metaValueItem = this.getMetaValueItem(stack);
		if (metaValueItem != null && metaValueItem.containsModule(IItemInteraction.class)) {
			return metaValueItem.getModule(IItemInteraction.class).itemInteractionForEntity(stack, playerIn, target,
					hand);
		}
		return super.itemInteractionForEntity(stack, playerIn, target, hand);
	}

	/** @see net.minecraft.item.Item#onItemRightClick */
	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, EntityPlayer player, @Nonnull EnumHand hand) {
		MetaValueItem metaValueItem = this.getMetaValueItem(player.getHeldItem(hand));
		if (metaValueItem != null && metaValueItem.containsModule(IItemInteraction.class)) {
			return metaValueItem.getModule(IItemInteraction.class).onItemRightClick(world, player, hand);
		}
		return super.onItemRightClick(world, player, hand);
	}

	@Override
	public boolean onLeftClickEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, @Nonnull Entity entity) {
		MetaValueItem metaValueItem = this.getMetaValueItem(stack);
		if (metaValueItem != null && metaValueItem.containsModule(IItemInteraction.class)) {
			return metaValueItem.getModule(IItemInteraction.class).onLeftClickEntity(stack, player, entity);
		}
		return super.onLeftClickEntity(stack, player, entity);
	}

	/* ---------- ItemUseModule ---------- */

	@Nonnull
	@Override
	public EnumAction getItemUseAction(@Nonnull ItemStack stack) {
		MetaValueItem metaValueItem = this.getMetaValueItem(stack);
		if (metaValueItem != null && metaValueItem.containsModule(IItemUse.class)) {
			return metaValueItem.getModule(IItemUse.class).getItemUseAction(stack);
		}
		return super.getItemUseAction(stack);
	}

	@Override
	public int getMaxItemUseDuration(@Nonnull ItemStack stack) {
		MetaValueItem metaValueItem = this.getMetaValueItem(stack);
		if (metaValueItem != null && metaValueItem.containsModule(IItemUse.class)) {
			return metaValueItem.getModule(IItemUse.class).getMaxItemUseDuration(stack);
		}
		return super.getMaxItemUseDuration(stack);
	}

	@Nonnull
	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, @Nonnull World world, @Nonnull BlockPos pos,
			@Nonnull EnumFacing side, float hitX, float hitY, float hitZ, @Nonnull EnumHand hand) {
		MetaValueItem metaValueItem = this.getMetaValueItem(player.getHeldItem(hand));
		if (metaValueItem != null && metaValueItem.containsModule(IItemUse.class)) {
			return metaValueItem.getModule(IItemUse.class).onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ,
					hand);
		}
		return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
	}

	@Nonnull
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, @Nonnull World world, @Nonnull BlockPos pos,
			@Nonnull EnumHand hand, @Nonnull EnumFacing side, float hitX, float hitY, float hitZ) {
		MetaValueItem metaValueItem = this.getMetaValueItem(player.getHeldItem(hand));
		if (metaValueItem != null && metaValueItem.containsModule(IItemUse.class)) {
			return metaValueItem.getModule(IItemUse.class).onItemUse(player, world, pos, hand, side, hitX, hitY, hitZ);
		}
		return super.onItemUse(player, world, pos, hand, side, hitX, hitY, hitZ);
	}

	@Override
	public void onUsingTick(@Nonnull ItemStack stack, @Nonnull EntityLivingBase player, int count) {
		MetaValueItem metaValueItem = this.getMetaValueItem(stack);
		if (metaValueItem != null && metaValueItem.containsModule(IItemUse.class)) {
			metaValueItem.getModule(IItemUse.class).onUsingTick(stack, player, count);
			return;
		}
		super.onUsingTick(stack, player, count);
	}

	@Override
	public void onPlayerStoppedUsing(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull EntityLivingBase player,
			int timeLeft) {
		MetaValueItem metaValueItem = this.getMetaValueItem(stack);
		if (metaValueItem != null && metaValueItem.containsModule(IItemUse.class)) {
			metaValueItem.getModule(IItemUse.class).onPlayerStoppedUsing(stack, world, player, timeLeft);
			return;
		}
		super.onPlayerStoppedUsing(stack, world, player, timeLeft);
	}

	@Nonnull
	@Override
	public ItemStack onItemUseFinish(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull EntityLivingBase player) {
		MetaValueItem metaValueItem = this.getMetaValueItem(stack);
		if (metaValueItem != null && metaValueItem.containsModule(IItemUse.class)) {
			return metaValueItem.getModule(IItemUse.class).onItemUseFinish(stack, world, player);
		}
		return super.onItemUseFinish(stack, world, player);
	}

	/* ---------- ItemFuelModule ---------- */

	@Override
	public int getItemBurnTime(@Nonnull ItemStack stack) {
		MetaValueItem metaValueItem = this.getMetaValueItem(stack);
		if (metaValueItem != null && metaValueItem.containsModule(IItemFuel.class)) {
			return metaValueItem.getModule(IItemFuel.class).getItemBurnTime(stack);
		}
		return super.getItemBurnTime(stack);
	}
}