package theWeaponMaster.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.characters.TheWeaponMaster;
import theWeaponMaster.powers.TauntPower;

import static theWeaponMaster.DefaultMod.makeCardPath;

public class BullyMeanToEveryone extends AbstractBullyCard {

    public static final String ID = DefaultMod.makeID(BullyMeanToEveryone.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheWeaponMaster.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int MAGIC_NUMBER = 10;
    private static final int UPGRADED_MAGIC_NUMBER = 3;
    private static final int BULLY_COST = 15;
    private static final int REDUCED_BULLY_COST = 3;

    public BullyMeanToEveryone() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        isInnate = true;
        exhaust = true;
        this.bullyNumber = baseBullyNumber = BULLY_COST;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, "ViciousPower", 3));
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (monster.intent != AbstractMonster.Intent.ATTACK && monster.intent != AbstractMonster.Intent.ATTACK_BUFF && monster.intent != AbstractMonster.Intent.ATTACK_DEBUFF && monster.intent != AbstractMonster.Intent.ATTACK_DEFEND) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new TauntPower(monster, p)));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new StrengthPower(monster, magicNumber)));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber)));
            }
        }
        p.getPower("ViciousPower").amount -= bullyNumber;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(-UPGRADED_MAGIC_NUMBER);
            reduceBullyCost(REDUCED_BULLY_COST);
            retain = true;
            initializeDescription();
        }
    }
}