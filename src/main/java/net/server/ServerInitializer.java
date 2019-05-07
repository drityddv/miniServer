package net.server;

import net.middleware.PacketDecoder;
import net.middleware.PacketEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author : ddv
 * @date : 2019/3/5 下午5:01
 */

public class ServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();

		pipeline.addLast(new PacketDecoder());
		pipeline.addLast(new PacketEncoder());
		pipeline.addLast(new SessionHandler());
		pipeline.addLast(new ServerHandler());
	}
}
