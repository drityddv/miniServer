package net.middleware;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.model.PacketProtocol;

/**
 * @author : ddv
 * @since : 2019/4/26 下午3:31
 */

public class PacketEncoder extends MessageToByteEncoder<PacketProtocol> {
	@Override
	protected void encode(ChannelHandlerContext ctx, PacketProtocol protocol, ByteBuf out) throws Exception {
		out.writeByte(protocol.getId());
		out.writeInt(protocol.getLength());
		out.writeBytes(protocol.getData());
	}
}
