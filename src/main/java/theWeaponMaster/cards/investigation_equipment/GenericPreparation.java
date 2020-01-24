package theWeaponMaster.cards.investigation_equipment;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.powers.PreparationPower;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class GenericPreparation extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(GenericPreparation.class.getSimpleName());
    public static final String IMG = makeCardPath("Power.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final CardRarity RARITY = CardRarity.COMMON;
    public static final CardTarget TARGET = CardTarget.SELF;
    public static final CardType TYPE = CardType.SKILL;
    public static final int COST = 1;
    public static final int MAGIC = 1;
    public static final int UPGRADED_MAGIC = 1;
    public static final int BLOCK = 5;
    public static final int UPGRADED_BLOCK = 3;

    public GenericPreparation() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.magicNumber = baseMagicNumber = MAGIC;
        this.block = baseBlock = BLOCK;

        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, magicNumber)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PreparationPower(p, p, magicNumber)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_MAGIC);
            upgradeBlock(UPGRADED_BLOCK);
            initializeDescription();
        }
    }
}