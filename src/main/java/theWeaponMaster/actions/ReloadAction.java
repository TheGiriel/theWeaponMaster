package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theWeaponMaster.cards.abstractcards.AbstractRevolverCard;
import theWeaponMaster.cards.revolver.RevolverUnload;
import theWeaponMaster.relics.HeavyDrumRelic;
import theWeaponMaster.relics.RevolverRelic;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class ReloadAction extends AbstractGameAction {

    public static ArrayList<AbstractCard> ammoCards = new ArrayList<>();

    public ReloadAction() {
        if (!player.discardPile.isEmpty()) {
            for (int i = Math.min(player.discardPile.size(), 3); i > 0; i--) {
                player.discardPile.moveToDeck(player.discardPile.getBottomCard(), true);
            }
        }
        for (AbstractCard c : player.drawPile.group) {
            if (c instanceof AbstractRevolverCard) {
                ((AbstractRevolverCard) c).setNormalDescription();
            }
        }
        for (AbstractCard c : player.hand.group) {
            if (c instanceof AbstractRevolverCard) {
                ((AbstractRevolverCard) c).setNormalDescription();
            }
        }
        for (AbstractCard c : player.discardPile.group) {
            if (c instanceof AbstractRevolverCard) {
                ((AbstractRevolverCard) c).setNormalDescription();
            }
        }
        for (AbstractCard c : player.exhaustPile.group) {
            if (c instanceof AbstractRevolverCard) {
                ((AbstractRevolverCard) c).setNormalDescription();
            }
        }
        player.draw(1);

        if (player.hasRelic(HeavyDrumRelic.ID)) {
            player.getRelic(RevolverRelic.ID).counter = 5;
            RevolverRelic.shotsLeft = 5;
        } else {
            player.getRelic(RevolverRelic.ID).counter = 6;
            RevolverRelic.shotsLeft = 6;
        }

        for (AbstractCard card : player.hand.group) {
            if (card.cardID.equals(RevolverUnload.ID)) {
                ((AbstractRevolverCard) card).baseSecondValue = RevolverRelic.shotsLeft;
            }
        }
        for (AbstractCard card : player.drawPile.group) {
            if (card.cardID.equals(RevolverUnload.ID)) {
                ((AbstractRevolverCard) card).baseSecondValue = RevolverRelic.shotsLeft;
            }
        }
        for (AbstractCard card : player.discardPile.group) {
            if (card.cardID.equals(RevolverUnload.ID)) {
                ((AbstractRevolverCard) card).baseSecondValue = RevolverRelic.shotsLeft;
            }
        }

        resetCost();
        isDone = true;
    }

    private void resetCost() {
        ammoCards.addAll(AbstractDungeon.player.drawPile.group);
        ammoCards.addAll(AbstractDungeon.player.hand.group);
        ammoCards.addAll(AbstractDungeon.player.discardPile.group);
        ammoCards.addAll(AbstractDungeon.player.exhaustPile.group);
        for (AbstractCard c : ammoCards) {
            if (c instanceof AbstractRevolverCard) {
                ((AbstractRevolverCard) c).setNormalDescription();
            }
        }
    }

    @Override
    public void update() {

    }
}
