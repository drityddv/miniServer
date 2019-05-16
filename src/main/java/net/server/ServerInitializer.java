package net.server;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import net.middleware.PacketDecoder;
import net.middleware.PacketEncoder;

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
        pipeline.addLast(new IdleStateHandler(30, 30, 30, TimeUnit.SECONDS));
        pipeline.addLast(new SessionHandler());
        pipeline.addLast(new ServerHandler());
    }
}
