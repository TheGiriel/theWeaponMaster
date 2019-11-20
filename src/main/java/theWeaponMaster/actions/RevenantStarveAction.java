package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theWeaponMaster.cards.abstractcards.AbstractDefaultCard;
import theWeaponMaster.cards.legendary_weapons.*;
import theWeaponMaster.relics.ArsenalRelic;

import java.util.HashSet;

public class RevenantStarveAction extends AbstractGameAction {

    public HashSet<String> revenantSet = new HashSet<>();

    public RevenantStarveAction(int revenantStarve) {
        for (AbstractDefaultCard c : getRevenantCards()) {
            c.defaultBaseSecondMagicNumber += revenantStarve;
            c.applyPowers();
        }
        ArsenalRelic.revenantHunger += revenantStarve;
        this.isDone = true;
    }


    private HashSet<AbstractDefaultCard> getRevenantCards() {
        HashSet<AbstractDefaultCard> revenantHunger = new HashSet<>();

        AbstractDefaultCard card = (AbstractDefaultCard) AbstractDungeon.player.cardInUse;
        if (card != null && card.cardID.equals(this)) {
            revenantHunger.add(card);
        }

        addCards(revenantHunger, AbstractDungeon.player.drawPile);

        addCards(revenantHunger, AbstractDungeon.player.discardPile);

        addCards(revenantHunger, AbstractDungeon.player.exhaustPile);

        addCards(revenantHunger, AbstractDungeon.player.limbo);

        addCards(revenantHunger, AbstractDungeon.player.hand);


        return revenantHunger;
    }

    private void addCards(HashSet<AbstractDefaultCard> revenantHunger, CardGroup cardGroup) {
        update();

        for (AbstractCard c : cardGroup.group) {
            if (revenantSet.contains(c.cardID)) {
                revenantHunger.add((AbstractDefaultCard) c);
            }
        }

    }

    @Override
    public void update() {
        revenantSet.add(RevenantRavenous.ID);
        revenantSet.add(RevenantChopChopCHOP.ID);
        revenantSet.add(RevenantHungrySteel.ID);
        revenantSet.add(RevenantNoseToTail.ID);
        revenantSet.add(RevenantBloodbath.ID);
    }
}
