package game.scene.fight.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import game.base.fight.model.pvpunit.FighterAccount;
import game.role.player.model.Player;

/**
 * @author : ddv
 * @since : 2019/7/5 下午9:28
 */
@Component
public class FightService implements IFightService {

    private static final Logger logger = LoggerFactory.getLogger(FightService.class);

    @Override
    public FighterAccount initForPlayer(Player player) {
        FighterAccount fighterAccount = FighterAccount.valueOf(player);
        return fighterAccount;
    }
}
