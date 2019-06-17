package game.user.pack.packet;

import game.user.pack.model.Pack;

/**
 * id: 91
 *
 * @author : ddv
 * @since : 2019/6/6 下午11:45
 */

public class SM_PackInfo {

    private Pack pack;

    public static SM_PackInfo valueOf(Pack pack) {
        SM_PackInfo packInfo = new SM_PackInfo();
        packInfo.pack = pack;
        return packInfo;
    }

    @Override
    public String toString() {
        return "SM_PackInfo{" + "pack=" + pack + '}';
    }
}
