package core.upcraftlp.craftdev.API.net;

import core.upcraftlp.craftdev.common.CoreInternalPacketHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {

	/**
	 * Register a packet and it's handler using the simple network implementation
	 * @param handler
	 * @param message
	 * @param side
	 */
	public static <REQ extends IMessage, REPLY extends IMessage> void registerPacket(Class<? extends IMessageHandler<REQ, REPLY>> handler, Class<REQ> message, Side side) {
		CoreInternalPacketHandler.add(handler, message, side);
	}
	
}
