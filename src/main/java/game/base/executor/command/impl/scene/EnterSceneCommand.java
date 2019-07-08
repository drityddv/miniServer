package game.base.executor.command.impl.scene;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.executor.command.impl.scene.base.AbstractSceneCommand;
import game.role.player.model.Player;

/**
 * @author : ddv
 * @since : 2019/7/2 上午10:53
 */

public class EnterSceneCommand extends AbstractSceneCommand {
    private static final Logger logger = LoggerFactory.getLogger(EnterSceneCommand.class);
    /**
     * 切图玩家
     */
    private Player player;

    private int oldMapId;

    public EnterSceneCommand(int mapId, String accountId) {
        super(accountId, mapId);
    }

    @Override
    public void action() {

    }

    @Override
    public String getName() {
        return "EnterSceneCommand";
    }
}
