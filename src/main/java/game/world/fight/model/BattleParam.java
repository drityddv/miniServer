package game.world.fight.model;

import java.util.List;

import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.fight.model.pvpunit.PlayerUnit;
import game.base.fight.model.skill.action.handler.BaseActionHandler;
import game.map.handler.AbstractMapHandler;

/**
 * 一次战斗所需对象集合
 *
 * @author : ddv
 * @since : 2019/7/17 12:28 PM
 */

public class BattleParam {
    AbstractMapHandler mapHandler;
    BaseActionHandler actionHandler;
    PlayerUnit caster;
    BaseCreatureUnit targetUnit;
    List<BaseCreatureUnit> targetUnits;

    public AbstractMapHandler getMapHandler() {
        return mapHandler;
    }

    public void setMapHandler(AbstractMapHandler mapHandler) {
        this.mapHandler = mapHandler;
    }

    public BaseActionHandler getActionHandler() {
        return actionHandler;
    }

    public void setActionHandler(BaseActionHandler actionHandler) {
        this.actionHandler = actionHandler;
    }

    public PlayerUnit getCaster() {
        return caster;
    }

    public void setCaster(PlayerUnit caster) {
        this.caster = caster;
    }

    public BaseCreatureUnit getTargetUnit() {
        return targetUnit;
    }

    public void setTargetUnit(BaseCreatureUnit targetUnit) {
        this.targetUnit = targetUnit;
    }

    public List<BaseCreatureUnit> getTargetUnits() {
        return targetUnits;
    }

    public void setTargetUnits(List<BaseCreatureUnit> targetUnits) {
        this.targetUnits = targetUnits;
    }
}
