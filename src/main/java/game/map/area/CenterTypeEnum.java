package game.map.area;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.base.skill.constant.SkillParamConstant;
import game.base.skill.model.BaseSkill;
import game.map.model.Grid;
import game.world.fight.model.BattleParam;

/**
 * 中心点类型
 *
 * @author : ddv
 * @since : 2019/7/26 2:14 PM
 */

public enum CenterTypeEnum {
    /**
     * 指定中心点
     */
    Target_Grid(1) {
        @Override
        public void loadTargets(BattleParam battleParam, Grid center) {
            center = battleParam.getCenter();
            super.loadTargets(battleParam, center);
        }
    },
    /**
     * 自身为中心点
     */
    Self(2) {
        @Override
        public void loadTargets(BattleParam battleParam, Grid center) {
            center = battleParam.getCaster().getMapObject().getCurrentGrid();
            super.loadTargets(battleParam, center);
        }
    },
    /**
     * 默认 根据id查找
     */
    Default(3) {
        @Override
        public void loadTargets(BattleParam battleParam, Grid center) {
            List<Long> targetIdList = battleParam.getTargetIdList();
            battleParam.setTargetUnits(battleParam.getMapScene().getMapUnits(targetIdList));
        }
    },;

    private static Map<Integer, CenterTypeEnum> ID_TO_TYPE = new HashMap<>();
    private static Map<String, CenterTypeEnum> NAME_TO_TYPE = new HashMap<>();

    static {
        for (CenterTypeEnum anEnum : CenterTypeEnum.values()) {
            ID_TO_TYPE.put(anEnum.id, anEnum);
            NAME_TO_TYPE.put(anEnum.name(), anEnum);
        }
    }

    private int id;

    CenterTypeEnum(int id) {
        this.id = id;
    }

    public static CenterTypeEnum getById(Integer id) {
        CenterTypeEnum centerTypeEnum = ID_TO_TYPE.get(id);
        if (centerTypeEnum == null) {
            return Default;
        }
        return centerTypeEnum;
    }

    public static CenterTypeEnum getByName(String name) {
        return NAME_TO_TYPE.get(name);
    }

    public void loadTargets(BattleParam battleParam, Grid center) {
        BaseSkill baseSkill = battleParam.getBaseSkill();
        BaseAreaProcess baseAreaProcess = baseSkill.getSkillLevelResource().getAreaTypeEnum().getProcess();
        AreaProcessParam param = AreaProcessParam.valueOf(center,
            Integer.parseInt(baseSkill.getSkillLevelResource().getAreaTypeParam().get(SkillParamConstant.RADIUS)));
        battleParam.setTargetUnits(baseAreaProcess.calculate(param, battleParam.getMapScene()));
    }

    public int getId() {
        return id;
    }
}
