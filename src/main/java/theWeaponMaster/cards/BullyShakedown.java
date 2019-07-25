package theWeaponMaster.cards;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.GainGoldTextEffect;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.actions.ViciousAction;
import theWeaponMaster.characters.TheWeaponMaster;

import java.util.Random;

import static theWeaponMaster.DefaultMod.makeCardPath;

public class BullyShakedown extends AbstractBullyCard {

    public static final String ID = DefaultMod.makeID(BullyShakedown.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheWeaponMaster.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 5;
    private static final int UPGRADED_DAMAGE = 2;
    private static final int MAGIC_NUMBER = 2;
    private static final int UPGRADED_MAGIC_NUMBER = 1;
    private static final int BULLY_COST = 8;
    private static final int REDUCED_BULLY_COST = 3;

    public BullyShakedown() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.damage = baseDamage = DAMAGE;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.bullyNumber = baseBullyNumber = BULLY_COST;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int stolenMoney = new Random().nextInt(3 * magicNumber) + 3 * magicNumber;
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), stolenMoney));
        AbstractDungeon.player.gainGold(stolenMoney);
        AbstractDungeon.effectList.add(new RainingGoldEffect(stolenMoney));
        AbstractDungeon.effectList.add(new GainGoldTextEffect(stolenMoney));
        p.getPower("ViciousPower").amount -= bullyNumber;
    }


    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return new ViciousAction().viciousUse(p, this);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            upgradeDamage(UPGRADED_DAMAGE);
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            reduceBullyCost(REDUCED_BULLY_COST);
            initializeDescription();
        }
    }
}
