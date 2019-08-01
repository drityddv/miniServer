package game.world.base.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.base.executor.util.ExecutorUtils;
import game.base.message.exception.RequestException;
import game.base.message.packet.SM_Message;
import game.map.handler.AbstractMapHandler;
import game.map.handler.IMovableMapHandler;
import game.map.model.Grid;
import game.role.player.model.Player;
import game.world.base.command.scene.*;
import game.world.base.constant.Map_Constant;
import game.world.base.packet.CM_ShowAround;
import game.world.base.resource.MiniMapResource;
import net.utils.PacketUtil;

/**
 * 地图所有对外服务接口
 *
 * @author : ddv
 * @since : 2019/7/2 下午4:40
 */
@Component
public class WorldService implements IWorldService {

    private static final Logger logger = LoggerFactory.getLogger(WorldService.class);

    @Autowired
    private WorldManager worldManager;

    @Override
    public void gatewayChangeMap(Player player, int newMapId, long newSceneId, boolean clientRequest) {
        int currentMapId = player.getCurrentMapId();
        try {
            if (player.isChangingMap()) {
                logger.warn("玩家[{}]正在切图", player.getAccountId());
                return;
            }

            // 允许玩家从无地图状态切过来 如果有旧图就先去旧地图线程离开再提交进图命令
            if (currentMapId != Map_Constant.EMPTY_MAP) {
                gatewayLeaveMap(player, newMapId, newSceneId, clientRequest);
            } else {
                ExecutorUtils.submit(EnterMapCommand.valueOf(player, newMapId, newSceneId));
            }

        } catch (RequestException e) {
            // 如果是客户端调用 打印日志
            if (clientRequest) {
                logger.warn("玩家[{}]请求从地图[{}]进入地图[{}]失败,原因[{}]", player.getAccountId(), currentMapId, newMapId,
                    e.getErrorCode());
                PacketUtil.send(player, SM_Message.valueOf(e.getErrorCode()));
            }
        } catch (Exception e) {
            player.setChangingMap(false);
            e.printStackTrace();
        }

    }

    @Override
    public void gatewayLeaveMap(Player player, int mapId, long sceneId, boolean clientRequest) {
        ExecutorUtils.submit(LeaveMapCommand.valueOf(player, mapId, sceneId));
    }

    @Override
    public void leaveMap(Player player, int mapId, boolean clientRequest) {
        MiniMapResource mapResource = worldManager.getMapResource(mapId);
        AbstractMapHandler handler = AbstractMapHandler.getHandler(mapResource.getGroupId());
        handler.leaveMap(player);
    }

    @Override
    public void move(Player player, Grid targetPosition) {
        int currentMapId = player.getCurrentMapId();
        MiniMapResource mapResource = worldManager.getMapResource(currentMapId);
        AbstractMapHandler handler = AbstractMapHandler.getHandler(mapResource.getGroupId());

        // 有些地图是不能移动的
        if (handler instanceof IMovableMapHandler) {
            ExecutorUtils.submit(MoveCommand.valueOf(player, targetPosition, handler));
        } else {
            logger.warn("玩家[{}]地图[{}]移动失败,该地图不能手动移动", player.getAccountId(), currentMapId);
        }
    }

    @Override
    public void showAround(Player player, CM_ShowAround request) {
        ExecutorUtils.submit(ShowAroundCommand.valueOf(player));
    }

    // 测试用 gm和facade都会走这里
    @Override
    public void logMap(Player player, int mapId, long sceneId) {
        ExecutorUtils.submit(LogMapCommand.valueOf(player, mapId, sceneId));
    }

}
