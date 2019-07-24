package game.world.fight.command.skill;

import java.util.List;

import client.MessageEnum;
import game.base.executor.command.impl.scene.base.AbstractSceneCommand;
import game.base.fight.utils.BattleUtil;
import game.base.message.exception.RequestException;
import game.base.skill.model.BaseSkill;
import game.role.player.model.Player;
import game.world.fight.model.BattleParam;

/**
 * @author : ddv
 * @since : 2019/7/23 10:39 AM
 */

public abstract class AbstractSkillCommand extends AbstractSceneCommand {

    protected Player player;
    protected BaseSkill baseSkill;
    protected BattleParam battleParam;

    public AbstractSkillCommand(int mapId, Player player, long skillId, List<Long> targetIds) {
        super(mapId);
        this.player = player;
        this.battleParam = BattleUtil.initParam(mapId, skillId, player.getPlayerId(), targetIds);
        this.baseSkill = BattleUtil.getUnitSkill(battleParam.getCaster(), skillId);
        if (!isSkillLegality()) {
            RequestException.throwException(MessageEnum.SKILL_TYPE_ERROR);
        }
    }

    // 技能种类是否合格
    protected boolean isSkillLegality() {
        return true;
    }
}
