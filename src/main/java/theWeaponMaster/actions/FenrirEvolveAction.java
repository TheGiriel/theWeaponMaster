package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.*;

import java.util.HashSet;
import java.util.Iterator;

public class FenrirEvolveAction extends AbstractGameAction {

    public HashSet<String> fenrirSet = new HashSet<>();

    public FenrirEvolveAction() {
        for (AbstractCard c : getFenrirCards()) {
            if (c.cardID.equals("theWeaponMaster:FenrirDefensiveStance")) {
                c.baseBlock++;
            } else if (c.cardID.equals("theWeaponMaster:FenrirHeavySwing")) {
                c.baseMagicNumber++;
            } else {
                if (c.cardID.equals(FenrirUnleashed.ID)) {
                    TheWeaponMaster.logger.info("Base Damage before Evolve: " + c.baseDamage);
                }
                c.baseDamage++;
            }
            c.applyPowers();
            if (c.cardID.equals(FenrirUnleashed.ID)) {
                TheWeaponMaster.logger.info("Base Damage after Evolve: " + c.baseDamage);
            }
        }
        this.isDone = true;
    }


    private HashSet<AbstractCard> getFenrirCards() {
        HashSet<AbstractCard> fenrirUpgrades = new HashSet<>();

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
        update();

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
        fenrirSet.add(FenrirLacerate.ID);
        fenrirSet.add(FenrirHeavySwing.ID);
        fenrirSet.add(FenrirShieldEater.ID);
        fenrirSet.add(FenrirDefensiveStance.ID);
        fenrirSet.add(FenrirUnleashed.ID);
    }
}
