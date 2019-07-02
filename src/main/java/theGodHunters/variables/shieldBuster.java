package theGodHunters.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static theGodHunters.DefaultMod.makeID;

public class shieldBuster extends DynamicVariable {
    @Override
    public String key() {
        return makeID("SHIELD_BUSTER");
        // What you put in your localization file between ! to show your value. Eg, !myKey!.
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return card.isDamageModified;
    }

    @Override
    public int value(AbstractCard card) {
        return (card.damage * 2);
    }

    @Override
    public int baseValue(AbstractCard card) {
        return card.damage;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return card.upgradedDamage;
    }
}