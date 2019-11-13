package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theWeaponMaster.relics.RevolverRelic;

import static theWeaponMaster.patches.WeaponMasterTags.REVOLVER;

public class ReloadAction extends AbstractGameAction {


    public ReloadAction() {
        if (RevolverRelic.getShotsLeft() <= 0) {
            for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
                if (card.hasTag(REVOLVER)) {
                    AbstractDungeon.player.drawPile.addToBottom(card);
                }
            }
            AbstractDungeon.player.drawPile.shuffle();
            RevolverRelic.setShotsLeft(RevolverRelic.SHOTS);
            AbstractDungeon.player.draw(1);
            return;
        }
    }

    @Override
    public void update() {
    }
}
