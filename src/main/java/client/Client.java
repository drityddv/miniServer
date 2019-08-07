package client;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.internal.ConcurrentSet;

/**
 * @author : ddv
 * @date : 2019/3/3 下午7:38
 */

public class Client {
    private static Set<ChannelFuture> connectionSet = new ConcurrentSet<>();
    // public static void main(String[] args) {
    // EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
    // Bootstrap bootstrap = new Bootstrap();
    //
    // try {
    // bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
    // .handler(ClientInitializer.valueOf(args.length <= 0 ? "ddv" : args[0]));
    // ChannelFuture future = bootstrap.connect("localhost", 8000).sync();
    // future.channel().closeFuture().sync();
    // } catch (Exception e) {
    // e.printStackTrace();
    // } finally {
    // eventLoopGroup.shutdownGracefully();
    // }
    // }

    public static void main(String[] args) {



        for (int i = 0; i < 100; i++) {
            String k = i + "";
            EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();

            try {
                bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(ClientInitializer.valueOf(k));
                ChannelFuture future = bootstrap.connect("localhost", 8000).sync();
				connectionSet.add(future);
                future.channel().closeFuture();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
