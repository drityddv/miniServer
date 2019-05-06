package net.client;

import game.user.login.packet.CM_UserLogin;
import game.user.login.packet.CM_UserRegister;
import io.netty.channel.ChannelHandlerContext;
import net.model.PacketProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * @author : ddv
 * @since : 2019/5/6 下午3:16
 */

public class ClientDispatch {

	private static final Logger logger = LoggerFactory.getLogger(ClientDispatch.class);

	// 临时的客户端应用层派发器
	public void handler(ChannelHandlerContext ctx, Scanner scanner, int id) {
		switch (id) {
			case 1: {
				logger.info("请输入账户名和密码!");
				CM_UserLogin cm = new CM_UserLogin();
				cm.setAccountId(scanner.next());
				cm.setPassword(scanner.next());
				ctx.writeAndFlush(PacketProtocol.valueOf(cm));
				break;
			}
			case 2: {
				logger.info("请按顺序输入注册账号,注册密码,游戏昵称,身份证号,真实姓名!");
				CM_UserRegister cm = new CM_UserRegister();
				cm.setAccountId(scanner.next());
				cm.setPassword(scanner.next());
				cm.setUsername(scanner.next());
				cm.setIdCard(scanner.next());
				cm.setName(scanner.next());
				ctx.writeAndFlush(PacketProtocol.valueOf(cm));
				break;
			}
			case 3:
			case 4:
			default: {
				logger.info("派发器未找到相应操作命令,请确认指令[{}]", id);
			}
		}
	}
}
