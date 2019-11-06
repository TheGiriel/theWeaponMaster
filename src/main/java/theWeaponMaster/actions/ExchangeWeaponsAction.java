package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theWeaponMaster.powers.*;

public class ExchangeWeaponsAction extends AbstractGameAction {

    private final AbstractCreature owner;

    public ExchangeWeaponsAction(AbstractCreature owner){
        this.owner = owner;
        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(owner, owner, FenrirPower.POWER_ID));
        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(owner, owner, CerberusPower.POWER_ID));
        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(owner, owner, RevenantPower.POWER_ID));
        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(owner, owner, AtroposPower.POWER_ID));
        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(owner, owner, LeviathanPower.POWER_ID));
    }
    @Override
    public void update() {
    }
}
