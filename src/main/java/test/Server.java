package test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import net.server.ServerInitializer;
import sun.misc.Signal;
import utils.LogUtil;
import utils.OsUtil;

/**
 * @author : ddv
 * @since : 2019/12/17 10:35 AM
 */

public class Server {
    public static void main(String[] args) {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(boss, worker);

        serverBootstrap.channel(NioServerSocketChannel.class);

        // handler针对boss线程连接处理
        serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));
        // childHandler对worker线程组连接处理
        serverBootstrap.childHandler(new ServerInitializer());

        try {
            Signal signal = new Signal(OsUtil.getSignalName());
            Signal.handle(signal, signal1 -> {

            });
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                LogUtil.log("shut down server...");
            }));
            ChannelFuture future = serverBootstrap.bind(9000).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
