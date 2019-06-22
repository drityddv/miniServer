package net.middleware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.model.PacketProtocol;

/**
 * @author : ddv
 * @since : 2019/4/26 下午3:31
 */

public class PacketEncoder extends MessageToByteEncoder<PacketProtocol> {

    private static final Logger logger = LoggerFactory.getLogger(PacketEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, PacketProtocol protocol, ByteBuf out) throws Exception {
        out.writeInt(protocol.getId());
        out.writeInt(protocol.getLength());
        out.writeBytes(protocol.getData());
    }
}
