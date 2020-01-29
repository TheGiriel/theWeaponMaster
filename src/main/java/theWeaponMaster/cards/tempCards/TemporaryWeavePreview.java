package theWeaponMaster.cards.tempCards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.EnemyForceAction;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.cards.martialarts.MartialBob;
import theWeaponMaster.patches.WeaponMasterTags;

import java.util.HashSet;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class TemporaryWeavePreview extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(TemporaryWeavePreview.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(TemporaryWeavePreview.class.getSimpleName());
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final CardRarity RARITY = CardRarity.SPECIAL;
    public static final CardTarget TARGET = CardTarget.SELF;
    public static final CardType TYPE = CardType.SKILL;
    public static final int COST = 1;
    public static final int UPGRADED_COST = 1;

    public static final int BLOCK = MartialBob.BLOCK - 2;
    public static final int UPGRADED_BLOCK = MartialBob.UPGRADED_BLOCK;
    public static final int MAGIC_NUMBER = 2;
    public static final int UPGRADED_MAGIC_NUMBER = 1;

    public static HashSet<AbstractMonster.Intent> intents;

    public TemporaryWeavePreview() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.block = baseBlock = BLOCK;

        tags.add(WeaponMasterTags.INTIMIDATE);
        tags.add(WeaponMasterTags.MARTIAL);

        intents = EnemyForceAction.getIntents(this);

        initializeDescription();
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            upgradeBlock(UPGRADED_BLOCK);
            initializeDescription();
        }
    }
}
