package net.server;

import java.util.Map;

import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import utils.ResourceUtil;

/**
 * @author ddv
 */
@Component
public class Server {
    private ServerBootstrap serverBootstrap;
    private EventLoopGroup boss;
    private EventLoopGroup worker;

    private int bossNum;
    private int workerNum;

    private void init() {

        boss = new NioEventLoopGroup(bossNum);
        worker = new NioEventLoopGroup(workerNum);
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss, worker);
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.childHandler(new ServerInitializer());
    }

    public void run() {
        initNetParams();
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

    private void initNetParams() {
        Map<String, Object> head = ResourceUtil.getYmlRoot("server-dev.yml");
        Map<String, Object> root = ResourceUtil.getNode("miniServer", head);
        Map<String, Object> net = ResourceUtil.getNode("net", root);
        Map<String, Object> boss = ResourceUtil.getNode("boss", net);
        Map<String, Object> worker = ResourceUtil.getNode("worker", net);
        this.bossNum = (int)boss.get("threadNum");
        this.workerNum = (int)worker.get("threadNum");
    }

}
