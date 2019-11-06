package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class WeaponCardRemovalAction extends AbstractGameAction {

    public WeaponCardRemovalAction(String a, String b, String c, String d, String e, String f, String g){

        ArrayList<AbstractCard> masterDeckCopy = new ArrayList<>();
        for (AbstractCard copyCheck : AbstractDungeon.player.masterDeck.group) {
            if (copyCheck.cardID.equals(a) || copyCheck.cardID.equals(b) || copyCheck.cardID.equals(c) || copyCheck.cardID.equals(d) || copyCheck.cardID.equals(e) || copyCheck.cardID.equals(f) || copyCheck.cardID.equals(g)) {
                masterDeckCopy.add(copyCheck);
            }
        }
        for (AbstractCard card : masterDeckCopy){
            AbstractDungeon.player.masterDeck.removeCard(card);
        }
        masterDeckCopy.clear();

        ArrayList<AbstractCard> handCopy = new ArrayList<>();
        for (AbstractCard copyCheck : AbstractDungeon.player.hand.group) {
            if (copyCheck.cardID.equals(a) || copyCheck.cardID.equals(b) || copyCheck.cardID.equals(c) || copyCheck.cardID.equals(d) || copyCheck.cardID.equals(e) || copyCheck.cardID.equals(f) || copyCheck.cardID.equals(g)) {
                handCopy.add(copyCheck);
            }
        }
        for (AbstractCard card : handCopy) {
            AbstractDungeon.actionManager.addToTop((AbstractGameAction) new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand, true));
        }
        handCopy.clear();

        ArrayList<AbstractCard> deckCopy = new ArrayList<>();
        for (AbstractCard copyCheck : AbstractDungeon.player.drawPile.group) {
            if (copyCheck.cardID.equals(a) || copyCheck.cardID.equals(b) || copyCheck.cardID.equals(c) || copyCheck.cardID.equals(d) || copyCheck.cardID.equals(e) || copyCheck.cardID.equals(f) || copyCheck.cardID.equals(g)) {
                deckCopy.add(copyCheck);
            }
        }
        for (AbstractCard card : deckCopy) {
            AbstractDungeon.actionManager.addToTop((AbstractGameAction) new ExhaustSpecificCardAction(card, AbstractDungeon.player.drawPile, true));
        }
        deckCopy.clear();

        ArrayList<AbstractCard> discardCopy = new ArrayList<>();
        for (AbstractCard copyCheck : AbstractDungeon.player.discardPile.group) {
            if (copyCheck.cardID.equals(a) || copyCheck.cardID.equals(b) || copyCheck.cardID.equals(c) || copyCheck.cardID.equals(d) || copyCheck.cardID.equals(e) || copyCheck.cardID.equals(f) || copyCheck.cardID.equals(g)) {
                discardCopy.add(copyCheck);
            }
        }
        for (AbstractCard card : discardCopy) {
            AbstractDungeon.actionManager.addToTop((AbstractGameAction) new ExhaustSpecificCardAction(card, AbstractDungeon.player.discardPile, true));
        }
        discardCopy.clear();
    }

    @Override
    public void update() {

    }
}
