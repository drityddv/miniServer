package game.world.fight.packet;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.anno.Action;
import game.base.fight.base.model.ActionResult;
import game.role.player.model.Player;
import game.world.fight.model.BattleParam;

/**
 * @author : ddv
 * @since : 2019/7/29 4:12 PM
 */

public class SM_BattleReport {
    private static final Logger logger = LoggerFactory.getLogger(SM_BattleReport.class);
    private Player player;
    private long skillId;
    private Map<Long, ActionResult> resultMap = new HashMap<>();

    public static SM_BattleReport valueOf(BattleParam battleParam) {
        SM_BattleReport sm = new SM_BattleReport();
        sm.player = battleParam.getPlayer();
        sm.skillId = battleParam.getBaseSkill().getSkillId();
        sm.resultMap = battleParam.getUnitResultMap();
        return sm;
    }

    @Action
    private void action() {
        logger.info("玩家[{}] 技能[{}]战报", player.getAccountId(), skillId);
        resultMap.values().forEach(actionResult -> {
            logger.info("伤害[{}] 敌方[{}] 状态[{}]", actionResult.getValue(), actionResult.getId(),
                actionResult.getRestrictStatus().name());
        });
    }

}
