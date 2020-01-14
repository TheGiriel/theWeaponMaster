package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
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
        TheWeaponMaster.logger.info("Starting Charge Action [x   ]");
        Math.min(Math.max(ArsenalRelic.leviathanCharges, 0), 3);
        ArsenalRelic.leviathanCharges += leviathanCharging;
        for (AbstractDefaultCard c : getLeviathanCards()) {
            if (c.cardID.equals(LeviathanGroundSplitter.ID)) {
                c.setBackgroundTexture("theWeaponMasterResources/images/512/bg_leviathan_skill_" + ArsenalRelic.leviathanCharges + "_charge_sm.png",
                        "theWeaponMasterResources/images/1024/bg_leviathan_skill_" + ArsenalRelic.leviathanCharges + "_charge.png");
            } else {
                c.setBackgroundTexture("theWeaponMasterResources/images/512/bg_leviathan_attack_" + ArsenalRelic.leviathanCharges + "_charge_sm.png",
                        "theWeaponMasterResources/images/1024/bg_leviathan_attack_" + ArsenalRelic.leviathanCharges + "_charge.png");
            }
            c.baseSecondValue += leviathanCharging;
            c.applyPowers();
            c.initializeDescription();
            c.getCardBg();
        }
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

        cardGroup.group.forEach(e -> {
            if (leviathanSet.contains(e.cardID)) leviathanCharges.add((AbstractDefaultCard) e);
        });
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
