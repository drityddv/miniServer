package game.user.pack.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.base.item.base.model.AbstractItem;
import game.base.item.resource.ItemResource;
import game.base.message.I18N;
import game.base.message.exception.RequestException;
import game.role.player.model.Player;
import game.user.pack.model.Pack;
import game.user.pack.model.PackSquare;
import game.user.pack.packet.SM_Items;
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
    public void addItemWithThrow(Player player, AbstractItem item) {
        Pack pack = getPack(player);
        pack.addItemWithThrow(item);
        packManager.save(player.getPlayerId());
        sendPackDetails(player, pack);
    }

    @Override
    public boolean addItem(Player player, AbstractItem item) {
        Pack pack = getPack(player);
        boolean success = pack.addItem(item);
        if (success) {
            packManager.save(player.getPlayerId());
        }
        return success;
    }

    @Override
    public boolean addItems(Player player, List<AbstractItem> items) {
        Pack pack = getPack(player);
        for (AbstractItem item : items) {
            if (!pack.addItem(item)) {
                return false;
            }
        }
        packManager.save(player.getPlayerId());
        PacketUtil.send(player, SM_Items.valueOf(items));
        return true;
    }

    @Override
    public void useItem(Player player, ItemResource itemResource, int count) {}

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
    public boolean isEnoughSize(Player player, List<AbstractItem> itemList) {
        for (AbstractItem abstractItem : itemList) {
            if (!isEnoughSize(player, abstractItem)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isEnoughSize(Player player, long itemConfigId, int itemNum) {
        Pack pack = getPack(player);
        return pack.isEnoughSize(itemConfigId, itemNum);
    }

    @Override
    public int getItemNum(Player player, AbstractItem item) {
        return player.getPack().countItemNum(item);
    }

    @Override
    public boolean reduceItem(Player player, AbstractItem item) {
        Pack pack = getPlayerPack(player, false);
        boolean enoughItem = pack.isEnoughItem(item);
        if (!enoughItem) {
            return false;
        }
        pack.doReduceItem(item);
        return true;
    }

    @Override
    public void reduceItemWithThrow(Player player, AbstractItem item) {
        Pack pack = getPlayerPack(player, false);
        boolean enoughItem = pack.isEnoughItem(item);
        if (!enoughItem) {
            RequestException.throwException(I18N.ITEM_NUM_NOT_ENOUGH);
        }
        pack.doReduceItem(item);
    }

    @Override
    public void reduceItemWithThrow(Player player, long itemConfigId, int num) {
        Pack pack = getPlayerPack(player, false);
        boolean enoughSize = pack.isEnoughItem(itemConfigId, num);
        if (!enoughSize) {
            RequestException.throwException(I18N.ITEM_NUM_NOT_ENOUGH);
        }
        pack.doReduceItem(itemConfigId, num);
    }

    @Override
    public boolean reduceItem(Player player, long itemConfigId, int num) {
        Pack pack = getPlayerPack(player, false);
        if (!pack.isEnoughItem(itemConfigId, num)) {
            return false;
        }
        pack.doReduceItem(itemConfigId, num);
        return true;
    }

    @Override
    public AbstractItem getItemFromPack(Player player, long configId) {
        Pack pack = player.getPack();
        AbstractItem item = SpringContext.getItemService().createItem(configId, 1);
        return pack.getItem(item);

    }

    @Override
    public void sortPack(Player player) {
        Pack pack = getPlayerPack(player, false);
        pack.sortPack();
    }

    private Pack getPack(Player player) {
        return packManager.loadOrCreate(player.getPlayerId()).getPack();
    }

    private void sendPackDetails(Player player, Pack pack) {
        PacketUtil.send(player, SM_PackInfo.valueOf(pack));
    }

}
