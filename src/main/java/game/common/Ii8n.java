package game.common;

/**
 * @author : ddv
 * @since : 2019/5/6 下午2:07
 */

public interface Ii8n {

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

}
