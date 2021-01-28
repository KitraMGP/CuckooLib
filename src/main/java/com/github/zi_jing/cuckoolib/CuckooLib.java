package com.github.zi_jing.cuckoolib;

import java.util.Optional;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.zi_jing.cuckoolib.gui.ModularGuiInfo;
import com.github.zi_jing.cuckoolib.gui.TileEntityCodec;
import com.github.zi_jing.cuckoolib.item.MaterialItem;
import com.github.zi_jing.cuckoolib.item.MaterialToolItem;
import com.github.zi_jing.cuckoolib.network.IMessage;
import com.github.zi_jing.cuckoolib.network.MessageCapabilityUpdate;
import com.github.zi_jing.cuckoolib.network.MessageGuiToClient;
import com.github.zi_jing.cuckoolib.network.MessageGuiToServer;
import com.github.zi_jing.cuckoolib.network.MessageModularGuiOpen;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

@Mod(CuckooLib.MODID)
@Mod.EventBusSubscriber(modid = CuckooLib.MODID, bus = Bus.MOD)
public class CuckooLib {
	public static final String MODID = "cuckoolib";
	public static final String MODNAME = "Cuckoo Lib";
	public static final String VERSION = "1.0.1";

	private static final Logger LOGGER = LogManager.getLogger("CuckooLib");

	public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
			.named(new ResourceLocation(MODID, "main")).networkProtocolVersion(() -> VERSION)
			.serverAcceptedVersions(VERSION::equals).clientAcceptedVersions(VERSION::equals).simpleChannel();

	public static final ItemGroup GROUP_MATERIAL = new ItemGroup(MODID + "_material") {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(Items.IRON_INGOT);
		}
	};

	public CuckooLib() {

	}

	public static Logger getLogger() {
		return LOGGER;
	}

	@SubscribeEvent
	public static void setup(FMLCommonSetupEvent event) {
		registerMessage(0, MessageModularGuiOpen.class, MessageModularGuiOpen::decode, NetworkDirection.PLAY_TO_CLIENT);
		registerMessage(1, MessageGuiToServer.class, MessageGuiToServer::decode, NetworkDirection.PLAY_TO_SERVER);
		registerMessage(2, MessageGuiToClient.class, MessageGuiToClient::decode, NetworkDirection.PLAY_TO_CLIENT);
		registerMessage(3, MessageCapabilityUpdate.class, MessageCapabilityUpdate::decode,
				NetworkDirection.PLAY_TO_CLIENT);
		ModularGuiInfo.registerGuiHolderCodec(TileEntityCodec.INSTANCE);
		MaterialToolItem.REGISTERED_TOOL_ITEM.forEach((item) -> {
			if (item instanceof MaterialToolItem) {
				Minecraft.getInstance().getItemColors().register(((MaterialToolItem) item)::getItemColor, item);
			}
		});
		MaterialItem.REGISTERED_MATERIAL_ITEM.values().forEach((map) -> map.values()
				.forEach((item) -> Minecraft.getInstance().getItemColors().register(item::getItemColor, item)));
	}

	private static <T extends IMessage> void registerMessage(int id, Class<T> type, Function<PacketBuffer, T> decoder,
			NetworkDirection direction) {
		CHANNEL.registerMessage(id, type, (msg, buf) -> {
			msg.encode(buf);
		}, decoder, (msg, ctx) -> {
			msg.process(ctx);
			ctx.get().setPacketHandled(true);
		}, Optional.of(direction));
	}
}