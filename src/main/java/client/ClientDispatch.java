package client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.manager.ClazzManager;
import game.gm.packet.CM_GmCommand;
import game.world.fight.packet.CM_UseGroupPointSkill;
import io.netty.channel.ChannelHandlerContext;
import net.model.PacketProtocol;
import utils.ClassUtil;

/**
 * @author : ddv
 * @since : 2019/5/6 下午3:16
 */
public class ClientDispatch {

    private static final Logger logger = LoggerFactory.getLogger(ClientDispatch.class);

    private Object lastCommand = null;

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
            case "gm": {
                sendGmCommand(ctx, input, list);
                break;
            }

            case "+": {
                ctx.writeAndFlush(PacketProtocol.valueOf(lastCommand));
                break;
            }

            default:
                send(ctx, input, list);
                break;
        }
    }

    private void sendGmCommand(ChannelHandlerContext ctx, String input, List<String> list) {
        CM_GmCommand cm = new CM_GmCommand();
        List<String> stringList = list.stream().skip(1).collect(Collectors.toList());
        String content = "";
        for (int i = 0; i < stringList.size(); i++) {
            content += stringList.get(i) + " ";
        }
        cm.setMethodAndParams(content);
        ctx.writeAndFlush(PacketProtocol.valueOf(cm));
        logger.info("执行gm命令 : [{}]", cm.toString());
    }

    // 发包
    private void send(ChannelHandlerContext ctx, String input, List<String> list) {
        String action = list.get(0).toLowerCase();

        try {
            Class<?> aClass = ClazzManager.getClazz(action);

            if (aClass == CM_UseGroupPointSkill.class) {
                CM_UseGroupPointSkill cm = (CM_UseGroupPointSkill)aClass.newInstance();
                cm.setSkillId(Long.parseLong(list.get(1)));
                List<Long> targetIdList = new ArrayList<>();
                list.stream().skip(2).forEach(s -> {
                    targetIdList.add(Long.parseLong(s));
                });
                cm.setTargetIdList(targetIdList);
                ctx.writeAndFlush(PacketProtocol.valueOf(cm));
                return;
            }

            if (aClass == null) {
                logger.error("发包动作错误,id[{}]", action);
                return;
            }

            Object newInstance = aClass.newInstance();
            ClassUtil.insertDefaultFields(newInstance, list.stream().skip(1).collect(Collectors.toList()));
            ctx.writeAndFlush(PacketProtocol.valueOf(newInstance));
            lastCommand = newInstance;
            logger.info("执行命令 : [{}]", newInstance.getClass().getSimpleName());
        } catch (Exception e) {
            logger.error("派发器执行错误,input[{}]", input);
        }
    }

}
