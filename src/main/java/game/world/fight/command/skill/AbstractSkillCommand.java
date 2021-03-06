package game.world.fight.command.skill;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.MessageEnum;
import game.base.executor.command.impl.scene.base.AbstractSceneCommand;
import game.base.fight.model.pvpunit.PlayerUnit;
import game.base.fight.model.skill.action.handler.BaseActionHandler;
import game.base.fight.utils.BattleUtil;
import game.base.message.exception.RequestException;
import game.base.skill.model.BaseSkill;
import game.role.player.model.Player;
import game.world.fight.model.BattleParam;
import net.utils.PacketUtil;

/**
 * @author : ddv
 * @since : 2019/7/23 10:39 AM
 */

public abstract class AbstractSkillCommand extends AbstractSceneCommand {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractSkillCommand.class);

    protected Player player;
    protected long skillId;
    protected BattleParam battleParam;

    public AbstractSkillCommand(Player player, long skillId) {
        super(player.getCurrentMapId(), player.getCurrentSceneId());
        this.player = player;
        this.battleParam = BattleUtil.loadParam(mapId, sceneId, skillId, player.getPlayerId());
        this.battleParam.setTargetId(player.getPlayerId());
        this.battleParam.getTargetIdList().add(battleParam.getCaster().getId());
    }

    public AbstractSkillCommand(Player player, long skillId, Long targetId) {
        super(player.getCurrentMapId(), player.getCurrentSceneId());
        this.player = player;
        this.battleParam = BattleUtil.loadParam(mapId, sceneId, skillId, player.getPlayerId());
        this.battleParam.setTargetId(targetId);
        this.battleParam.getTargetIdList().add(targetId);
    }

    public AbstractSkillCommand(Player player, long skillId, List<Long> targetIdList) {
        super(player.getCurrentMapId(), player.getCurrentSceneId());
        this.player = player;
        this.battleParam = BattleUtil.loadParam(mapId, sceneId, skillId, player.getPlayerId());
        this.battleParam.setTargetIdList(targetIdList);
    }

    // 技能种类是否符合
    protected boolean isSkillLegality() {
        return true;
    }

    protected void checkSkillLegality() {
        if (!isSkillLegality()) {
            RequestException.throwException(MessageEnum.SKILL_TYPE_ERROR);
        }

        PlayerUnit caster = battleParam.getCaster();
        BaseSkill baseSkill = battleParam.getBaseSkill();

        if (baseSkill == null) {
            logger.warn("玩家[{}] 使用技能失败,技能为空", caster.getFighterAccount().getAccountId());
            RequestException.throwException(MessageEnum.SKILL_EMPTY);
        }

        if (!caster.isEnoughMp(baseSkill.getSkillMpConsume())) {
            logger.warn("玩家[{}] 使用技能失败,魔法值不足 需要[{}] 实际拥有[{}]", caster.getFighterAccount().getAccountId(),
                baseSkill.getSkillMpConsume(), caster.getCurrentMp());
            RequestException.throwException(MessageEnum.SKILL_MP_NOT_ENOUGH);
        }

        if (baseSkill.isCd()) {
            logger.warn("玩家[{}] 使用技能失败,技能cd中", caster.getFighterAccount().getAccountId());
            RequestException.throwException(MessageEnum.SKILL_IN_CD);
        }
    }

    @Override
    public void action() {
        try {
            checkSkillLegality();
            BaseActionHandler actionHandler = battleParam.getActionHandler();
            actionHandler.action(battleParam);
        } catch (RequestException e) {
            PacketUtil.send(player, e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
