package theWeaponMaster.cards.legendary_weapons;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.LeviathanGauntletAction;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.relics.ArsenalRelic;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class LeviathanGauntletCharger extends AbstractDynamicCard /*implements FlipCard*/ {

    public static final String ID = TheWeaponMaster.makeID(LeviathanGauntletCharger.class.getSimpleName());
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    public static final String IMG = makeCardPath("Attack.png");

    public static final CardRarity RARITY = CardRarity.SPECIAL;
    public static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    public static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    public static final int COST = 1;
    public static final int DAMAGE = 4;
    public static final int UPGRADED_DAMAGE = 2;
    private static final int BLOCK = 4;
    private static final int UPGRADED_BLOCK = 2;

    public static boolean recharge = false;
    private static int publicDamage;
    private static int publicBlock;

    public LeviathanGauntletCharger() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = publicDamage = baseDamage = DAMAGE;
        this.block = publicBlock = baseBlock = BLOCK;
        this.secondValue = baseSecondValue = ArsenalRelic.leviathanCharges;

        /*if (ArsenalRelic.leviathanCharges == 0) {
            recharge = true;
        }*/
    }

    public static int getPublicDamage() {
        return publicDamage;
    }

    public static int getPublicBlock() {
        return publicBlock;
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
        AbstractDungeon.actionManager.addToBottom(new LeviathanGauntletAction());
        //new OctopusAction().leviathanCharge();
        /*if (recharge) {
            flipUse(p, m);
        } else {
            standardUse(p, m);
        }*/
    }
/*
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
        AbstractDungeon.actionManager.addToBottom(new LeviathanChargeAction(-ArsenalRelic.leviathanCharges, this.damage ,true));
        ArsenalRelic.leviathanCharges = 0;
    }

    @Override
    public void flipUse(AbstractPlayer p, AbstractMonster m) {
        //AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block * (3 - ArsenalRelic.leviathanCharges)));
        AbstractDungeon.actionManager.addToBottom(new LeviathanChargeAction(3 - ArsenalRelic.leviathanCharges, this.block, false));
        this.cost = COST;
        recharge = false;
    }*/
}
