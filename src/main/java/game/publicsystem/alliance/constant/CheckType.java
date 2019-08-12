package game.publicsystem.alliance.constant;

import client.MessageEnum;
import game.base.message.exception.RequestException;
import game.publicsystem.alliance.model.Alliance;
import game.publicsystem.alliance.model.AllianceParam;
import game.publicsystem.alliance.model.application.BaseAllianceApplication;
import spring.SpringContext;

/**
 * @author : ddv
 * @since : 2019/8/9 6:09 PM
 */

public enum CheckType {
    // 行会不存在
    Alliance_Empty {
        @Override
        public void check(AllianceParam param) {
            Alliance alliance = SpringContext.getAllianceService().getAlliance(param.getAllianceId());
            if (alliance == null) {
                RequestException.throwException(MessageEnum.Alliance_Empty);
            }
        }
    },
    // 行会解散
    Alliance_DISMISS {
        @Override
        public void check(AllianceParam param) {
            long allianceId = param.getAllianceId();
            Alliance alliance = SpringContext.getAllianceService().getAlliance(allianceId);
            if (alliance.isDismiss()) {
                RequestException.throwException(MessageEnum.Alliance_Dismiss);
            }
        }
    },
    // 操作者是否是会长
    Is_Operator_Chairman() {
        @Override
        public void check(AllianceParam param) {
            Alliance alliance = SpringContext.getAllianceService().getAlliance(param.getAllianceId());
            if (alliance.getChairmanId() != param.getPlayer().getPlayerId()) {
                RequestException.throwException(MessageEnum.Operator_Not_Chairman);
            }
        }
    },
    // 操作者是否是管理员
    Is_Operator_Admin() {
        @Override
        public void check(AllianceParam param) {
            Alliance alliance = SpringContext.getAllianceService().getAlliance(param.getAllianceId());
            if (!alliance.isMemberAdmin(param.getPlayer().getPlayerId())) {
                RequestException.throwException(MessageEnum.Operator_Not_Admin);
            }
        }
    },
    // 操作者是否是会员
    Is_Operator_Member() {
        @Override
        public void check(AllianceParam param) {
            Alliance alliance = SpringContext.getAllianceService().getAlliance(param.getAllianceId());
            if (!alliance.isMemberAdmin(param.getPlayer().getPlayerId())) {
                RequestException.throwException(MessageEnum.Operator_Not_Member);
            }
        }
    },
    // 申请表是否有效
    Is_Application_Legal() {
        @Override
        public void check(AllianceParam param) {
            Alliance alliance = SpringContext.getAllianceService().getAlliance(param.getAllianceId());
            BaseAllianceApplication application =
                alliance.getApplicationMap().get(param.getOperationType()).get(param.getApplicationId());
            if (application == null || application.isExpired()) {
                RequestException.throwException(MessageEnum.Alliance_Application_Expired);
            }
        }
    };

    public void check(AllianceParam param) {

    }
}
