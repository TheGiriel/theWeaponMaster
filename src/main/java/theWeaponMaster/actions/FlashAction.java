package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.BiConsumer;


public class FlashAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private boolean pickCard = false;
    private AbstractPlayer p = AbstractDungeon.player;
    private final Object state;
    private BiConsumer<Object, ArrayList<AbstractCard>> onDiscard;
    private boolean anyNumber;
    private AbstractCard card;

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAction");
        TEXT = uiStrings.TEXT;
    }


    public FlashAction(AbstractCard card, int amount, Object state, BiConsumer<Object, ArrayList<AbstractCard>> discarded) {
        this(card, amount, state, discarded, true);
    }


    public FlashAction(AbstractCard card, int amount, Object state, BiConsumer<Object, ArrayList<AbstractCard>> discarded, boolean anyNumber) {
        this.card = card;
        this.state = state;
        this.onDiscard = discarded;
        this.amount = amount;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.DISCARD;
        this.anyNumber = anyNumber;
    }


    @Override
    public void update() {
        ArrayList<AbstractCard> discarded = new ArrayList<>();
        discarded.add(card);
        if (duration == Settings.ACTION_DUR_XFAST) {
            pickCard = true;

            AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, true, true);
        } else if (pickCard && !AbstractDungeon.handCardSelectScreen.selectedCards.isEmpty()) {
            pickCard = false;
            AbstractCard c;
            Iterator var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();
            while (var1.hasNext()) {
                c = (AbstractCard) var1.next();
                p.hand.moveToDiscardPile(c);
                c.triggerOnManualDiscard();
                discarded.add(c);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            this.onDiscard.accept(this.state, discarded);
            isDone = true;
        } else {
            pickCard = false;
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            this.onDiscard.accept(this.state, discarded);
            isDone = true;
        }
        tickDuration();

    }

}
