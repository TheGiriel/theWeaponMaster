package theWeaponMaster.cards.bully;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.EnemyForceAction;
import theWeaponMaster.cards.abstractcards.AbstractBullyCard;
import theWeaponMaster.powers.IntimidatePower;

import java.util.HashSet;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;
import static theWeaponMaster.patches.WeaponMasterTags.BULLY;
import static theWeaponMaster.patches.WeaponMasterTags.INTIMIDATE;

public class BullySuckerPunch extends AbstractBullyCard {

    public static final String ID = TheWeaponMaster.makeID(BullySuckerPunch.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final CardRarity RARITY = CardRarity.RARE;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final CardType TYPE = CardType.ATTACK;

    public static final int COST = 0;
    public static final int DAMAGE = 6;
    public static final int UPGRADED_DAMAGE = 2;
    public static final int BULLY_NUMBER = 3;
    public static final int UPGRADED_BULLY_NUMBER = 2;
    public static final int SECOND_VALUE = 4;
    public static final int UPGRADED_SECOND_VALUE = 4;
    private final HashSet<AbstractMonster.Intent> intents;

    public BullySuckerPunch() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.damage = baseDamage = DAMAGE;
        this.bullyNumber = baseBullyNumber = BULLY_NUMBER;

        tags.add(BULLY);
        tags.add(INTIMIDATE);
        intents = EnemyForceAction.getIntents(this);
        if (upgraded) {
            isInnate = true;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.actionManager.cardsPlayedThisCombat.size() <= 1) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage * 2, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            if (intents.contains(m.intent) && !m.hasPower(IntimidatePower.POWER_ID)) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new IntimidatePower(m, p)));
            }
        } else {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADED_DAMAGE);
            upgradeBullyNumber(UPGRADED_BULLY_NUMBER);
            isInnate = true;
            initializeDescription();
        }
    }
}
