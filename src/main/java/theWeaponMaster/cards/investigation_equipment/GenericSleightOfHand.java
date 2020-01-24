package theWeaponMaster.cards.investigation_equipment;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.SleightOfHandAction;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class GenericSleightOfHand extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(GenericSleightOfHand.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final CardRarity RARITY = CardRarity.COMMON;
    public static final CardTarget TARGET = CardTarget.SELF;
    public static final CardType TYPE = CardType.SKILL;
    public static final int COST = 1;
    public static final int MAGIC_NUMBER = 2;
    public static final int SECOND_VALUE = 1;
    public static final int UPGRADED_MAGIC_NUMBER = 1;
    public static final int UPGRADED_SECOND_VALUE = 1;

    public GenericSleightOfHand() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.secondValue = baseSecondValue = SECOND_VALUE;

        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 2));
        AbstractDungeon.actionManager.addToBottom(new DiscardAction(p, p, 1, false));
        AbstractDungeon.actionManager.addToBottom(new SleightOfHandAction());
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            upgradeSecondValue(UPGRADED_SECOND_VALUE);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}