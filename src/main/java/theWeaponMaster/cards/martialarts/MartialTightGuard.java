package theWeaponMaster.cards.martialarts;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.patches.WeaponMasterTags;
import theWeaponMaster.powers.RedirectingPower;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class MartialTightGuard extends AbstractDynamicCard {


    public static final String ID = TheWeaponMaster.makeID(MartialTightGuard.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(MartialTightGuard.class.getSimpleName());
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    private static final int BLOCK = 7;
    private static final int UPGRADED_BLOCK = 3;
    private static final int MAGIC_NUMBER = 2;
    private static final int UPGRADED_MAGIC_NUMBER = 1;


    public MartialTightGuard() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.block = baseBlock = BLOCK;

        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;

        tags.add(WeaponMasterTags.MARTIAL);

        initializeDescription();
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RedirectingPower(p, magicNumber)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            upgradeBlock(UPGRADED_BLOCK);
            initializeDescription();
        }
    }
}
