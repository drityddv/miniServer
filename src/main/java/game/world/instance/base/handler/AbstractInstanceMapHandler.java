package game.world.instance.base.handler;

import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.fight.model.pvpunit.MonsterUnit;
import game.base.fight.model.pvpunit.PlayerUnit;
import game.map.handler.AbstractMapHandler;
import game.world.instance.base.model.BaseInstanceMapScene;

/**
 * 副本处理器
 *
 * @author : ddv
 * @since : 2019/8/1 2:38 PM
 */

public abstract class AbstractInstanceMapHandler extends AbstractMapHandler {

    @Override
    public void handlerUnitDead(BaseCreatureUnit unit) {
        BaseInstanceMapScene mapScene = (BaseInstanceMapScene)getMapScene(unit.getMapId(), unit.getSceneId());
        if (unit instanceof MonsterUnit) {
            mapScene.monsterDeadCallBack();
        }

        if (unit instanceof PlayerUnit) {
            mapScene.playerDeadCallBack();
        }
    }

}
