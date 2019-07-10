package client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import net.codec.PacketDecoder;
import net.codec.PacketEncoder;

/**
 * @author : ddv
 * @date : 2019/3/3 下午7:50
 */

public class ClientInitializer extends ChannelInitializer<SocketChannel> {

    private String username;

    public static ClientInitializer valueOf(String username) {
        ClientInitializer clientInitializer = new ClientInitializer();
        clientInitializer.username = username;
        return clientInitializer;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new PacketDecoder());
        pipeline.addLast(new PacketEncoder());
        pipeline.addLast(ClientHandler.valueOf(username));
    }

}
