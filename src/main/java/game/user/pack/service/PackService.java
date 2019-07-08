package game.user.pack.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.common.I18N;
import game.common.exception.RequestException;
import game.role.player.model.Player;
import game.user.item.base.model.AbstractItem;
import game.user.item.resource.ItemResource;
import game.user.pack.model.Pack;
import game.user.pack.model.PackSquare;
import game.user.pack.packet.SM_PackInfo;
import net.utils.PacketUtil;
import spring.SpringContext;

/**
 * @author : ddv
 * @since : 2019/6/1 上午10:08
 */
@Component
public class PackService implements IPackService {

    private static final Logger logger = LoggerFactory.getLogger(PackSquare.class);

    @Autowired
    private PackManager packManager;

    @Override
    public Pack getPlayerPack(Player player, boolean clientRequest) {
        Pack pack = getPack(player);
        if (clientRequest) {
            sendPackDetails(player, pack);
        }
        return pack;
    }

    @Override
    public void addItem(Player player, AbstractItem item) {
        Pack pack = getPack(player);
        pack.addItems(item);
        packManager.save(player.getPlayerId());
        sendPackDetails(player, pack);
    }

    @Override
    public void useItem(Player player, ItemResource itemResource, int count) {}

    @Override
    public ItemResource getResource(Long configId) {
        return packManager.getResource(configId);
    }

    @Override
    public boolean isFull(Player player) {
        Pack pack = getPack(player);
        return pack.getEmptySquares().size() == 0;
    }

    @Override
    public boolean isEnoughSize(Player player, AbstractItem item) {
        Pack pack = getPack(player);
        return pack.isEnoughSize(item);
    }

    @Override
    public int getItemNum(Player player, AbstractItem item) {
        return player.getPack().countItemNum(item);
    }

    @Override
    public void reduceItem(Player player, AbstractItem item, int num) {
        Pack pack = player.getPack();
        int itemNum = pack.countItemNum(item);
        if (itemNum < num) {
            RequestException.throwException(I18N.ITEM_NUM_NOT_ENOUGH);
        }

        pack.reduceItem(item, num);
        sendPackDetails(player, pack);
    }

    @Override
    public AbstractItem getItemFromPack(Player player, long configId) {
        Pack pack = player.getPack();
        AbstractItem item = SpringContext.getCommonService().createItem(configId, 1);
        return pack.getItem(item);

    }

    private Pack getPack(Player player) {
        return packManager.loadOrCreate(player.getPlayerId()).getPack();
    }

    private void sendPackDetails(Player player, Pack pack) {
        PacketUtil.send(player, SM_PackInfo.valueOf(pack));
    }

}
