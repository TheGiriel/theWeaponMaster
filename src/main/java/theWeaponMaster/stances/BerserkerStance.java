package theWeaponMaster.stances;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.stances.AbstractStance;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.powers.ViciousPower;

public class BerserkerStance extends AbstractStance {

    public static String stanceID = TheWeaponMaster.makeID(BerserkerStance.class.getSimpleName());

    public BerserkerStance() {
        this.ID = stanceID;
    }

    @Override
    public void onPlayCard(AbstractCard card) {
        if (card.type.equals(AbstractCard.CardType.ATTACK))

            super.onPlayCard(card);
    }

    @Override
    public void onEnterStance() {
        ViciousPower.berserkerStanceBonus = 1;
        super.onEnterStance();
    }

    @Override
    public void onExitStance() {
        ViciousPower.berserkerStanceBonus = 0;
        super.onExitStance();
    }

    @Override
    public void updateDescription() {

    }
}
