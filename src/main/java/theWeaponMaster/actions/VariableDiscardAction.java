//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.BiConsumer;

public class VariableDiscardAction extends AbstractGameAction {
    private final AbstractCard card;
    private final boolean anyNumber;
    private final Object state;
    private final BiConsumer<Object, ArrayList<AbstractCard>> onDiscard;

    public VariableDiscardAction(AbstractCard card, AbstractPlayer player, int discard, Object state, BiConsumer<Object, ArrayList<AbstractCard>> onDiscard) {
        this(card, player, discard, state, onDiscard, true);
    }

    public VariableDiscardAction(AbstractCard card, AbstractPlayer player, int discard, Object state, BiConsumer<Object, ArrayList<AbstractCard>> onDiscard, boolean anyNumber) {
        this.card = card;
        this.state = state;
        this.onDiscard = onDiscard;
        this.target = player;
        this.amount = discard;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.DISCARD;
        this.anyNumber = anyNumber;
    }

    public void update() {
        AbstractPlayer p = (AbstractPlayer) this.target;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (p.hand.size() != 0 && this.amount != 0) {
                String discardMessage = CardCrawlGame.languagePack.getUIString("DiscardAction").TEXT[0];
                if (this.card != null) {
                    discardMessage = discardMessage + " (" + this.card.name + ")";
                }

                AbstractDungeon.handCardSelectScreen.open(discardMessage, this.amount, this.anyNumber, this.anyNumber);
                this.tickDuration();
            } else {
                this.isDone = true;
            }

        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                ArrayList<AbstractCard> discarded = new ArrayList();
                Iterator var3 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

                while (var3.hasNext()) {
                    AbstractCard card = (AbstractCard) var3.next();
                    p.hand.moveToDiscardPile(card);
                    card.triggerOnManualDiscard();
                    GameActionManager.incrementDiscard(false);
                    AbstractDungeon.player.hand.applyPowers();
                    discarded.add(card);
                }

                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                this.onDiscard.accept(this.state, discarded);
            }

            this.tickDuration();
        }
    }
}
