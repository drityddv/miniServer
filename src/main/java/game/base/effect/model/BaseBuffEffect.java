package game.base.effect.model;

import java.util.List;
import java.util.Map;

import game.base.effect.resource.EffectResource;
import game.base.fight.model.buff.PVPBuffEffectComponent;
import game.base.fight.model.componet.UnitComponentType;
import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.map.handler.AbstractMapHandler;

/**
 * 持续类buff
 *
 * @author : ddv
 * @since : 2019/7/15 12:01 PM
 */

public abstract class BaseBuffEffect extends BaseEffect<BaseCreatureUnit> {
    // 周期次数
    protected int period;
    // buff拥有者,释放者
    protected BaseCreatureUnit caster;
    // 剩余执行次数
    protected int remainCount;

    public void init(BaseCreatureUnit caster, List<BaseCreatureUnit> targetList, EffectResource effectResource) {
        super.init(targetList, effectResource);
        this.period = effectResource.getPeriodTime();
        this.remainCount = period;
        this.caster = caster;
    }

    // buff效果 默认啥都不做
    public void active() {

    }

    // get and set
    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public BaseCreatureUnit getCaster() {
        return caster;
    }

    public void setCaster(BaseCreatureUnit caster) {
        this.caster = caster;
    }

    public int getRemainCount() {
        return remainCount;
    }

    public Map<Long, BaseBuffEffect> getMapBuffGroup() {
        return AbstractMapHandler.getAbstractMapHandler(caster.getMapId()).getBuffEffects(caster.getMapId());
    }

    // buff失效
    public void removeBuff() {
        targetList.forEach(creatureUnit -> {
            PVPBuffEffectComponent component =
                creatureUnit.getComponentContainer().getComponent(UnitComponentType.BUFF);
            component.removeBuff(this);
        });
    }

}
