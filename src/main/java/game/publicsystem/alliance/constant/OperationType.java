package game.publicsystem.alliance.constant;

import java.util.*;

/**
 * @author : ddv
 * @since : 2019/8/3 3:51 PM
 */

public enum OperationType {
    // 加入申请
    Join_Alliance(1) {
        @Override
        public List<OperationType> getConflictTypes() {
            return Arrays.asList(Join_Alliance, Leave_Alliance);
        }
    },

    // 离开申请
    Leave_Alliance(2) {
        @Override
        public List<OperationType> getConflictTypes() {
            return Arrays.asList(Join_Alliance, Leave_Alliance);
        }
    };

    private static Map<Integer, OperationType> ID_TO_TYPE = new HashMap<>();

    static {
        for (OperationType operationType : OperationType.values()) {
            ID_TO_TYPE.put(operationType.id, operationType);
        }
    }

    private int id;

    OperationType() {}

    OperationType(int id) {
        this.id = id;
    }

    public static OperationType getById(int id) {
        return ID_TO_TYPE.get(id);
    }

    /**
     * 互斥的操作 eg 进入行会和离开行会由于bug 导致申请队列存在一条离队请求,但是玩家实际已经离开行会 玩家再提进入行会申请会被驳回 即实际存储队员的容器不做业务逻辑上的同步
     *
     * @return
     */
    public List<OperationType> getConflictTypes() {
        return new ArrayList<>();
    }
}
