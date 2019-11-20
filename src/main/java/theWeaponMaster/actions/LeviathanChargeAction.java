package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.abstractcards.AbstractDefaultCard;
import theWeaponMaster.cards.legendary_weapons.*;
import theWeaponMaster.relics.ArsenalRelic;

import java.util.HashSet;

public class LeviathanChargeAction extends AbstractGameAction {

    public HashSet<String> leviathanSet = new HashSet<>();

    public LeviathanChargeAction(int leviathanCharging) {
        Math.min(Math.max(ArsenalRelic.leviathanCharges, 0), 3);
        for (AbstractDefaultCard c : getLeviathanCards()) {
            c.defaultBaseSecondMagicNumber += leviathanCharging;
            c.applyPowers();
        }
        ArsenalRelic.leviathanCharges += leviathanCharging;
        TheWeaponMaster.logger.info("leviathan charges: " + ArsenalRelic.leviathanCharges);
        this.isDone = true;
    }


    private HashSet<AbstractDefaultCard> getLeviathanCards() {
        HashSet<AbstractDefaultCard> leviathanCharges = new HashSet<>();

        AbstractDefaultCard card = (AbstractDefaultCard) AbstractDungeon.player.cardInUse;
        if (card != null && card.cardID.equals(this)) {
            leviathanCharges.add(card);
        }

        addCards(leviathanCharges, AbstractDungeon.player.drawPile);

        addCards(leviathanCharges, AbstractDungeon.player.discardPile);

        addCards(leviathanCharges, AbstractDungeon.player.exhaustPile);

        addCards(leviathanCharges, AbstractDungeon.player.limbo);

        addCards(leviathanCharges, AbstractDungeon.player.hand);


        return leviathanCharges;
    }

    private void addCards(HashSet<AbstractDefaultCard> leviathanCharges, CardGroup cardGroup) {
        update();

        for (AbstractCard c : cardGroup.group) {
            if (leviathanSet.contains(c.cardID)) {
                leviathanCharges.add((AbstractDefaultCard) c);
            }
        }
    }

    @Override
    public void update() {
        leviathanSet.add(LeviathanCrush.ID);
        leviathanSet.add(LeviathanGauntletCharger.ID);
        leviathanSet.add(LeviathanGroundSplitter.ID);
        leviathanSet.add(LeviathanDeepImpact.ID);
        leviathanSet.add(LeviathanEarthquake.ID);
    }
}
