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
        return c.isSecondValueModified;
    }

    @Override
    public int value(AbstractCard card) {
        AbstractDynamicCard c = (AbstractDynamicCard) card;
        return c.secondValue;
    }

    @Override
    public int baseValue(AbstractCard card) {
        AbstractDynamicCard c = (AbstractDynamicCard) card;
        return c.baseSecondValue;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        AbstractDynamicCard c = (AbstractDynamicCard) card;
        return c.upgradedSecondValue;
    }
}