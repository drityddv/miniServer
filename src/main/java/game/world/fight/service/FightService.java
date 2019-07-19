package game.world.fight.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import game.base.executor.util.ExecutorUtils;
import game.base.fight.model.pvpunit.FighterAccount;
import game.role.player.model.Player;
import game.world.fight.command.LogUnitBattleInfoCommand;
import game.world.fight.command.UseGroupPointSkillCommand;
import game.world.fight.command.UseSinglePointSkillCommand;

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
    public FighterAccount initForPlayer(Player player) {
        FighterAccount fighterAccount = FighterAccount.valueOf(player, player.getCurrentMapId());
        return fighterAccount;
    }

    @Override
    public void logUnitBattleInfo(Player player, long unitId) {
        ExecutorUtils.submit(LogUnitBattleInfoCommand.valueOf(player, unitId));
    }

    @Override
    public void useSinglePointSkill(Player player, long skillId, long targetId) {
        ExecutorUtils.submit(UseSinglePointSkillCommand.valueOf(player, skillId, targetId));
    }

    @Override
    public void useGroupPointSkill(Player player, long skillId, List<Long> targetIdList) {
        ExecutorUtils.submit(UseGroupPointSkillCommand.valueOf(player, skillId, targetIdList));
    }
}
