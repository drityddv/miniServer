package game.base.fight.pvpunit;

/**
 * @author : ddv
 * @since : 2019/7/2 上午10:28
 */

public class FighterAccount {
    private String accountId;

    private transient boolean isAttack = false;

    /**
     * 目标账号
     */
    private transient FighterAccount targetAccount;

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
}
