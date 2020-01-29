package theWeaponMaster.cards.tempCards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.RecollectAction;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.cards.trickster.GenericRelax;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class TemporaryRecollect extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(TemporaryRecollect.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(TemporaryRecollect.class.getSimpleName());
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final CardRarity RARITY = CardRarity.SPECIAL;
    public static final CardTarget TARGET = CardTarget.SELF;
    public static final CardType TYPE = CardType.SKILL;
    public static final int COST = 0;
    public static final int MAGIC_NUMBER = 2;
    public static final int UPGRADED_MAGIC_NUMBER = 1;
    public static final int SECOND_VALUE = 1;
    public static final int UPGRADED_SECOND_VALUE = 1;

    public TemporaryRecollect() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.secondValue = baseSecondValue = SECOND_VALUE;

        if (AbstractDungeon.isPlayerInDungeon()) {
            this.cardsToPreview = new TemporaryRelaxPreview();
        }

        purgeOnUse = true;
        initializeDescription();
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new RecollectAction(magicNumber));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new GenericRelax(), 1));
    }

    @Override
    public boolean cardPlayable(AbstractMonster m) {
        boolean canUse = AbstractDungeon.player.discardPile.size() >= magicNumber;
        if (!canUse) {
            cantUseMessage = "There aren't enough cards in my discard pile.";
            return false;
        } else return true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            upgradeSecondValue(UPGRADED_SECOND_VALUE);
            if (AbstractDungeon.isPlayerInDungeon()) {
                cardsToPreview.upgrade();
            }
            initializeDescription();
        }
    }
}
