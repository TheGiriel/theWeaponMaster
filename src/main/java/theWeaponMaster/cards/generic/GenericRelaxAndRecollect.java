package theWeaponMaster.cards.generic;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.RecollectAction;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.powers.ViciousPower;
import theWeaponMaster.util.FlipCard;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class GenericRelaxAndRecollect extends AbstractDynamicCard implements FlipCard {

    public static final String ID = TheWeaponMaster.makeID(GenericRelaxAndRecollect.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] DESCRIPTIONS = cardStrings.EXTENDED_DESCRIPTION;
    public static final CardRarity RARITY = CardRarity.COMMON;
    public static final CardTarget TARGET = CardTarget.SELF;
    public static final CardType TYPE = CardType.SKILL;

    public static final int COST = 1;
    private static final int BLOCK = 3;
    private static final int UPGRADED_BLOCK = 2;
    public static final int MAGIC_NUMBER = 2;
    public static final int UPGRADED_MAGIC_NUMBER = 1;
    private static final int SECOND_VALUE = 1;
    private static final int UPGRADED_SECOND_VALUE = 1;

    private boolean flipped = false;
    private boolean initializeCard = false;

    public GenericRelaxAndRecollect() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.secondValue = baseSecondValue = SECOND_VALUE;
        this.block = baseBlock = BLOCK;

        initializeDescription();
    }


    @Override
    public void atTurnStart() {
        if (!initializeCard) {
            initializeCard = true;
            flipCard();
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADED_BLOCK);
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            upgradeSecondValue(UPGRADED_SECOND_VALUE);
            initializeDescription();
        }
    }

    @Override
    public boolean cardPlayable(AbstractMonster m) {
        boolean canUse = AbstractDungeon.player.discardPile.size() >= magicNumber;
        if (!flipped) {
            return true;
        } else if (!canUse) {
            cantUseMessage = "There aren't enough cards to exhaust for this.";
            return false;
        } else return true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (flipped) {
            flipUse(p, m);
        } else {
            standardUse(p, m);
        }
    }

    @Override
    public void standardUse(AbstractPlayer p, AbstractMonster m) {
        int viciousBonus = 0;

        if (p.hasPower(ViciousPower.POWER_ID)) {
            viciousBonus = p.getPower(ViciousPower.POWER_ID).amount / 2;
        }
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        if (p.hasPower(ViciousPower.POWER_ID) && p.getPower(ViciousPower.POWER_ID).amount >= 2) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, viciousBonus));
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, ViciousPower.POWER_ID, viciousBonus));
        }
        flipCard();
    }

    @Override
    public void flipUse(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
        AbstractDungeon.actionManager.addToBottom(new RecollectAction(magicNumber));
        flipCard();
    }

    @Override
    public void flipCard() {
        if (!flipped) {
            this.name = DESCRIPTIONS[2];
            rawDescription = DESCRIPTIONS[3];
            this.cost = 0;
            this.secondValue = baseSecondValue = SECOND_VALUE;
            flipped = true;
        } else {
            this.name = DESCRIPTIONS[0];
            rawDescription = DESCRIPTIONS[1];
            this.cost = COST;
            this.secondValue = baseSecondValue = AbstractDungeon.player.getPower(ViciousPower.POWER_ID).amount / 2;
            flipped = false;
        }
        initializeDescription();
    }
}
