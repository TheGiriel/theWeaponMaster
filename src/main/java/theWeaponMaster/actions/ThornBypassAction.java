package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;

public class ThornBypassAction {
    //Todo: make this work
    private static int thorns;
    public AbstractMonster m;

    public ThornBypassAction(AbstractMonster m, int thorns) {
        this.m = m;
        ThornBypassAction.thorns = thorns;
        //m.getPower(ThornsPower.POWER_ID).amount=0;
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.m, this.m, ThornsPower.POWER_ID, ThornBypassAction.thorns));
    }

    public void ThornsReapply() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new ThornsPower(m, thorns)));
    }
}
