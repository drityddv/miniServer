package game.base.game.attribute.id;

/**
 * @author : ddv
 * @since : 2019/7/24 10:33 PM
 */

public class PvpBuffAttributeId implements AttributeId {
    private long buffId;

    public PvpBuffAttributeId(long buffId) {
        this.buffId = buffId;
    }

    public static PvpBuffAttributeId valueOf(long buffId) {
        return new PvpBuffAttributeId(buffId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PvpBuffAttributeId that = (PvpBuffAttributeId)o;

        return buffId == that.buffId;
    }

    @Override
    public int hashCode() {
        return (int)(buffId ^ (buffId >>> 32));
    }

    @Override
    public String getName() {
        return "buffId{id=" + buffId + '}';
    }

}
