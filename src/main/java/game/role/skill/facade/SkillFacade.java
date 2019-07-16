package game.role.skill.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import game.base.message.I18N;
import game.base.message.exception.RequestException;
import game.dispatch.anno.HandlerAnno;
import game.role.player.model.Player;
import game.role.skill.packet.*;
import net.utils.PacketUtil;
import spring.SpringContext;

/**
 * @author : ddv
 * @since : 2019/7/12 3:25 PM
 */
@Component
public class SkillFacade {

    public static final Logger logger = LoggerFactory.getLogger(SkillFacade.class);

    /**
     * 学习技能
     *
     * @param player
     * @param request
     */
    @HandlerAnno
    public void learnSkill(Player player, CM_LearnSkill request) {
        try {
            SpringContext.getSkillService().learnSkill(player, request.getSkillId());
        } catch (RequestException e) {
            PacketUtil.send(player, e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
            PacketUtil.send(player, I18N.SERVER_ERROR);
        }
    }

    /**
     * 客户端请求玩家技能信息
     *
     * @param player
     * @param request
     */
    @HandlerAnno
    public void skillList(Player player, CM_SkillList request) {
        try {
            SpringContext.getSkillService().getPlayerSkillList(player, true);
        } catch (RequestException e) {
            PacketUtil.send(player, e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
            PacketUtil.send(player, I18N.SERVER_ERROR);
        }
    }

    /**
     * 设置默认技能栏
     *
     * @param player
     * @param request
     */
    @HandlerAnno
    public void setDefaultSquare(Player player, CM_SetDefaultSquare request) {
        try {
            SpringContext.getSkillService().setDefaultSquare(player, request.getSquareIndex());
        } catch (RequestException e) {
            PacketUtil.send(player, e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
            PacketUtil.send(player, I18N.SERVER_ERROR);
        }
    }

    /**
     * 添加技能到技能栏
     *
     * @param player
     * @param request
     */
    @HandlerAnno
    public void addSkillToSquare(Player player, CM_AddSkillToSquare request) {
        try {
            SpringContext.getSkillService().addSkillToSquare(player, request.getSkillId(), request.getSquareIndex());
        } catch (RequestException e) {
            PacketUtil.send(player, e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
            PacketUtil.send(player, I18N.SERVER_ERROR);
        }
    }

    /**
     * 客户端请求玩家技能信息
     *
     * @param player
     * @param request
     */
    @HandlerAnno
    public void levelUpSkill(Player player, CM_LevelUpSkill request) {
        try {
            SpringContext.getSkillService().levelUpSkill(player, request.getSkillId());
        } catch (RequestException e) {
            PacketUtil.send(player, e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
            PacketUtil.send(player, I18N.SERVER_ERROR);
        }
    }

}
