package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.abstractcards.AbstractDefaultCard;

import java.util.ArrayList;
import java.util.Iterator;


public class FlashAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static int flashNumber = 0;
    private static CardGroup group;
    public static final String[] TEXT;
    private static AbstractDefaultCard card;
    private static ArrayList<AbstractCard> discardList = new ArrayList<>();
    private boolean pickCard = false;
    private AbstractPlayer p;

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAction");
        TEXT = uiStrings.TEXT;
    }


    public FlashAction(String cardID, int amount) {
        flashNumber = 0;
        this.amount = amount;
        actionType = ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_XFAST;
    }


    @Override
    public void update() {

        //via CenterGridCardSelectScreen
        /*if (duration == Settings.ACTION_DUR_XFAST) {
            pickCard = true;
            group = new CardGroup(CardGroup.CardGroupType.HAND);
            for (AbstractCard card : AbstractDungeon.player.hand.group) {
                group.addToTop(card);
            }

            CenterGridCardSelectScreen.centerGridSelect = true;
            AbstractDungeon.gridSelectScreen.open(group, this.amount, true, "Pick your cards");

        } else if (pickCard && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            pickCard = false;

            ArrayList<AbstractCard> toDiscard = new ArrayList<>(AbstractDungeon.gridSelectScreen.selectedCards);
            toDiscard.addAll(AbstractDungeon.gridSelectScreen.selectedCards);
            for (AbstractCard card : toDiscard){
                AbstractDungeon.actionManager.addToBottom(new DiscardSpecificCardAction(card));
            }
            flashNumber = toDiscard.size();
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            CenterGridCardSelectScreen.centerGridSelect = false;

            TheWeaponMaster.logger.info("Flash Number " + flashNumber);
            isDone = true;
        }*/

        //Via AbstractDungeon.handCardSelectScreen.open
        if (duration == Settings.ACTION_DUR_XFAST) {
            pickCard = true;

            AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, false, true, false, false, true);
        } else if (pickCard && !AbstractDungeon.handCardSelectScreen.selectedCards.isEmpty()) {
            pickCard = false;
            AbstractCard c;
            for (Iterator var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator(); var1.hasNext(); AbstractDungeon.player.hand.addToTop(c)) {
                c = (AbstractCard) var1.next();
                AbstractDungeon.actionManager.addToBottom(new DiscardSpecificCardAction(c, AbstractDungeon.handCardSelectScreen.selectedCards));
            }
            AbstractDungeon.handCardSelectScreen.selectedCards.clear();

            TheWeaponMaster.logger.info("Flash Number " + flashNumber);
            isDone = true;
        }
        tickDuration();

    }

    public void chargeFlash() {

    }
}
