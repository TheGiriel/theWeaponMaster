package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theWeaponMaster.cards.legendary_weapons.*;

import java.util.HashSet;

public class FenrirEvolveAction extends AbstractGameAction {

    public HashSet<String> fenrirSet = new HashSet<>();

    public FenrirEvolveAction() {

        fenrirSet.add(FenrirLacerate.ID);
        fenrirSet.add(FenrirHeavySwing.ID);
        fenrirSet.add(FenrirShieldEater.ID);
        fenrirSet.add(FenrirProtectingBlade.ID);
        fenrirSet.add(FenrirUnleashed.ID);

        for (AbstractCard c : getFenrirCards()) {
            if (c.cardID.equals(FenrirProtectingBlade.ID)) {
                c.baseBlock++;
            } else if (c.cardID.equals(FenrirHeavySwing.ID) || c.cardID.equals(FenrirShieldEater.ID)) {
                c.baseMagicNumber++;
            } else {
                c.baseDamage++;
            }
            c.applyPowers();
        }
        this.isDone = true;
    }

    public FenrirEvolveAction(int devolveCount) {

        fenrirSet.add(FenrirLacerate.ID);
        fenrirSet.add(FenrirHeavySwing.ID);
        fenrirSet.add(FenrirShieldEater.ID);
        fenrirSet.add(FenrirProtectingBlade.ID);
        fenrirSet.add(FenrirUnleashed.ID);

        for (AbstractCard c : getFenrirCards()) {
            if (c.cardID.equals(FenrirProtectingBlade.ID)) {
                c.baseBlock -= devolveCount;
            } else if (c.cardID.equals(FenrirHeavySwing.ID) || c.cardID.equals(FenrirShieldEater.ID)) {
                c.baseMagicNumber -= devolveCount;
            } else {
                c.baseDamage -= devolveCount;
            }
            c.applyPowers();
        }
        this.isDone = true;
    }


    private HashSet<AbstractCard> getFenrirCards() {
        HashSet<AbstractCard> fenrirUpgrades = new HashSet<>();

        AbstractCard card = AbstractDungeon.player.cardInUse;
        if (card != null && card.cardID.equals(this)) {
            fenrirUpgrades.add(card);
        }

        addCards(fenrirUpgrades, AbstractDungeon.player.drawPile);

        addCards(fenrirUpgrades, AbstractDungeon.player.discardPile);

        addCards(fenrirUpgrades, AbstractDungeon.player.exhaustPile);

        addCards(fenrirUpgrades, AbstractDungeon.player.limbo);

        addCards(fenrirUpgrades, AbstractDungeon.player.hand);


        return fenrirUpgrades;
    }

    private void addCards(HashSet<AbstractCard> fenrirUpgrades, CardGroup cardGroup) {
        update();

        for (AbstractCard c : cardGroup.group) {
            if (fenrirSet.contains(c.cardID)) {
                fenrirUpgrades.add(c);
            }
        }
    }

    @Override
    public void update() {

    }
}
