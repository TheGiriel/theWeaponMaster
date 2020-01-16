package theWeaponMaster.cards.generic;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class GenericGuillotineChoke extends AbstractDynamicCard {


    public static final String ID = TheWeaponMaster.makeID(GenericGuillotineChoke.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final CardRarity RARITY = CardRarity.UNCOMMON;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final CardType TYPE = CardType.SKILL;
    public static final int COST = 0;
    public static final int UPGRADED_COST = 0;
    public static final int MAGIC_NUMBER = 1;
    public static final int BLOCK = 6;
    public static final int UPGRADED_BLOCK = 3;
    public static final int SECOND_VALUE = 2;
    public static final int UPGRADED_SECOND_VALUE = 1;

    public GenericGuillotineChoke() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.block = baseBlock = BLOCK;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.secondValue = baseSecondValue = SECOND_VALUE;

        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, secondValue, false)));
        //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new ManaBurnPower(m, p, magicNumber)));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            upgradeSecondValue(UPGRADED_SECOND_VALUE);
            upgradeBlock(UPGRADED_BLOCK);
            initializeDescription();
        }
    }
}
