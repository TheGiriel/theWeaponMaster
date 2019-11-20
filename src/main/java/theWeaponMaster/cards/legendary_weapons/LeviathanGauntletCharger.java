package theWeaponMaster.cards.legendary_weapons;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.LeviathanChargeAction;
import theWeaponMaster.actions.OctopusAction;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.relics.ArsenalRelic;
import theWeaponMaster.util.FlipCard;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class LeviathanGauntletCharger extends AbstractDynamicCard implements FlipCard {

    public static final String ID = TheWeaponMaster.makeID(LeviathanGauntletCharger.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    public static final String IMG = makeCardPath("Attack.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 5;
    private static final int UPGRADED_DAMAGE = 2;
    private static final int BLOCK = 5;
    private static final int UPGRADED_BLOCK = 3;

    public static boolean recharge = false;
    private static int publicDamage;

    public LeviathanGauntletCharger() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = publicDamage = baseDamage = DAMAGE;
        this.block = baseBlock = BLOCK;
        this.defaultSecondMagicNumber = defaultBaseSecondMagicNumber = ArsenalRelic.leviathanCharges;

        if (ArsenalRelic.leviathanCharges == 0) {
            recharge = true;
        }
    }

    public static int getDamage() {
        return publicDamage;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADED_DAMAGE);
            upgradeBlock(UPGRADED_BLOCK);
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //TODO: fix this commented line.
        new OctopusAction().leviathanCharge();
        if (recharge) {
            flipUse(p, m);
        } else {
            standardUse(p, m);
        }
    }

    @Override
    public void flipCard() {
        if (recharge) {
            this.cost = 0;
        } else {
            this.cost = COST;
        }
        initializeDescription();
    }

    @Override
    public void standardUse(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < ArsenalRelic.leviathanCharges; i++) {
            AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(new DamageInfo(m, this.damage), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
        recharge = true;
        this.cost = 0;
        new LeviathanChargeAction(-ArsenalRelic.leviathanCharges);
        ArsenalRelic.leviathanCharges = 0;
    }

    @Override
    public void flipUse(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block * (3 - ArsenalRelic.leviathanCharges)));
        new LeviathanChargeAction(3 - ArsenalRelic.leviathanCharges);
        this.cost = COST;
        recharge = false;
    }
}
