package game.base.fight.model.pvpunit;

import java.util.HashMap;
import java.util.Map;

import game.base.effect.model.constant.RestrictStatusEnum;
import game.base.fight.base.model.BaseActionEntry;
import game.base.fight.model.attribute.PVPCreatureAttributeComponent;
import game.base.fight.model.buff.PVPBuffComponent;
import game.base.fight.model.componet.IUnitComponent;
import game.base.fight.model.componet.UnitComponentContainer;
import game.base.fight.model.componet.UnitComponentType;
import game.base.fight.model.skill.model.PVPSkillComponent;
import game.base.game.attribute.Attribute;
import game.base.game.attribute.AttributeType;
import game.map.handler.AbstractMapHandler;
import game.map.visible.AbstractMapObject;
import game.world.base.constant.Map_Constant;
import game.world.base.resource.CreatureResource;
import quartz.job.model.JobEntry;

/**
 * 战斗成员对应的基础单元
 *
 * @author : ddv
 * @since : 2019/7/5 下午2:14
 */

public abstract class BaseCreatureUnit extends BaseUnit {

    static {
        Map<UnitComponentType, Class<? extends IUnitComponent>> map = new HashMap<>();
        map.put(UnitComponentType.ATTRIBUTE, PVPCreatureAttributeComponent.class);
        map.put(UnitComponentType.BUFF, PVPBuffComponent.class);
        UnitComponentContainer.registerComponentClazz(BaseCreatureUnit.class, map);
    }

    /**
     * 杀死我的单位
     */
    protected BaseCreatureUnit attackUnit;

    protected FighterAccount fighterAccount;

    /**
     * 是否已经处理死亡事件
     */
    protected boolean handleDead = false;
    /**
     * 名称
     */
    private String name;

    protected BaseCreatureUnit(long id, FighterAccount fighterAccount, String name) {
        super(id);
        this.fighterAccount = fighterAccount;
        this.name = name;
    }

    /**
     * 孵化逻辑
     *
     * @param creatureResource
     * @param fighterAccount
     * @param id
     * @return
     */
    public BaseCreatureUnit hatch(CreatureResource creatureResource, FighterAccount fighterAccount, long id, int mapId,
        long sceneId) {
        return null;
    }

    @Override
    public void handlerDead(BaseActionEntry attackEntry) {
        if (!handleDead) {
            super.handlerDead(attackEntry);
            attackUnit = attackEntry.getCaster();
            JobEntry.newMapObjectReliveJob(Map_Constant.Relive_Delay, id, mapId, sceneId).schedule();
            handleDead = true;
        }

    }

    public void handlerStatus(BaseActionEntry attackEntry) {
        attackEntry.getActionResult().setRestrictStatus(statusEnum);
    }

    @Override
    public void relive() {
        initBaseAttribute();
        handleDead = false;
        statusEnum = RestrictStatusEnum.ALIVE;
        dead = false;
        AbstractMapObject mapObject = fighterAccount.getMapObject();
        AbstractMapHandler.getAbstractMapHandler(mapId).broadcast(mapId, mapObject.getCurrentGrid());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FighterAccount getFighterAccount() {
        return fighterAccount;
    }

    public void setFighterAccount(FighterAccount fighterAccount) {
        this.fighterAccount = fighterAccount;
    }

    protected void initBaseAttribute() {
        PVPCreatureAttributeComponent unitAttributeComponent = getAttributeComponent();
        unitAttributeComponent.containerRecompute();
        currentHp = maxHp = unitAttributeComponent.getFinalAttributes().get(AttributeType.MAX_HP).getValue();
        currentMp = maxMp = unitAttributeComponent.getFinalAttributes().get(AttributeType.MAX_MP).getValue();
    }

    public PVPBuffComponent getBuffComponent() {
        return componentContainer.getComponent(UnitComponentType.BUFF);
    }

    public PVPCreatureAttributeComponent getAttributeComponent() {
        return componentContainer.getComponent(UnitComponentType.ATTRIBUTE);
    }

    public long getUnitAttributeValue(AttributeType type) {
        Attribute attribute = getAttributeComponent().getFinalAttributes().get(type);
        return attribute == null ? 0 : attribute.getValue();
    }

    public PVPSkillComponent getSkillComponent() {
        return componentContainer.getComponent(UnitComponentType.SKILL);
    }
}
