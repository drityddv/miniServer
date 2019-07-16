package game.base.fight.model.skill.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.base.fight.model.componet.BaseUnitComponent;
import game.base.fight.model.componet.UnitComponentType;
import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.skill.model.BaseSkill;
import game.role.player.model.Player;
import game.role.skill.model.SkillList;
import game.role.skill.model.SkillSquare;

/**
 * pvp技能组件
 *
 * @author : ddv
 * @since : 2019/7/16 10:33 AM
 */

public class PVPSkillComponent extends BaseUnitComponent<BaseCreatureUnit> {

    private List<BaseSkill> skillList = new ArrayList<>();

    private Map<Long, BaseSkill> skillMap = new HashMap<>();

    private SkillSquare skillSquare;

    public void init(Player player, BaseCreatureUnit unit) {
        owner = unit;

        // 加载所有技能
        SkillList skillInfo = player.getSkillList();
        skillInfo.getSkills().forEach((skillId, skillEntry) -> {
            PlayerSkill playerSkill = PlayerSkill.valueOf(skillEntry);
            skillList.add(playerSkill);
            skillMap.put(skillId, playerSkill);
        });

        // 加载快捷施法栏
        SkillSquare square = skillInfo.getDefaultSquare();
        skillSquare = SkillSquare.valueOf(square);
    }

    @Override
    public UnitComponentType getType() {
        return UnitComponentType.SKILL;
    }

    public List<BaseSkill> getSkillList() {
        return skillList;
    }

    public void setSkillList(List<BaseSkill> skillList) {
        this.skillList = skillList;
    }

    public Map<Long, BaseSkill> getSkillMap() {
        return skillMap;
    }

    public SkillSquare getSkillSquare() {
        return skillSquare;
    }

    public void setSkillSquare(SkillSquare skillSquare) {
        this.skillSquare = skillSquare;
    }
}
