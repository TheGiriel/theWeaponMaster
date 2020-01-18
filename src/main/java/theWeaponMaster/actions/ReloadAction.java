package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theWeaponMaster.cards.abstractcards.AbstractRevolverCard;
import theWeaponMaster.cards.revolver.RevolverUnload;
import theWeaponMaster.relics.HeavyDrum;
import theWeaponMaster.relics.RevolverRelic;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class ReloadAction extends AbstractGameAction {


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

        if (player.hasRelic(HeavyDrum.ID)) {
            player.getRelic(RevolverRelic.ID).counter = 5;
        } else player.getRelic(RevolverRelic.ID).counter = 6;
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

        isDone = true;
    }

    @Override
    public void update() {

    }
}
