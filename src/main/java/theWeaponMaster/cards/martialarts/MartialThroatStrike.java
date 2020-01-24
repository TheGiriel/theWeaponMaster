package theWeaponMaster.cards.martialarts;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ChokePower;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.patches.WeaponMasterTags;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class MartialThroatStrike extends AbstractDynamicCard {


    public static final String ID = TheWeaponMaster.makeID(MartialThroatStrike.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(MartialThroatStrike.class.getSimpleName());
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int BLOCK = 8;
    private static final int UPGRADED_BLOCK = 2;
    private static final int MAGIC_NUMBER = 3;
    private static final int UPGRADED_MAGIC_NUMBER = 1;

    public MartialThroatStrike() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.block = baseBlock = BLOCK;

        tags.add(WeaponMasterTags.MARTIAL);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new ChokePower(m, magicNumber)));
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
