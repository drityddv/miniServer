package client.handler.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.handler.IHandler;
import game.user.pack.packet.SM_PackInfo;

/**
 * @author : ddv
 * @since : 2019/7/10 下午10:34
 */

public class PackHandler implements IHandler<SM_PackInfo> {
    private static Logger logger = LoggerFactory.getLogger(PackHandler.class);

    @Override
    public void handler(SM_PackInfo sm_packInfo) {
        logger.info("背包大小[{}]", sm_packInfo.getSize());
        sm_packInfo.getSquareList().stream().filter(packSquare -> !packSquare.isEmpty()).forEach(packSquare -> {
            logger.info("格子编号[{}] 道具id[{}] 道具数量[{}],", packSquare.getIndex(), packSquare.getItem().getConfigId(),
                packSquare.getItemNum());
        });
    }
}
