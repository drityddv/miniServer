package net.client;

import net.middleware.PacketDecoder;
import net.middleware.PacketEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author : ddv
 * @date : 2019/3/3 下午7:50
 */

public class ClientInitializer extends ChannelInitializer<SocketChannel> {
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();

		pipeline.addLast(new PacketDecoder());
		pipeline.addLast(new PacketEncoder());
		pipeline.addLast(new ClientHandler());
	}
}
