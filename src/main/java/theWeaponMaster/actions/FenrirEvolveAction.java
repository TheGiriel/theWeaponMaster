package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.HashSet;
import java.util.Iterator;

public class FenrirEvolveAction extends AbstractGameAction {

    private HashSet<String> fenrirSet = new HashSet<>();
    public FenrirEvolveAction() {
        for (AbstractCard c : getFenrirCards()) {
            if (c.cardID.equals("theWeaponMaster:FenrirDefensiveStance")) {
                c.baseBlock++;
            } else if (c.cardID.equals("theWeaponMaster:FenrirHeavySwing")) {
                c.baseMagicNumber++;
            } else {
                c.baseDamage++;
            }
            c.applyPowers();
        }
    }


    private HashSet<AbstractCard> getFenrirCards() {
        HashSet<AbstractCard> fenrirUpgrades = new HashSet<AbstractCard>();

        AbstractCard card = AbstractDungeon.player.cardInUse;
        if (card != null && card.cardID.equals(this)) {
            fenrirUpgrades.add(card);
        }

        Iterator cardIterator = AbstractDungeon.player.drawPile.group.iterator();
        addCards(fenrirUpgrades, cardIterator);

        cardIterator = AbstractDungeon.player.discardPile.group.iterator();
        addCards(fenrirUpgrades, cardIterator);

        cardIterator = AbstractDungeon.player.exhaustPile.group.iterator();
        addCards(fenrirUpgrades, cardIterator);

        cardIterator = AbstractDungeon.player.limbo.group.iterator();
        addCards(fenrirUpgrades, cardIterator);

        cardIterator = AbstractDungeon.player.hand.group.iterator();
        addCards(fenrirUpgrades, cardIterator);


        return fenrirUpgrades;
    }

    private void addCards(HashSet<AbstractCard> fenrirUpgrades, Iterator cardIterator) {
        AbstractCard c;
        while (cardIterator.hasNext()) {
            c = (AbstractCard) cardIterator.next();
            if (fenrirSet.contains(c.cardID)) {
                fenrirUpgrades.add(c);
            }
        }
    }

    @Override
    public void update() {
        fenrirSet.add("theWeaponMaster:FenrirLacerate");
        fenrirSet.add("theWeaponMaster:FenrirHeavySwing");
        fenrirSet.add("theWeaponMaster:FenrirShieldEater");
        fenrirSet.add("theWeaponMaster:FenrirDefensiveStance");
        fenrirSet.add("theWeaponMaster:FenrirUnleashed");
    }
}
