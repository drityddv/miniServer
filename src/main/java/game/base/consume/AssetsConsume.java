package game.base.consume;

import game.role.player.model.Player;

/**
 * @author : ddv
 * @since : 2019/7/11 9:02 PM
 */

public class AssetsConsume extends BaseConsume {

    private AssetsType type;
    private long configId;
    private int value;

    @Override
    public void doParse(String value) {
        String[] split = value.split(":");
        {
            if (split.length == 2) {
                this.type = AssetsType.getByName(split[0]);
                this.value = Integer.parseInt(split[1]);
            } else if (split.length == 3) {
                this.type = AssetsType.getByName(split[0]);
                this.configId = Long.parseLong(split[1]);
                this.value = Integer.parseInt(split[2]);
            }
        }

    }

    @Override
    public void doVerify(Player player, VerifyResult result) {
        result = type.verify(player, ConsumeParam.valueOf(configId, value));
    }

    @Override
    public void doConsume(Player player) {
		type.consume(player,ConsumeParam.valueOf(configId, value));
    }

    @Override
    public boolean merge(BaseConsume consume) {
        if (consume instanceof AssetsConsume) {
            AssetsConsume assetsConsume = (AssetsConsume)consume;
            if (this.type == assetsConsume.type) {
                this.value += assetsConsume.value;
            }
            return true;
        }
        return false;
    }
}
