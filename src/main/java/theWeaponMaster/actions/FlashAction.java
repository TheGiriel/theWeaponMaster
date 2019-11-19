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
import theWeaponMaster.patches.CenterGridCardSelectScreen;

import java.util.ArrayList;


public class FlashAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static int flashNumber = 0;
    private static CardGroup group;
    public static final String[] TEXT;
    ArrayList<AbstractCard> toDiscard = new ArrayList<>();
    private boolean pickCard = false;
    private AbstractPlayer p;

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAction");
        TEXT = uiStrings.TEXT;
    }

    public FlashAction(int amount) {
        this.amount = amount;
        actionType = ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_XFAST;
        flashNumber = 0;
    }

    public static int getFlashNumber() {
        return flashNumber;
    }

    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_XFAST) {
            pickCard = true;
            group = new CardGroup(CardGroup.CardGroupType.HAND);
            for (AbstractCard card : AbstractDungeon.player.hand.group) {
                group.addToTop(card);
            }

            CenterGridCardSelectScreen.centerGridSelect = true;
            AbstractDungeon.gridSelectScreen.open(group, this.amount, true, "Pick your cards");
        } else if (pickCard && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            pickCard = false;

            toDiscard = new ArrayList<>(AbstractDungeon.gridSelectScreen.selectedCards);
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            CenterGridCardSelectScreen.centerGridSelect = false;

            flashNumber = toDiscard.size();
            TheWeaponMaster.logger.info("Flash Number " + flashNumber);
            for (AbstractCard card : toDiscard) {
                AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(card));
            }
            isDone = true;
        }
        tickDuration();
    }


}
