package game.user.pack.packet;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.action.IMessageAction;
import game.user.pack.model.Pack;
import game.user.pack.model.PackSquare;

/**
 * id: 91
 *
 * @author : ddv
 * @since : 2019/6/6 下午11:45
 */

public class SM_PackInfo implements IMessageAction {

    private static final Logger logger = LoggerFactory.getLogger("client");

    private int size;
    private List<PackSquare> squareList = new ArrayList<>();

    public static SM_PackInfo valueOf(Pack pack) {
        SM_PackInfo packInfo = new SM_PackInfo();
        packInfo.size = pack.getSize();
        for (PackSquare packSquare : pack.getPackSquares()) {
            if (!packSquare.isEmpty()) {
                packInfo.squareList.add(packSquare);
            }
        }
        return packInfo;
    }

    @Override
    public void action() {
        logger.info("背包大小[{}]", size);
        squareList.stream().filter(packSquare -> !packSquare.isEmpty()).forEach(packSquare -> {
            logger.info("格子编号[{}] 道具id[{}] 道具数量[{}],", packSquare.getIndex(), packSquare.getItem().getConfigId(),
                packSquare.getItemNum());
        });
    }

    @Override
    public String toString() {
        return "SM_PackInfo{" + "size=" + size + ", squareList=" + squareList + '}';
    }
}
