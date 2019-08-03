package game.user.task.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.user.task.model.TaskCondition;
import resource.constant.CsvSymbol;

/**
 * @author : ddv
 * @since : 2019/8/1 10:10 PM
 */

public enum TaskEventType {
    /**
     * 普通地图中击杀怪物 [副本不算]
     */
    Kill_Monster_NormalMap(1) {
        /**
         * monsterId:count 1&1;2&2
         */
        @Override
        public List<TaskCondition> parseParam(String param) {
            List<TaskCondition> result = new ArrayList<>();
            String[] split = param.split(CsvSymbol.Comma);
            for (String s : split) {
                TaskCondition condition = new TaskCondition();
                condition.setType(this);
                String[] split1 = s.split(CsvSymbol.AND);
                condition.addParam(Long.parseLong(split1[0]));
                condition.addParam(Integer.parseInt(split1[1]));
                result.add(condition);
            }
            return result;
        }

        @Override
        public int getProgress(List<Object> paramList) {
            return (Integer)paramList.get(1);
        }
    },
    /**
     * 穿戴装备
     */
    Equipment_Wear(2),
    /**
     * 强化装备
     */
    Equipment_Enhance(3),
    /**
     * 角色升级
     */
    Player_LevelUp(4) {
        @Override
        public List<TaskCondition> parseParam(String param) {
            // 3
            List<TaskCondition> result = new ArrayList<>();
            String[] split = param.split(CsvSymbol.Comma);
            for (String s : split) {
                TaskCondition condition = new TaskCondition();
                condition.setType(this);
                condition.addParam(Integer.parseInt(s));
                result.add(condition);
            }
            return result;
        }

        @Override
        public int getProgress(List<Object> paramList) {
            return (Integer)paramList.get(0);
        }
    },;

    private static Map<Integer, TaskEventType> ID_TO_TYPE = new HashMap<>();

    static {
        for (TaskEventType typeEnum : TaskEventType.values()) {
            ID_TO_TYPE.put(typeEnum.id, typeEnum);
        }
    }

    private int id;

    TaskEventType(int id) {
        this.id = id;
    }

    public static TaskEventType getById(int id) {
        return ID_TO_TYPE.get(id);
    }

    public List<TaskCondition> parseParam(String param) {
        return new ArrayList<>();
    }

    public int getProgress(List<Object> paramList) {
        return 0;
    }
}
