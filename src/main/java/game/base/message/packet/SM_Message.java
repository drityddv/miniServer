package game.base.message.packet;

/**
 * @author : ddv
 * @since : 2019/5/6 下午12:26
 */

public class SM_Message {

    int i18nId;

    public static SM_Message valueOf(int i18nId) {
        SM_Message sm = new SM_Message();
        sm.i18nId = i18nId;
        return sm;
    }

    public int getI18nId() {
        return i18nId;
    }

    public void setI18nId(int i18nId) {
        this.i18nId = i18nId;
    }

    @Override
    public String toString() {
        return "SM_Message{" + "i18nId=" + i18nId + '}';
    }
}
