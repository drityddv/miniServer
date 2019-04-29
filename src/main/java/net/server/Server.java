package net.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;


/**
 * @author ddv
 */
public class Server {
	private EventLoopGroup boss;
	private EventLoopGroup worker;
	ServerBootstrap serverBootstrap;

	public void init() {
		boss = new NioEventLoopGroup();
		worker = new NioEventLoopGroup();
		serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(boss, worker);
		serverBootstrap.channel(NioServerSocketChannel.class);
		serverBootstrap.childHandler(new ServerInitializer());
	}

	public void run() {
		try {
			ChannelFuture future = serverBootstrap.bind(8000).sync();
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			boss.shutdownGracefully();
			worker.shutdownGracefully();
		}
	}

	public void shutdown() {
		if (boss != null) {
			boss.shutdownGracefully();
		}

		if (worker != null) {
			worker.shutdownGracefully();
		}
	}

	public static void main(String[] args) {
		Server server = new Server();
		server.init();
		new Thread(() -> server.run()).start();

		System.out.println(1);
	}
}
