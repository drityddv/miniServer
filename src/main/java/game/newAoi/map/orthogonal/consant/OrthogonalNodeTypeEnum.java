package game.newAoi.map.orthogonal.consant;

/**
 * @author : ddv
 * @since : 2020/1/14 11:21 AM
 */

public enum OrthogonalNodeTypeEnum {
    /**
     * x轴链表
     */
    X_LIST(1, "X轴链表"),
    /**
     * y轴链表
     */
    Y_LIST(2, "Y轴链表"),;

    private int type;
    private String desc;

    OrthogonalNodeTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

}
