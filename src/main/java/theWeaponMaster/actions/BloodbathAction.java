package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import theWeaponMaster.powers.ViciousPower;

public class BloodbathAction extends AbstractGameAction {

    public int viciousBonus;

    public BloodbathAction(AbstractPlayer player) {
        this.source = player;
        viciousBonus = source.getPower(ViciousPower.POWER_ID).amount / 5;
    }


    @Override
    public void update() {

    }
}
