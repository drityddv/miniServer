package game.base.map.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.game.attribute.AttributeContainer;
import game.base.object.AbstractCreature;

/**
 * 地图中的生物单位
 *
 * @author : ddv
 * @since : 2019/5/6 下午9:26
 */

public class MapCreature extends AbstractCreature {

    private static final Logger logger = LoggerFactory.getLogger(MapCreature.class);

    private String accountId;
    private int x;
    private int y;
    private CreatureType type;
    private AttributeContainer attributeContainer;

    public static MapCreature valueOf(String accountId, long objectId, int x, int y) {
        MapCreature creature = new MapCreature();
        creature.setAccountId(accountId);
        creature.setX(x);
        creature.setY(y);
        creature.setIsAlive(1);
        creature.setId(objectId);
        return creature;
    }

    // get and set
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "MapCreature{" + "accountId='" + accountId + '\'' + ", x=" + x + ", y=" + y + '}';
    }

    // 地图单位状态,坐标打印
    public void print() {
        logger.info("单位所属[{}],单位id[{}],坐标[{},{}],存活状态[{}]", accountId, getId(), x, y, getIsAlive());
    }

    // 重置单位状态
    public void reset() {
        this.x = 0;
        this.y = 0;
        this.setIsAlive(1);
    }

}
