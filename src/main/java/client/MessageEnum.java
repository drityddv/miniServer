package client;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : ddv
 * @since : 2019/7/18 9:46 PM
 */

public enum MessageEnum {
    //
    SUCCESS(-1, "操作成功!"),

    ILLEGAL_SESSION(0, "用户操作未授权!"),

    SERVER_ERROR(1, "服务器错误!"),

    USER_EXIST(2, "用户名已经注册!"),

    USER_NOT_EXIST(3, "登录用户名不存在"),

    PASSWORD_ERROR(4, "登陆密码错误!"),

    TARGET_POSITION_ERROR(9, "地图移动坐标有误!"),

    PACK_SIZE_NOT_ENOUGH(11, "背包容量不足!"),

    RESOURCE_NOT_EXIST(12, "资源文件不存在!"),

    ITEM_NUM_NOT_ENOUGH(13, "道具数量不足!"),

    INDEX_ERROR(14, "操作下标有误!"),

    ENUM_NULL(15, "执行器为null"),

    EQUIP_WEAR_CONDITION_NOT_QUALIFIED(16, "穿戴条件不满足!"),

    SEX_ERROR(18, "创建角色性别有误!"),

    FORCED_OFFLINE(19, "服务器强制下线!"),

    MAP_ENTER_CONDITION_NOT_SATISFY(20, "进图要求不合格!"),

    SKILL_POINT_NOT_ENOUGH(21, "技能点不足!"),

    PLAYER_UNIT_NOT_EXIST(22, "地图无战斗角色!"),

    SKILL_TYPE_ERROR(23, "技能种类发包错误!"),

    DEAD(24, "您已死亡!"),

    RE_LIVE(25, "您已重生!"),

    SKILL_EMPTY(26, "技能为空!"),

    SKILL_MP_NOT_ENOUGH(27, "技能使用失败,蓝量不足!"),

    SKILL_IN_CD(28, "技能cd中!"),

    Instance_Occupy(29, "副本已经占用!"),

    Instance_Failed(30, "副本通关失败!"),

    Conflict_Application(31, "行会存在冲突的申请!"),

    INSTANCE_END(32, "副本已经结束,不能进入!"),

    MAP_MAX_PLAYER_NUM(33, "副本人数达到最大上限!"),

    RANK_INDEX_ERROR(34, "排行榜分页查看参数错误,实例参数:'rankTypeId:1,start:0,end:1'"),

    Alliance_Dismiss(35, "行会已经被解散!"),

    Alliance_Empty(36, "行会不存在!"),

    Operator_Not_Chairman(37, "操作人不是会长!"),

    Operator_Not_Admin(38, "操作人不是会员!"),

    Operator_Not_Member(39, "操作人不是行会成员"),

	Alliance_Application_Expired(40, "行会申请表过期!");

    private static Map<Integer, MessageEnum> ID_TO_ENUM = new HashMap<>();

    static {
        for (MessageEnum messageEnum : MessageEnum.values()) {
            ID_TO_ENUM.put(messageEnum.id, messageEnum);
        }
    }

    private int id;
    private String message;

    MessageEnum(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public static MessageEnum getById(int id) {
        return ID_TO_ENUM.get(id);
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
