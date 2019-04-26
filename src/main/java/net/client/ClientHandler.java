package net.client;

import middleware.model.User;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.model.PacketProtocol;
import net.utils.ProtoStuffUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : ddv
 * @date : 2019/3/3 下午7:49
 */

public class ClientHandler extends SimpleChannelInboundHandler<PacketProtocol> {

	private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, PacketProtocol msg) throws Exception {
		logger.info(msg.toString());
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		PacketProtocol protocol = new PacketProtocol();
		protocol.setId((byte) 1);

		User user = new User();
		user.setUid(1);
		user.setUsername("ddv");
		user.setAccountId("account");
		user.setPassword("pwd");
		Map<String, String> map = new HashMap<>(16);
		map.put("1", "1");
		map.put("2", "2");
		user.setMap(map);


		byte[] data = ProtoStuffUtil.serialize(user);

		protocol.setLength(data.length);
		protocol.setData(data);

		ctx.writeAndFlush(protocol);

	}
}
