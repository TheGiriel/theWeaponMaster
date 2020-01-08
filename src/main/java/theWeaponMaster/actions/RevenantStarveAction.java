package theWeaponMaster.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.abstractcards.AbstractDefaultCard;
import theWeaponMaster.cards.legendary_weapons.*;
import theWeaponMaster.relics.ArsenalRelic;

import java.util.HashSet;

public class RevenantStarveAction extends AbstractGameAction {

    private static AbstractPlayer p = AbstractDungeon.player;
    private HashSet<String> revenantSet = new HashSet<>();

    public RevenantStarveAction(int revenantStarve, boolean sated) {

        revenantSet.add(RevenantRavenous.ID);
        revenantSet.add(RevenantChopChopCHOP.ID);
        revenantSet.add(RevenantHungrySteel.ID);
        revenantSet.add(RevenantNoseToTail.ID);
        revenantSet.add(RevenantBloodbath.ID);

        TheWeaponMaster.logger.info("Does this even start?");
        ArsenalRelic.revenantHunger += revenantStarve;
        TheWeaponMaster.logger.info("Arsenal hunger " + ArsenalRelic.revenantHunger);
        for (AbstractDefaultCard c : getRevenantCards()) {
            TheWeaponMaster.logger.info("Applying the hunger reduction by" + revenantStarve);
            c.baseSecondValue += revenantStarve;
            c.applyPowers();
            c.initializeDescription();
        }
        if (sated) {
            satedAction();
        }
        this.isDone = true;
    }

    public static void satedAction() {
        AbstractDungeon.actionManager.addToBottom(new AddTemporaryHPAction(p, p, 2));
    }

    private void addCards(HashSet<AbstractDefaultCard> revenantHunger, CardGroup cardGroup) {
        update();

        for (AbstractCard c : cardGroup.group) {
            if (revenantSet.contains(c.cardID)) {
                revenantHunger.add((AbstractDefaultCard) c);
            }
        }

    }

    private HashSet<AbstractDefaultCard> getRevenantCards() {
        HashSet<AbstractDefaultCard> revenantHunger = new HashSet<>();

        AbstractDefaultCard card = (AbstractDefaultCard) AbstractDungeon.player.cardInUse;
        if (card != null && card.cardID.equals(this)) {
            revenantHunger.add(card);
        }

        addCards(revenantHunger, p.drawPile);

        addCards(revenantHunger, p.discardPile);

        addCards(revenantHunger, p.exhaustPile);

        addCards(revenantHunger, p.limbo);

        addCards(revenantHunger, p.hand);


        return revenantHunger;
    }

    @Override
    public void update() {
    }
}
