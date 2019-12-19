package net.server;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import net.codec.PacketDecoder;
import net.codec.PacketEncoder;

/**
 * @author : ddv
 * @date : 2019/3/5 下午5:01
 */

public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    private static final int MAX_TIME_SECOND = 60 * 60;

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new PacketDecoder());
        pipeline.addLast(new PacketEncoder());
        pipeline.addLast(new ServerHandler());
    }
}
