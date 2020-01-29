package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class SleightOfHandAction extends AbstractGameAction {

    private AbstractPlayer p = AbstractDungeon.player;

    public SleightOfHandAction() {
        duration = Settings.ACTION_DUR_XFAST;
        actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_XFAST) {
            if (this.p.discardPile.isEmpty()) {
                this.isDone = true;
                return;
            }

            if (this.p.discardPile.size() == 1) {
                AbstractCard tmp = this.p.discardPile.getTopCard();
                this.p.discardPile.moveToDeck(tmp, true);
            }

            if (this.p.discardPile.group.size() > this.amount) {
                AbstractDungeon.gridSelectScreen.open(this.p.discardPile, 1, "Return a card.", false, false, false, false);
                this.tickDuration();
                return;
            }
        } else if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            Iterator var3 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

            while (var3.hasNext()) {
                AbstractCard c = (AbstractCard) var3.next();
                this.p.discardPile.moveToHand(c, p.discardPile);
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.player.hand.refreshHandLayout();
        }
        isDone = true;
    }
}
