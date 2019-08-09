package game.publicsystem.rank.model.type;

import java.util.Random;

import game.publicsystem.rank.constant.RankType;

/**
 * @author : ddv
 * @since : 2019/8/5 9:37 PM
 */

public abstract class BaseRankInfo {
    protected String id;
    protected long value;

    public BaseRankInfo() {}

    public BaseRankInfo(String id, long value) {
        this.id = id;
        this.value = value;
    }

    /**
     * 获取对应排行榜的类型
     *
     * @return
     */
    public abstract RankType getType();

    /**
     * 加载逻辑
     */
    public abstract void init();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BaseRankInfo rankInfo = (BaseRankInfo)o;

        return id != null ? id.equals(rankInfo.id) : rankInfo.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "BaseRankInfo{" + "id=" + id + ", value=" + value + '}';
    }

    public void random() {
        value = new Random().nextInt((int)value);
    }
}
