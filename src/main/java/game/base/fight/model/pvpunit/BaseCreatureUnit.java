package game.base.fight.model.pvpunit;

import java.util.HashMap;
import java.util.Map;

import game.base.fight.base.model.attack.BaseActionEntry;
import game.base.fight.model.attribute.PVPCreatureAttributeComponent;
import game.base.fight.model.buff.PVPBuffComponent;
import game.base.fight.model.componet.IUnitComponent;
import game.base.fight.model.componet.UnitComponentContainer;
import game.base.fight.model.componet.UnitComponentType;
import game.base.fight.utils.BattleUtil;
import game.base.game.attribute.AttributeType;
import game.map.handler.AbstractMapHandler;
import game.map.visible.AbstractMapObject;
import game.world.base.constant.Map_Constant;
import scheduler.job.model.JobEntry;

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
     * 名称
     */
    private String name;

    protected BaseCreatureUnit(long id, FighterAccount fighterAccount, String name) {
        super(id);
        this.fighterAccount = fighterAccount;
        this.name = name;
    }

    @Override
    protected void handlerDead(BaseActionEntry attackEntry) {
        attackUnit = attackEntry.getCaster();
        JobEntry.newMapObjectReliveJob(Map_Constant.Relive_Delay, id, mapId).schedule();
    }

    @Override
    public void relive() {
        initBaseAttribute();
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
        PVPCreatureAttributeComponent unitAttributeComponent = BattleUtil.getUnitAttrComponent(this);
        unitAttributeComponent.containerRecompute();
        currentHp = maxHp = unitAttributeComponent.getFinalAttributes().get(AttributeType.MAX_HP).getValue();
        currentMp = maxMp = unitAttributeComponent.getFinalAttributes().get(AttributeType.MAX_MP).getValue();
    }
}
