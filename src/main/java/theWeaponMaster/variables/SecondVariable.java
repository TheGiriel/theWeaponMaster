package theWeaponMaster.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;

public class SecondVariable extends DynamicVariable {

    @Override
    public String key() {
        return "SV";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        AbstractDynamicCard c = (AbstractDynamicCard) card;
        return c.isDefaultSecondMagicNumberModified;
    }

    @Override
    public int value(AbstractCard card) {
        AbstractDynamicCard c = (AbstractDynamicCard) card;
        return c.defaultSecondMagicNumber;
    }

    @Override
    public int baseValue(AbstractCard card) {
        AbstractDynamicCard c = (AbstractDynamicCard) card;
        return c.defaultBaseSecondMagicNumber;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        AbstractDynamicCard c = (AbstractDynamicCard) card;
        return c.upgradedDefaultSecondMagicNumber;
    }
}