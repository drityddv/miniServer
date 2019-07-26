package game.world.fight.model;

import java.util.ArrayList;
import java.util.List;

import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.fight.model.pvpunit.PlayerUnit;
import game.base.fight.model.skill.action.handler.BaseActionHandler;
import game.base.skill.constant.SkillTypeEnum;
import game.base.skill.model.BaseSkill;
import game.map.area.CenterTypeEnum;
import game.map.base.AbstractMovableScene;
import game.map.handler.AbstractMapHandler;
import game.map.model.Grid;
import game.map.visible.BaseAttackAbleMapObject;

/**
 * 一次战斗所需对象集合
 *
 * @author : ddv
 * @since : 2019/7/17 12:28 PM
 */

public class BattleParam {
    // 地图参数
    private AbstractMapHandler mapHandler;
    private BaseActionHandler actionHandler;
    private AbstractMovableScene mapScene;

    // 单元参数
    private PlayerUnit caster;
    private BaseSkill baseSkill;
    private BaseCreatureUnit targetUnit;
    private List<BaseCreatureUnit> targetUnits;

    // 其他参数
    private Grid center;
    private CenterTypeEnum centerTypeEnum;
    private long targetId;
    private List<Long> targetIdList;

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

    public List<BaseCreatureUnit> getTargets() {
        return targetUnits;
    }

    public CenterTypeEnum getCenterTypeEnum() {
        return centerTypeEnum;
    }

    public void setCenterTypeEnum(CenterTypeEnum centerTypeEnum) {
        this.centerTypeEnum = centerTypeEnum;
    }

    public AbstractMovableScene getMapScene() {

        return mapScene;
    }

    public void setMapScene(AbstractMovableScene mapScene) {
        this.mapScene = mapScene;
    }

    public long getTargetId() {
        return targetId;
    }

    public void setTargetId(long targetId) {
        this.targetId = targetId;
    }

    public List<BaseCreatureUnit> getTargetUnits() {
        return targetUnits;
    }

    public void setTargetUnits(List<BaseCreatureUnit> targetUnits) {
        this.targetUnits = targetUnits;
    }

    public Grid getCenter() {
        return center;
    }

    public void setCenter(Grid center) {
        this.center = center;
    }

    public List<Long> getTargetIdList() {
        return targetIdList;
    }

    public void setTargetIdList(List<Long> targetIdList) {
        this.targetIdList = targetIdList;
    }

    public BaseSkill getBaseSkill() {
        return baseSkill;
    }

    public void setBaseSkill(BaseSkill baseSkill) {
        this.baseSkill = baseSkill;
    }

    public SkillTypeEnum getSkillType() {
        return baseSkill.getSkillLevelResource().getSkillTypeEnum();
    }

    public void loadSingleTarget() {
        targetUnit = mapScene.getMapObject(targetId).getFighterAccount().getCreatureUnit();
    }

    public void loadGroupTargets() {
        targetUnits = new ArrayList<>();
        mapScene.getMapObjects(targetIdList).forEach(mapObject -> {
            BaseAttackAbleMapObject attackAbleMapObject = (BaseAttackAbleMapObject)mapObject;
            targetUnits.add(attackAbleMapObject.getFighterAccount().getCreatureUnit());
        });
    }

}
