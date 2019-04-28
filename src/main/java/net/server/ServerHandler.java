package net.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import middleware.manager.ClazzManager;
import middleware.model.SM_Success;
import middleware.model.User;
import net.model.PacketProtocol;
import net.utils.ProtoStuffUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author : ddv
 * @date : 2019/3/3 上午12:36
 */

public class ServerHandler extends SimpleChannelInboundHandler<PacketProtocol> {

	private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, PacketProtocol msg) throws Exception {
		logger.info(msg.toString());
		Class clazz = ClazzManager.getClazz((int) msg.getId());
		Object object = ClazzManager.readObjectFromBytes(msg.getData(), clazz);
		logger.info(object.toString());

		User user = (User) object;

		//测试回写数据
		SM_Success sm = new SM_Success();
		sm.setUsername(user.getUsername());
		sm.setStateCode(200);
		sm.setTokenList(Arrays.asList(1, 2, 3, 4, 5, 6));

		PacketProtocol protocol = new PacketProtocol();
		protocol.setId((byte) 2);
		protocol.setData(ProtoStuffUtil.serialize(sm));
		protocol.setLength(protocol.getData().length);

		ctx.writeAndFlush(protocol);
	}
}
