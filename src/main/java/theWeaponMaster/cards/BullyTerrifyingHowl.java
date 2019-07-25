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

public class BullyTerrifyingHowl extends AbstractBullyCard {

    public static final String ID = DefaultMod.makeID(BullyTerrifyingHowl.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = makeCardPath("Skill.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheWeaponMaster.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int MAGIC_NUMBER = 15;
    private static final int UPGRADED_MAGIC_NUMBER = 5;
    private static final int BULLY_COST = 15;
    private static final int REDUCED_BULLY_COST = 3;

    public BullyTerrifyingHowl() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.bullyNumber = baseBullyNumber = BULLY_COST;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(-UPGRADED_MAGIC_NUMBER);
            reduceBullyCost(REDUCED_BULLY_COST);
            initializeDescription();
        }
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return new ViciousAction().viciousUse(p, this);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (monster.intent == AbstractMonster.Intent.ATTACK || monster.intent == AbstractMonster.Intent.ATTACK_BUFF || monster.intent == AbstractMonster.Intent.ATTACK_DEBUFF || monster.intent == AbstractMonster.Intent.ATTACK_DEFEND) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new IntimidatePower(monster, p)));
            }
        }
        p.getPower("ViciousPower").amount -= bullyNumber;
    }
}
