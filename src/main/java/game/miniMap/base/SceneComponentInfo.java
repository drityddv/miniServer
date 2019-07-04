package game.miniMap.base;

import java.util.HashMap;
import java.util.Map;

/**
 * 场景的组件容器
 *
 * @author : ddv
 * @since : 2019/7/3 下午2:06
 */

public class SceneComponentInfo {

    /**
     * 模块信息
     */
    private Map<Integer, AbstractMapComponent> mapModelInfo = new HashMap<>();
}
