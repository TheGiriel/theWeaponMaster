package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class FlashAction extends AbstractGameAction {
    public static final String[] TEXT;
    private static final UIStrings uiStrings;
    private static final float DURATION;

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAction");
        TEXT = uiStrings.TEXT;
        DURATION = Settings.ACTION_DUR_XFAST;
    }

    AbstractCard card;
    boolean anyNumber = true;
    boolean canPickZero = true;
    boolean pickCard = false;
    AbstractPlayer player;

    public FlashAction(AbstractCard card, AbstractPlayer player, AbstractMonster m, int discarded) {
        this.setValues(AbstractDungeon.player, AbstractDungeon.player, discarded);
        duration = Settings.ACTION_DUR_FAST;
        this.card = card;


    }

    @Override
    public void update() {
        AbstractCard c;
        if (this.duration == DURATION) {
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                this.isDone = true;
                return;
            }
        }
    }
}
