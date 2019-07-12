package game.role.skill.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.base.consume.IConsume;
import game.base.skill.resource.SkillLevelResource;
import game.base.skill.resource.SkillResource;
import game.role.player.model.Player;
import game.role.skill.entity.SkillEnt;
import game.role.skill.model.SkillEntry;
import game.role.skill.model.SkillList;
import game.role.skill.packet.SM_SkillListVo;
import net.utils.PacketUtil;

/**
 * 玩家技能接口
 *
 * @author : ddv
 * @since : 2019/7/12 12:11 PM
 */
@Component
public class SkillService implements ISkillService {

    private static final Logger logger = LoggerFactory.getLogger(SkillService.class);

    @Autowired
    private SkillManager skillManager;

    @Override
    public SkillList getPlayerSkillList(Player player, boolean client) {
        SkillList skillList = getSkillList(player);
        if (client) {
            sendSkillInfo(player, skillList);
        }
        return skillList;
    }

    @Override
    public void learnSkill(Player player, long skillId) {
        SkillEnt ent = getPlayerSkillEnt(player);
        SkillList skillList = ent.getSkillList();
        if (skillList.isLearnedSkill(skillId)) {
            logger.warn("玩家[{}]已经学习过该技能[{}]", player.getAccountId(), skillId);
            return;
        }

        SkillResource skillResource = skillManager.getSkillResource(skillId);
        SkillLevelResource skillLevelResource =
            skillManager.getSkillLevelResource(skillResource.getInitLevelConfigId());

        List<IConsume> consumes = skillLevelResource.getConsumes();

        for (IConsume consume : consumes) {
            consume.verifyThrow(player);
        }

        for (IConsume consume : consumes) {
            consume.consume(player);
        }

        SkillEntry skillEntry = SkillEntry.valueOf(skillId, skillLevelResource.getLevel());
        skillList.addSkill(skillEntry);

        saveSkillEnt(ent);
    }

    @Override
    public void levelUpSkill(Player player, long skillId) {
        SkillEnt ent = getPlayerSkillEnt(player);
        SkillList skillList = ent.getSkillList();
        if (!skillList.isLearnedSkill(skillId)) {
            logger.warn("玩家[{}]未掌握该技能[{}]", player.getAccountId(), skillId);
            return;
        }

        SkillLevelResource levelResource = skillManager.getSkillLevelResource(skillId);
        if (levelResource.getNextLevelConfigId() == 0) {
            logger.warn("技能[{}]满级,无法在升级了", skillId);
            return;
        }

        SkillLevelResource nextLevelResource = skillManager.getSkillLevelResource(levelResource.getNextLevelConfigId());
        List<IConsume> consumes = nextLevelResource.getConsumes();

        for (IConsume consume : consumes) {
            consume.verifyThrow(player);
        }

        for (IConsume consume : consumes) {
            consume.consume(player);
        }

        skillList.levelUp(skillId, nextLevelResource.getLevel());
        saveSkillEnt(ent);
        sendSkillInfo(player, skillList);
    }

    private SkillEnt getPlayerSkillEnt(Player player) {
        return skillManager.loadOrCreate(player);
    }

    private SkillList getSkillList(Player player) {
        return getPlayerSkillEnt(player).getSkillList();
    }

    private void saveSkillEnt(SkillEnt ent) {
        skillManager.save(ent);
    }

    private void sendSkillInfo(Player player, SkillList skillList) {
        PacketUtil.send(player, SM_SkillListVo.valueOf(skillList));
    }
}
