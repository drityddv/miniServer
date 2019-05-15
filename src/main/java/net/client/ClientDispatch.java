package net.client;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.gm.packet.CM_GmCommand;
import game.scene.map.packet.CM_EnterMap;
import game.scene.map.packet.CM_LeaveMap;
import game.scene.map.packet.CM_MoveMap;
import game.scene.map.packet.CM_TransferMap;
import game.user.login.packet.CM_UserLogin;
import game.user.login.packet.CM_UserLogout;
import game.user.login.packet.CM_UserRegister;
import io.netty.channel.ChannelHandlerContext;
import net.model.PacketProtocol;

/**
 * @author : ddv
 * @since : 2019/5/6 下午3:16
 */
public class ClientDispatch {

    private static final Logger logger = LoggerFactory.getLogger(ClientDispatch.class);

    private static final String ACCOUNT_TIPS = "1: 账号创建[创建后请手动登陆]\n" + "2: 账号登陆\n" + "3: 账号登出";

    private static final String MAP_TIPS = "1: [进入地图]\n" + "2: [地图移动]\n" + "3: [触发传送]\n" + "4: [离开地图]";;

    private static StringBuffer stringBuffer = new StringBuffer();

    // 临时的客户端应用层派发器
    public void handler(ChannelHandlerContext ctx, Scanner scanner, int id) {
        switch (id) {
            case 1: {
                System.out.println(ACCOUNT_TIPS);
                int nextId = scanner.nextInt();

                switch (nextId) {
                    case 1: {
                        System.out.println("请换行输入注册账号,注册密码,游戏昵称,身份证号,真实姓名!");
                        CM_UserRegister cm = new CM_UserRegister();
                        cm.setAccountId(scanner.next());
                        cm.setPassword(scanner.next());
                        cm.setUsername(scanner.next());
                        cm.setIdCard(scanner.next());
                        cm.setName(scanner.next());
                        ctx.writeAndFlush(PacketProtocol.valueOf(cm));
                        break;
                    }

                    case 2: {
                        System.out.println("请换行输入账号,密码!");
                        CM_UserLogin cm = new CM_UserLogin();
                        cm.setAccountId(scanner.next());
                        cm.setPassword(scanner.next());
                        ctx.writeAndFlush(PacketProtocol.valueOf(cm));
                        break;
                    }

                    case 3: {
                        CM_UserLogout cm = new CM_UserLogout();
                        ctx.writeAndFlush(PacketProtocol.valueOf(cm));
                        break;
                    }

                    default: {
                        defaultTips(nextId);
                        break;
                    }
                }

                return;
            }

            case 2: {
                System.out.println(MAP_TIPS);

                int nextId = scanner.nextInt();

                switch (nextId) {

                    case 1: {
                        System.out.println("请输入想要进去的地图Id!");
                        CM_EnterMap cm = new CM_EnterMap();
                        cm.setMapId(scanner.nextLong());
                        ctx.writeAndFlush(PacketProtocol.valueOf(cm));
                        break;
                    }

                    case 2: {
                        System.out.println("请输入地图Id,移动终点x,y坐标!");
                        CM_MoveMap cm = new CM_MoveMap();
                        cm.setMapId(scanner.nextLong());
                        cm.setTargetX(scanner.nextInt());
                        cm.setTargetY(scanner.nextInt());
                        ctx.writeAndFlush(PacketProtocol.valueOf(cm));
                        break;
                    }

                    case 3: {
                        System.out.println("请输入当前地图Id!");
                        CM_TransferMap cm = new CM_TransferMap();
                        cm.setMapId(scanner.nextLong());
                        ctx.writeAndFlush(PacketProtocol.valueOf(cm));
                        break;
                    }

                    case 4: {
                        System.out.println("请输入地图Id!");
                        CM_LeaveMap cm = new CM_LeaveMap();
                        cm.setMapId(scanner.nextLong());
                        ctx.writeAndFlush(PacketProtocol.valueOf(cm));
                        break;
                    }

                    default: {
                        defaultTips(nextId);
                        break;
                    }

                }
                return;
            }

            case 3: {
                stringBuffer.setLength(0);
                // nextLine不阻塞 这里先暂时用参数个数占位
                System.out.println("请依次输入指令命令与参数个数!");
                CM_GmCommand cm = new CM_GmCommand();
                stringBuffer.append(scanner.next() + " ");
                int paramNum = scanner.nextInt();
                for (int i = 0; i < paramNum; i++) {
                    stringBuffer.append(scanner.next() + " ");
                }
                cm.setMethodAndParams(stringBuffer.toString());
                ctx.writeAndFlush(PacketProtocol.valueOf(cm));
                break;
            }

            default: {
                defaultTips(id);
                break;
            }
        }
    }

    private void defaultTips(int id) {
        System.out.println("派发器未找到相应操作命令,请确认指令" + id);
    }
}
