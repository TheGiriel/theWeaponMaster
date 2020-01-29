package theWeaponMaster.cards.trickster;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.cards.tempCards.TemporaryRecollect;
import theWeaponMaster.cards.tempCards.TemporaryRecollectPreview;
import theWeaponMaster.powers.ViciousPower;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class GenericRelax extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(GenericRelax.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] DESCRIPTIONS = cardStrings.EXTENDED_DESCRIPTION;

    public static final CardRarity RARITY = CardRarity.COMMON;
    public static final CardTarget TARGET = CardTarget.SELF;
    public static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    public static final int COST = 1;
    public static final int BLOCK = 3;
    public static final int UPGRADED_BLOCK = 2;
    public static final int MAGIC_NUMBER = 2;
    public static final int UPGRADED_MAGIC_NUMBER = 1;
    public static final int SECOND_VALUE = 1;
    public static final int UPGRADED_SECOND_VALUE = 1;


    public GenericRelax() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.secondValue = baseSecondValue = SECOND_VALUE;
        this.block = baseBlock = BLOCK;

        this.cardsToPreview = new TemporaryRecollectPreview();
        purgeOnUse = true;
        initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADED_BLOCK);
            cardsToPreview.upgrade();
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            upgradeSecondValue(UPGRADED_SECOND_VALUE);
            initializeDescription();
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int viciousBonus = 0;

        if (p.hasPower(ViciousPower.POWER_ID) && p.getPower(ViciousPower.POWER_ID).amount >= 2) {
            viciousBonus = p.getPower(ViciousPower.POWER_ID).amount / 2;
        }
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        if (viciousBonus != 0) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, viciousBonus));
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, ViciousPower.POWER_ID, viciousBonus));
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new TemporaryRecollect(), 1));
    }

    @Override
    public void onMoveToDiscard() {
        super.onMoveToDiscard();
    }
}
