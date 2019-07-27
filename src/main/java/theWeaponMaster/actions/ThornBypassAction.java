package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;

public class ThornBypassAction {
    //Todo: make this work
    private int thorns;
    public AbstractMonster m;

    public ThornBypassAction(AbstractMonster m, int thorns) {
        this.m = m;
        this.thorns = thorns;
        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(m, AbstractDungeon.player, ThornsPower.POWER_ID));
    }

    public void ThornsReapply() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new ThornsPower(m, thorns), thorns, true));
    }
}
