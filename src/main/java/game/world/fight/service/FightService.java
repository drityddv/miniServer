package game.world.fight.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import game.base.executor.util.ExecutorUtils;
import game.map.model.Grid;
import game.role.player.model.Player;
import game.world.fight.command.log.LogUnitBattleInfoCommand;
import game.world.fight.command.skill.UseAoeSkillCommand;
import game.world.fight.command.skill.UseGroupPointSkillCommand;
import game.world.fight.command.skill.UseSelfSkillCommand;
import game.world.fight.command.skill.UseSinglePointSkillCommand;
import spring.SpringContext;

/**
 * 战斗对象的信息处理
 *
 * @author : ddv
 * @since : 2019/7/5 下午9:28
 */
@Component
public class FightService implements IFightService {

    private static final Logger logger = LoggerFactory.getLogger(FightService.class);

    @Override
    public void logUnitBattleInfo(Player player, long unitId) {
        ExecutorUtils.submit(LogUnitBattleInfoCommand.valueOf(player, unitId));
    }

    @Override
    public void useSinglePointSkill(Player player, long skillId, long targetId) {
        if (!isSkillLegality(player, skillId)) {
            return;
        }
        ExecutorUtils.submit(UseSinglePointSkillCommand.valueOf(player, skillId, targetId));
    }

    @Override
    public void useGroupPointSkill(Player player, long skillId, List<Long> targetIdList) {
        if (!isSkillLegality(player, skillId)) {
            return;
        }
        ExecutorUtils.submit(UseGroupPointSkillCommand.valueOf(player, skillId, targetIdList));
    }

    @Override
    public void useAoeSkill(Player player, long skillId, int centerX, int centerY, int centerType) {
        if (!isSkillLegality(player, skillId)) {
            return;
        }
        ExecutorUtils.submit(UseAoeSkillCommand.valueOf(player, skillId, Grid.valueOf(centerX, centerY), centerType));
    }

    @Override
    public void useSelfSkill(Player player, long skillId) {
        if (!isSkillLegality(player, skillId)) {
            return;
        }
        ExecutorUtils.submit(UseSelfSkillCommand.valueOf(player, skillId));
    }

    private boolean isSkillLegality(Player player, long skillId) {
        if (!SpringContext.getSkillService().hasLearnedSkill(player, skillId)) {
            logger.warn("玩家[{}]使用技能[{}]失败,账号线程检测玩家未学习该技能", player.getAccountId(), skillId);
            return false;
        }
        return true;
    }
}
