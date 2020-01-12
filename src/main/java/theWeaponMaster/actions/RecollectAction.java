package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class RecollectAction extends AbstractGameAction {

    AbstractPlayer p = AbstractDungeon.player;
    private int exhaustNumber;

    public RecollectAction(int magicNumber) {
        duration = Settings.ACTION_DUR_XFAST;
        actionType = ActionType.WAIT;
        exhaustNumber = magicNumber;
    }

    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_XFAST) {
            if (this.p.discardPile.isEmpty()) {
                this.isDone = true;
                return;
            }

            if (this.p.discardPile.size() < exhaustNumber) {
                this.isDone = true;
                return;
            }

            if (this.p.discardPile.group.size() >= exhaustNumber) {
                AbstractDungeon.gridSelectScreen.open(this.p.discardPile, exhaustNumber, "Exhaust cards", false, false, true, false);
                this.tickDuration();
                return;
            }
        } else if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            Iterator var3 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

            while (var3.hasNext()) {
                AbstractCard c = (AbstractCard) var3.next();
                this.p.discardPile.removeCard(c);
                this.p.discardPile.moveToExhaustPile(c);
            }

            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.player.hand.refreshHandLayout();
        }
        isDone = true;
    }

}
