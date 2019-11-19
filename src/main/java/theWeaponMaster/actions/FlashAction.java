package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
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
    private AbstractPlayer p = AbstractDungeon.player;

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAction");
        TEXT = uiStrings.TEXT;
    }


    public FlashAction(int amount) {
        flashNumber = 0;
        this.amount = amount;
        actionType = ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_XFAST;
    }


    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_XFAST) {
            pickCard = true;

            AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, false, true, false, false, true);
        } else if (pickCard && !AbstractDungeon.handCardSelectScreen.selectedCards.isEmpty()) {
            pickCard = false;
            AbstractCard c;
            Iterator var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();
            while (var1.hasNext()) {
                c = (AbstractCard) var1.next();
                p.hand.moveToDiscardPile(c);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.clear();

            TheWeaponMaster.logger.info("Flash Number " + flashNumber);
            isDone = true;
        }
        tickDuration();

    }

}
