package game.world.base.command.scene;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.executor.command.impl.scene.base.AbstractSceneCommand;
import game.base.message.exception.RequestException;
import game.base.message.packet.SM_Message;
import game.map.base.AbstractMovableScene;
import game.map.handler.AbstractMapHandler;
import game.role.player.model.Player;
import game.world.base.resource.MiniMapResource;
import game.world.base.service.WorldManager;
import net.utils.PacketUtil;

/**
 * @author : ddv
 * @since : 2019/7/2 下午8:45
 */

public class EnterMapCommand extends AbstractSceneCommand {

    private static final Logger logger = LoggerFactory.getLogger(EnterMapCommand.class);

    private Player player;
    private int mapId;

    public EnterMapCommand(int mapId, long sceneId) {
        super(mapId, sceneId);
    }

    public static EnterMapCommand valueOf(Player player, int mapId, long sceneId) {
        EnterMapCommand command = new EnterMapCommand(mapId, sceneId);
        command.player = player;
        command.mapId = mapId;
        return command;
    }

    public static EnterMapCommand valueOfMainCity(Player player) {
        return valueOf(player, 4, 0);
    }

    @Override
    public void action() {
        AbstractMapHandler handler = null;
        try {
            // 具体切图逻辑
            MiniMapResource mapResource = WorldManager.getInstance().getMapResource(mapId);
            handler = AbstractMapHandler.getHandler(mapResource.getGroupId());

            // 检查进入条件
            handler.canEnterMapThrow(player, mapId, sceneId);
            AbstractMovableScene mapScene = handler.getMapScene(mapId, handler.decodeSceneId(player, sceneId));
            sceneId = mapScene.getSceneId();
            // 进入地图前的一些工作 检查,上锁等
            handler.enterMapPre(player);
            // 真正进入地图
            handler.realEnterMap(player, mapId, sceneId);
            // 进入地图后的一些工作
            handler.enterMapAfter(player, mapId, sceneId);
        } catch (RequestException e) {
            logger.info("玩家[{}]进图[{}]失败,自动回到主城", player.getAccountId(), mapId);
            player.setChangingMap(false);
            handler.handEnterMapFailed(player);
            PacketUtil.send(player, SM_Message.valueOf(e.getErrorCode()));
        } catch (Exception e) {
            player.setChangingMap(false);
            handler.handEnterMapFailed(player);
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "EnterMapCommand";
    }

}
