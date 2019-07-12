package game.role.skill.service;

import game.role.player.model.Player;
import game.role.skill.model.SkillList;

/**
 * 玩家技能接口
 *
 * @author : ddv
 * @since : 2019/7/12 11:04 AM
 */

public interface ISkillService {

    /**
     * 获取玩家技能信息
     *
     * @param player
     * @param client
     * @return
     */
    SkillList getPlayerSkillList(Player player, boolean client);

    /**
     * 学习技能
     *
     * @param player
     * @param skillId
     */
    void learnSkill(Player player, long skillId);

    /**
     * 升级技能
     *
     * @param player
     * @param skillId
     */
    void levelUpSkill(Player player, long skillId);

}
