package theWeaponMaster.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theWeaponMaster.cards.AbstractBullyCard;

import static theWeaponMaster.DefaultMod.makeID;

public class BullyVariable extends DynamicVariable {


    @Override
    public String key() {
        return makeID("BLY");
    }

    @Override
    public boolean isModified(AbstractCard abstractCard) {
        AbstractBullyCard c = (AbstractBullyCard) abstractCard;
        return c.isBullyModified;
    }

    @Override
    public int value(AbstractCard abstractCard) {
        AbstractBullyCard c = (AbstractBullyCard) abstractCard;
        return c.bullyNumber;
    }

    @Override
    public int baseValue(AbstractCard abstractCard) {
        AbstractBullyCard c = (AbstractBullyCard) abstractCard;
        return c.baseBullyNumber;
    }

    @Override
    public boolean upgraded(AbstractCard abstractCard) {
        AbstractBullyCard c = (AbstractBullyCard) abstractCard;
        return c.upgradedBullyNumber;
    }
}
