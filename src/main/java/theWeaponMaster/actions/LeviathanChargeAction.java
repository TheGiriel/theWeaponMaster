package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.abstractcards.AbstractDefaultCard;
import theWeaponMaster.cards.legendary_weapons.*;
import theWeaponMaster.relics.ArsenalRelic;

import java.util.HashSet;

public class LeviathanChargeAction extends AbstractGameAction {
    private boolean pickCard = false;

    public HashSet<String> leviathanSet = new HashSet<>();

    public LeviathanChargeAction(int leviathanCharging) {
        Math.min(Math.max(ArsenalRelic.leviathanCharges, 0), 3);
        for (AbstractDefaultCard c : getLeviathanCards()) {
            c.baseSecondValue += leviathanCharging;
            c.applyPowers();
        }
        ArsenalRelic.leviathanCharges += leviathanCharging;
        TheWeaponMaster.logger.info("leviathan charges: " + ArsenalRelic.leviathanCharges);
        this.isDone = true;
    }

    public LeviathanChargeAction(int leviathanCharging, int damageOrBlock, boolean discharge) {
        AbstractPlayer p = AbstractDungeon.player;
        Math.min(Math.max(ArsenalRelic.leviathanCharges, 0), 3);
        for (AbstractDefaultCard c : getLeviathanCards()) {
            c.baseSecondValue += leviathanCharging;
            c.applyPowers();
        }
        if (discharge) {
            for (int i = 0; i < leviathanCharging; i++) {
                AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, damageOrBlock), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }
        } else {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, damageOrBlock * (3 - ArsenalRelic.leviathanCharges)));
            new LeviathanChargeAction(3 - ArsenalRelic.leviathanCharges, damageOrBlock, false);
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
