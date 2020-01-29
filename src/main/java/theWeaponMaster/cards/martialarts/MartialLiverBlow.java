package theWeaponMaster.cards.martialarts;

import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.patches.WeaponMasterTags;
import theWeaponMaster.powers.MalicePower;
import theWeaponMaster.powers.StaggerPower;

import java.util.HashSet;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class MartialLiverBlow extends AbstractDynamicCard {


    public static final String ID = TheWeaponMaster.makeID(MartialLiverBlow.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(MartialLiverBlow.class.getSimpleName());
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final CardRarity RARITY = CardRarity.RARE;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final CardType TYPE = CardType.ATTACK;
    public static final int COST = 1;
    public static final int UPGRADED_COST = 0;

    public static final int DAMAGE = 8;
    public static final int UPGRADED_DAMAGE = 2;
    public static final int MAGIC_NUMBER = 2;
    public static final int UPGRADED_MAGIC_NUMBER = 1;

    public static HashSet<String> debilitatePowers = new HashSet<>();

    public MartialLiverBlow() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = baseDamage = DAMAGE;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;

        debilitatePowers.add(VulnerablePower.POWER_ID);
        debilitatePowers.add(StaggerPower.POWER_ID);

        tags.add(WeaponMasterTags.MARTIAL);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        if (m.hasPower(VulnerablePower.POWER_ID) || m.hasPower(StaggerPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new StunMonsterAction(m, p));
            for (AbstractPower power : m.powers) {
                if (debilitatePowers.contains(power.ID)) {
                    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, m, power));
                }
            }
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MalicePower(p, magicNumber)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            upgradeDamage(UPGRADED_DAMAGE);
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            initializeDescription();
        }
    }
}
