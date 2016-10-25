package core.upcraftlp.craftdev.common;

import core.upcraftlp.craftdev.common.main.CraftDevReference;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class CoreInternalPacketHandler {

	private static SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(CraftDevReference.MODID.toLowerCase());
	private static int packedID = 0;
	
	public static <REQ extends IMessage, REPLY extends IMessage> void add(Class<? extends IMessageHandler<REQ, REPLY>> handler, Class<REQ> message, Side side) {
		INSTANCE.registerMessage(handler, message, packedID++, side);
	}
}
