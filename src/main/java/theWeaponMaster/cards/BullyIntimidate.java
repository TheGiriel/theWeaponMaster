package theWeaponMaster.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.actions.ViciousAction;
import theWeaponMaster.characters.TheWeaponMaster;
import theWeaponMaster.powers.IntimidatePower;

import static theWeaponMaster.DefaultMod.makeCardPath;

public class BullyIntimidate extends AbstractBullyCard {

    public static final String ID = DefaultMod.makeID(BullyIntimidate.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = makeCardPath("Skill.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheWeaponMaster.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int MAGIC_NUMBER = 10;
    private static final int UPGRADED_MAGIC_NUMBER = 3;
    private static final int BULLY_COST = 5;

    public BullyIntimidate() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.bullyNumber = baseBullyNumber = BULLY_COST;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //TODO: Force the enemy to defend if they're attacking you.
        if (m.intent == AbstractMonster.Intent.ATTACK || m.intent == AbstractMonster.Intent.ATTACK_BUFF || m.intent == AbstractMonster.Intent.ATTACK_DEBUFF) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new IntimidatePower(m, p)));
        }
        p.getPower("ViciousPower").amount -= bullyNumber;
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return new ViciousAction().viciousUse(p, this);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            initializeDescription();
        }
    }
}