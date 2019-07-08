package game.base.fight.model.pvpunit;

import game.role.player.model.Player;

/**
 * @author : ddv
 * @since : 2019/7/2 上午10:28
 */

public class FighterAccount {
    private String accountId;

    private transient boolean isAttack = false;

    // 战斗单位
    private BaseCreatureUnit creatureUnit;

    /**
     * 目标账号
     */
    private transient FighterAccount targetAccount;

    public static FighterAccount valueOf(Player player) {
        FighterAccount fighterAccount = new FighterAccount();
        fighterAccount.setAccountId(player.getAccountId());
        fighterAccount.setAttack(false);
        fighterAccount.setTargetAccount(null);
        fighterAccount.setCreatureUnit(PlayerUnit.valueOf(player, fighterAccount));
        return fighterAccount;
    }

    // get and set
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public boolean isAttack() {
        return isAttack;
    }

    public void setAttack(boolean attack) {
        isAttack = attack;
    }

    public FighterAccount getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(FighterAccount targetAccount) {
        this.targetAccount = targetAccount;
    }

    public BaseCreatureUnit getCreatureUnit() {
        return creatureUnit;
    }

    public void setCreatureUnit(BaseCreatureUnit creatureUnit) {
        this.creatureUnit = creatureUnit;
    }
}
