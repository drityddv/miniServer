package client.handler.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.handler.IHandler;
import game.role.skill.model.SkillList;
import game.role.skill.packet.SM_SkillListVo;

/**
 * @author : ddv
 * @since : 2019/7/12 3:14 PM
 */

public class SkillHandler implements IHandler<SM_SkillListVo> {
    private static final Logger logger = LoggerFactory.getLogger("client");

    @Override
    public void handler(SM_SkillListVo skillListVo) {
        SkillList skillList = skillListVo.getSkillList();
        skillList.getSkills().forEach((skillId, skillEntry) -> {
            logger.info("技能[{} {}]", skillId, skillEntry.getLevel());
        });

        skillList.getSkillSquareMap().forEach((index, skillSquare) -> {
            logger.info("技能栏[{}]", index);
            skillSquare.getSquareSkills().forEach((skillId, skillEntry) -> {
                logger.info("技能[{} {}]", skillId, skillEntry.getLevel());
            });
        });
    }
}
