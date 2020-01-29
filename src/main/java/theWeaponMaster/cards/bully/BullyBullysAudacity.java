package theWeaponMaster.cards.bully;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.abstractcards.AbstractBullyCard;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;
import static theWeaponMaster.patches.WeaponMasterTags.BULLY;

public class BullyBullysAudacity extends AbstractBullyCard {

    public static final String ID = TheWeaponMaster.makeID(BullyBullysAudacity.class.getSimpleName());
    public static final String IMG = makeCardPath("Power.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final CardTarget TARGET = CardTarget.SELF;
    public static final CardType TYPE = CardType.SKILL;
    public static final CardRarity RARITY = CardRarity.UNCOMMON;

    public static final int COST = 2;
    public static final int BULLY_COST = 3;
    public static final int UPGRADED_BULLY_COST = 2;
    public static final int MAGIC_NUMBER = 2;
    public static final int UPGRADED_MAGIC_NUMBER = 1;

    public BullyBullysAudacity() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;

        tags.add(BULLY);

        bullyNumber = baseBullyNumber = BULLY_COST;
        ExhaustiveField.ExhaustiveFields.exhaustive.set(this, 2);
        ExhaustiveField.ExhaustiveFields.baseExhaustive.set(this, 2);

    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int block = (int) AbstractDungeon.actionManager.cardsPlayedThisCombat.stream().filter(card -> card.hasTag(BULLY)).count();
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block * magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            upgradeBullyNumber(UPGRADED_BULLY_COST);
            this.rawDescription = DESCRIPTION;
            initializeDescription();
        }
    }
}