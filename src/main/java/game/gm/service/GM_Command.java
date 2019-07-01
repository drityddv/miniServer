package game.gm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.scene.map.service.SceneMapManager;
import game.user.equip.model.EquipStorage;
import game.user.equip.packet.SM_EquipStorage;
import game.user.equip.service.EquipService;
import game.user.pack.model.Pack;
import game.user.pack.packet.SM_PackInfo;
import game.user.pack.service.IPackService;
import game.user.player.model.Player;
import game.user.player.service.IPlayerService;
import net.utils.PacketUtil;

/**
 * gm命令后台实现
 *
 * @author : ddv
 * @since : 2019/5/7 下午3:01
 */

@Component
public class GM_Command {

    private static final Logger logger = LoggerFactory.getLogger(GM_Command.class);

    @Autowired
    private SceneMapManager sceneMapManager;
    @Autowired
    private IPackService packService;

    @Autowired
    private IPlayerService playerService;

    @Autowired
    private EquipService equipService;

    public void logPack(Player player) {
        Pack pack = player.getPack();
        PacketUtil.send(player, SM_PackInfo.valueOf(pack));
    }

    public void logEquipStorage(Player player) {
        EquipStorage equipStorage = player.getEquipStorage();
        PacketUtil.send(player, SM_EquipStorage.valueOf(equipStorage));
    }

    public void addItem(Player player, long configId, int num) {
        packService.addItem(player, packService.createItem(configId), num);
    }

    public void levelUp(Player player, int newLevel) {
        player.setLevel(newLevel);
		playerService.savePlayer(player);

    }

}
