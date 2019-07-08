package game.common;

/**
 * @author : ddv
 * @since : 2019/5/6 下午2:07
 */

public interface I18N {

    // 操作成功 开发阶段用来显示效果
    int OPERATION_SUCCESS = -1;

    // 操作session未授权
    int ILLEGAL_SESSION = 0;

    // 服务器异常
    int SERVER_ERROR = 1;

    // 创建账号accountId已经存在
    int USER_EXIST = 2;

    // 登陆账号不存在
    int USER_NOT_EXIST = 3;

    // 密码错误
    int PASSWORD_ERROR = 4;

    // 地图不存在
    int MAP_NOT_EXIST = 5;

    // 操作的单元不存在与地图中
    int MAP_CREATURE_NOT_EXIST = 6;

    // 触发事件距离过远
    int DISTANCE_TOO_FAR = 7;

    // 地图移动坐标有误
    int TARGET_POSITION_ERROR = 9;

    // 背包已满
    int PACK_FULL = 10;

    // 添加道具失败 背包容量不足
    int PACK_SIZE_NOT_ENOUGH = 11;

    // 资源文件不存在
    int RESOURCE_NOT_EXIST = 12;

    // 道具数量不足
    int ITEM_NUM_NOT_ENOUGH = 13;

    // 操作容器的下标非法 防止非法发包
    int INDEX_ERROR = 14;

    // 执行器为空
    int ENUM_NULL = 15;

    /**
     * 穿戴装备条件不满足
     */
    int EQUIP_WEAR_CONDITION_NOT_QUALIFIED = 16;
    /**
     * 异地登陆下线
     */
    int OTHER_LOGIN = 17;
    /**
     * 创建角色性别错误
     */
    int SEX_ERROR = 18;

    /**
     * 服务器强制下线
     */
    int FORCED_OFFLINE = 19;
}
