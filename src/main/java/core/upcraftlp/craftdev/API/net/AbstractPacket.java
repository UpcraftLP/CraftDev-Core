package core.upcraftlp.craftdev.API.net;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * although you could technically use fields to transfer the data here,
 * you have to save and read everything to/from the actual Message.<br/>
 * use the {@link IMessage#fromBytes(ByteBuf)} and {@link IMessage#toBytes(ByteBuf)} methods!
 * @author UpcraftLP
 */
public abstract class AbstractPacket implements IMessage, IMessageHandler<AbstractPacket, IMessage>{

    /**
     * handle the received message
     */
    @Override
    public IMessage onMessage(AbstractPacket message, MessageContext ctx) {
        return null;
    }

    /**
     * save all data to the buffer
     */
    @Override
    public abstract void fromBytes(ByteBuf buf);

    /**
     * read the data from the buffer
     */
    @Override
    public abstract void toBytes(ByteBuf buf);
}
