package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.cards.AbstractBullyCard;
import theWeaponMaster.powers.ViciousPower;

public class ViciousAction extends AbstractGameAction {

    public boolean viciousUse(AbstractPlayer p, AbstractBullyCard c) {
        if (!p.hasPower("ViciousPower")) {
            c.cantUseMessage = "I don't have any Vicious Stacks.";
            return false;
        }
        if (p.getPower("ViciousPower").amount < c.bullyNumber) {
            c.cantUseMessage = "I don't have enough Vicious.";
            return false;
        }
        return true;
    }


    public void update() {
        //TODO: Charge and change up 'Ego: Vicious' whenever you use attacks (with Revenant giving extra stacks).
        //
    }
}
