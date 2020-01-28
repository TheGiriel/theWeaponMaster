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
import theWeaponMaster.cards.investigation_equipment.GenericRelax;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class TemporaryRecollect extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(TemporaryRecollect.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(TemporaryRecollect.class.getSimpleName());
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 0;
    private static final int MAGIC_NUMBER = 2;
    private static final int UPGRADED_MAGIC_NUMBER = 1;
    private static final int SECOND_VALUE = 1;
    private static final int UPGRADED_SECOND_VALUE = 1;

    public TemporaryRecollect() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.secondValue = baseSecondValue = SECOND_VALUE;

        if (AbstractDungeon.isPlayerInDungeon()) {
            this.cardsToPreview = new TemporaryRelaxPreview();
        }
        initializeDescription();
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
        AbstractDungeon.actionManager.addToBottom(new RecollectAction(magicNumber));
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
