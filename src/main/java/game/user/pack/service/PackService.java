package game.user.pack.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import game.base.item.Item;
import game.user.pack.model.Pack;
import game.user.pack.model.PackSquare;
import game.user.pack.packet.SM_PackInfo;
import game.user.player.model.Player;
import net.utils.PacketUtil;

/**
 * @author : ddv
 * @since : 2019/6/1 上午10:08
 */
@Component
public class PackService implements IPackService, ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(PackSquare.class);

    @Autowired
    private PackManager packManager;

    @Override
    public Pack getPlayerPack(Player player) {
        Pack pack = getPack(player);
        sendPackDetails(player, pack);
        return pack;
    }

    @Override
    public void addItem(Player player, Item item, int count) {
        Pack pack = getPack(player);
        pack.addItem(item, count);
        sendPackDetails(player, pack);
    }

    @Override
    public void useItem(Player player, Item item, int count) {
        Pack pack = getPack(player);
        pack.useItem(item, count);
    }

    private Pack getPack(Player player) {
        return packManager.loadOrCreate(player.getPlayerId()).getPack();
    }

    private void sendPackDetails(Player player, Pack pack) {
        PacketUtil.send(player, SM_PackInfo.valueOf(pack));
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("pack service init...");
    }
}
