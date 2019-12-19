package client.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.ClientInitializer;
import game.world.base.packet.CM_ChangeMap;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import net.model.PacketProtocol;

/**
 * @author : ddv
 * @since : 2019/8/8 9:14 AM
 */

public class ClientCommand implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger("测试");
    int i;
    int end;
    Random random = new Random();
    private List<Integer> mapIdList = Arrays.asList(1, 2, 4, 10, 11);
    private List<Channel> channelList = new ArrayList<>();

    public ClientCommand(int i) {
        this.i = 100 * (i);
        this.end = 1000 * (i + 1);
    }

    public static ClientCommand valueOf(int i) {
        return new ClientCommand(i);
    }

    @Override
    public void run() {
        while (i < end - 1) {
            try {
                Thread.sleep(100 * 5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String k = i + "";
            EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            try {
                bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(ClientInitializer.valueOf(k));
                ChannelFuture future = bootstrap.connect("192.168.99.100", 30279).sync();
                channelList.add(future.channel());
                logger.info("登陆序列[{}]", i);
            } catch (Exception e) {
                e.printStackTrace();
            }
            i++;
        }

        while (true) {
            try {
                channelList.forEach(channel -> {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int mapId = mapIdList.get(random.nextInt(mapIdList.size()));
                    channel.writeAndFlush(PacketProtocol.valueOf(CM_ChangeMap.valueOf(mapId, mapId)));
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
