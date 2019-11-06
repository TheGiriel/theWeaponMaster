package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theWeaponMaster.powers.*;

public class ExchangeWeaponsAction extends AbstractGameAction {

    private final AbstractCreature owner;

    public ExchangeWeaponsAction(AbstractCreature owner, String power){
        this.owner = owner;
        if (!power.equals("Fenrir")) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, FenrirPower.POWER_ID));
        }
        if (!power.equals("Cerberus")) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, CerberusPower.POWER_ID));
        }
        if (!power.equals("Revenant")) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, RevenantPower.POWER_ID));
        }
        if (!power.equals("Atropos")) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, AtroposPower.POWER_ID));
        }
        if (!power.equals("Leviathan")) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, LeviathanPower.POWER_ID));
        }
    }
    @Override
    public void update() {
    }
}
