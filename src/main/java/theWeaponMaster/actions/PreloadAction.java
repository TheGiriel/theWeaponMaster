package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theWeaponMaster.patches.WeaponMasterTags;
import theWeaponMaster.relics.RevolverRelic;

public class PreloadAction extends AbstractGameAction {

    private static AbstractPlayer p = AbstractDungeon.player;
    CardGroup ammoSelection = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    public PreloadAction() {
        duration = Settings.ACTION_DUR_XFAST;

        for (AbstractCard card : p.hand.group) {
            if (card.hasTag(WeaponMasterTags.AMMUNITION)) {
                ammoSelection.addToTop(card);
            }
        }
    }

    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_XFAST) {
            if (ammoSelection.isEmpty()) {
                this.isDone = true;
                return;
            }

            //ammoSelection.clear();
            if (ammoSelection.size() >= 1) {
                AbstractDungeon.gridSelectScreen.open(ammoSelection, 1, "Purge one Ammo card:", false, false, true, true);
                this.tickDuration();
                return;
            }
        } else if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            RevolverRelic.preloaded = AbstractDungeon.gridSelectScreen.selectedCards.get(0).makeSameInstanceOf();

            p.hand.moveToExhaustPile(AbstractDungeon.gridSelectScreen.selectedCards.get(0));

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.player.hand.refreshHandLayout();
        }
        isDone = true;
    }
}
