package game.base.fight.model.componet;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.fight.model.pvpunit.BaseUnit;

/**
 * 战斗对象的组件容器
 *
 * @author : ddv
 * @since : 2019/7/5 下午2:25
 */

public class UnitComponentContainer {

    private static Map<Class<? extends BaseUnit>, UnitClazzComponentInfo> unitClazzToMap = new HashMap<>();
    private Map<UnitComponentType, IUnitComponent> typeToComponent = new EnumMap<>(UnitComponentType.class);

    public UnitComponentContainer() {}

    public static void registerComponentClazz(Class<? extends BaseCreatureUnit> unitClass,
        Map<UnitComponentType, Class<? extends IUnitComponent>> map) {
        if (map == null) {
            map = new HashMap<>();
        }

        UnitClazzComponentInfo info = new UnitClazzComponentInfo(map);

        for (Class<? extends BaseUnit> aClass : unitClazzToMap.keySet()) {
            UnitClazzComponentInfo unitClazzComponentInfo = unitClazzToMap.get(aClass);
            if (unitClass.isAssignableFrom(aClass)) {
                unitClazzComponentInfo.merge(info.map);
            } else if (aClass.isAssignableFrom(unitClass)) {
                info.merge(unitClazzComponentInfo.map);
            }
        }
        unitClazzToMap.put(unitClass, info);
    }

    public void initialize(BaseUnit unit) {
        UnitClazzComponentInfo componentInfo = unitClazzToMap.get(unit.getClass());
        for (Class<? extends IUnitComponent> aClass : componentInfo.map.values()) {
            try {
                IUnitComponent iUnitComponent = aClass.newInstance();
                iUnitComponent.setOwner(unit);
                addComponent(iUnitComponent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addComponent(IUnitComponent iUnitComponent) {
        typeToComponent.put(iUnitComponent.getType(), iUnitComponent);
    }

    public <T extends IUnitComponent> T getComponent(UnitComponentType type) {
        return (T)typeToComponent.get(type);
    }

    public Map<UnitComponentType, IUnitComponent> getTypeToComponent() {
        return typeToComponent;
    }

    public static class UnitClazzComponentInfo {
        private Map<UnitComponentType, Class<? extends IUnitComponent>> map;

        public UnitClazzComponentInfo(Map<UnitComponentType, Class<? extends IUnitComponent>> map) {
            this.map = map;
        }

        public void merge(Map<UnitComponentType, Class<? extends IUnitComponent>> map) {
            map.forEach((type, aClass) -> {
                this.map.putIfAbsent(type, aClass);
            });
        }
    }
}
