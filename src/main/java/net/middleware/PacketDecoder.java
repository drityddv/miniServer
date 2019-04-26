package net.middleware;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import net.model.PacketProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 解码器
 *
 * @author : ddv
 * @since : 2019/4/26 下午3:12
 */

public class PacketDecoder extends ReplayingDecoder<PacketProtocol> {

	private static final Logger logger = LoggerFactory.getLogger(PacketDecoder.class);

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		logger.debug("packet decoder invoked!");
		byte id = in.readByte();
		int length = in.readInt();
		byte[] data = new byte[length];

		in.readBytes(data);

		PacketProtocol packet = new PacketProtocol();

		packet.setId(id);
		packet.setLength(length);
		packet.setData(data);

		out.add(packet);
		logger.debug("packet decoder:{}", packet.toString());
	}
}
