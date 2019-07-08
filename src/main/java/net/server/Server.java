package net.server;

import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author ddv
 */
@Component
public class Server {
    private ServerBootstrap serverBootstrap;
    private EventLoopGroup boss;
    private EventLoopGroup worker;

    private void init() {
        boss = new NioEventLoopGroup();
        worker = new NioEventLoopGroup();
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss, worker);
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.childHandler(new ServerInitializer());
    }

    public void run() {
        init();
        try {
            ChannelFuture future = serverBootstrap.bind(8000).sync();
            future.channel().closeFuture();
        } catch (InterruptedException e) {
            e.printStackTrace();
            shutdown();
        }
    }

    public void shutdown() {
        if (boss != null && !boss.isShutdown()) {
            boss.shutdownGracefully();
        }

        if (worker != null && !worker.isShutdown()) {
            worker.shutdownGracefully();
        }
    }

}
