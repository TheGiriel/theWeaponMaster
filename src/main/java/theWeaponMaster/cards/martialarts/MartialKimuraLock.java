package theWeaponMaster.cards.martialarts;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.patches.WeaponMasterTags;
import theWeaponMaster.powers.CrippledPower;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class MartialKimuraLock extends AbstractDynamicCard {


    public static final String ID = TheWeaponMaster.makeID(MartialKimuraLock.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(MartialKimuraLock.class.getSimpleName());
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF_AND_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int BLOCK = 0;
    private static final int UPGRADED_BLOCK = 6;
    private static final int MAGIC_NUMBER = 4;
    private static final int UPGRADED_MAGIC_NUMBER = -1;

    public MartialKimuraLock() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.block = baseBlock = BLOCK;

        tags.add(WeaponMasterTags.MARTIAL);
    }

    @Override
    public boolean cardPlayable(AbstractMonster m) {
        try {
            if (m.currentBlock > 0) {
                return true;
            }
        } catch (NullPointerException e) {

        }
        cantUseMessage = "This one has no Block.";
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int kimuraThreshold = Math.min(p.currentBlock + block, m.currentBlock);
        if (m.currentBlock > 0) {
            if (upgraded) {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
            }
            AbstractDungeon.actionManager.addToBottom(new LoseBlockAction(m, p, kimuraThreshold));
            if (kimuraThreshold >= m.currentBlock) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new CrippledPower(m)));
            }
        }
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
