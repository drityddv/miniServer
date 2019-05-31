package net.client;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import middleware.manager.ClazzManager;
import net.model.PacketProtocol;
import utils.ClassUtil;

/**
 * @author : ddv
 * @since : 2019/5/6 下午3:16
 */
public class ClientDispatch {

    private static final Logger logger = LoggerFactory.getLogger(ClientDispatch.class);

    private static final String ACCOUNT_TIPS = "1: 账号创建[创建后请手动登陆]\n" + "2: 账号登陆\n" + "3: 账号登出";

    private static final String MAP_TIPS = "1: [进入地图]\n" + "2: [地图移动]\n" + "3: [触发传送]\n" + "4: [离开地图]";;

    // 临时的客户端应用层派发器
    // 使用格式为 CM_Class param... 空格分隔
    public void handler(ChannelHandlerContext ctx, String input) {
        if (input == null || "".equals(input)) {
            return;
        }

        String[] strings = input.split(" ");

        List<String> list = Arrays.asList(strings);

        String operation = list.get(0);

        switch (operation) {
            case "send": {
                send(ctx, input, list);
                break;
            }

            case "close": {
                break;
            }

            default:
                break;
        }
    }

    // 发包
    private void send(ChannelHandlerContext ctx, String input, List<String> list) {
        String classId = list.get(1);

        try {
            Class<?> aClass = ClazzManager.getClazz(Integer.valueOf(classId));

            if (aClass == null) {
                logger.error("发包序列号错误,id[{}]", classId);
                return;
            }

            Object newInstance = aClass.newInstance();
            ClassUtil.insertDefaultFields(newInstance, list.stream().skip(2).collect(Collectors.toList()));
            ctx.writeAndFlush(PacketProtocol.valueOf(newInstance));
            logger.info("执行命令,[{}]", newInstance.toString());
        } catch (Exception e) {
            logger.error("派发器执行错误,input[{}]", input);
            e.printStackTrace();
        }
    }

    private void defaultTips(int id) {
        System.out.println("派发器未找到相应操作命令,请确认指令" + id);
    }
}
