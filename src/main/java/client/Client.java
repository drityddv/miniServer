package client;

import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import client.command.ClientCommand;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
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
    private static final ThreadPoolExecutor[] TEST_SERVICE = new ThreadPoolExecutor[10];
    private static Set<Channel> channelSet = new ConcurrentSet<>();

    public static void main(String[] args) {
        start(args);
        // test(new String[]{"1"});
    }

    private static void start(String[] args) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        try {
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                .handler(ClientInitializer.valueOf(args.length <= 0 ? "ddv" : args[0]));
            ChannelFuture future = bootstrap.connect(args[0], 8000).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    private static void test(String[] args) {
        int index = Integer.parseInt(args[0]);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CountDownLatch countDownLatch = new CountDownLatch(1);

        try {
            executorService.submit(new ClientCommand(index));
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
