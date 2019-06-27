package game.user.item.base.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : ddv
 * @since : 2019/6/26 下午2:20
 */

public class Equipment extends AbstractItem {

    private static final Logger logger = LoggerFactory.getLogger(Equipment.class);

    private int score;
    // 装备的部位
    private int type;

    private int equipType;
}
