package game.base.fight.model.pvpunit;

import java.util.HashMap;
import java.util.Map;

import game.base.fight.model.attribute.PVPCreatureAttributeComponent;
import game.base.fight.model.buff.PVPBuffComponent;
import game.base.fight.model.componet.IUnitComponent;
import game.base.fight.model.componet.UnitComponentContainer;
import game.base.fight.model.componet.UnitComponentType;

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

    protected transient FighterAccount fighterAccount;
    /**
     * 名称
     */
    private String name;

    protected BaseCreatureUnit(long id, FighterAccount fighterAccount, String name) {
        super(id);
        this.fighterAccount = fighterAccount;
        this.name = name;
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

}
