package theWeaponMaster.cards.legendary_weapons;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.RevenantStarveAction;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.powers.ViciousPower;
import theWeaponMaster.relics.ArsenalRelic;
import theWeaponMaster.relics.GhoulskinSheathRelic;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;
import static theWeaponMaster.patches.WeaponMasterTags.REVENANT;
import static theWeaponMaster.powers.ViciousPower.TIER_TWO;

public class RevenantBloodbath extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(RevenantBloodbath.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = -1;
    private static final int DAMAGE = 7;
    private static final int UPGRADED_DAMAGE = 3;
    private static final int MAGIC_NUMBER = 2;
    private static final int UPGRADED_MAGIC_NUMBER = 1;
    private final int HUNGERCOST = 10;

    public RevenantBloodbath() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.damage = baseDamage = DAMAGE;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.defaultSecondMagicNumber = defaultBaseSecondMagicNumber = TIER_TWO;

        getSated();
        tags.add(REVENANT);
        initializeDescription();
        AlwaysRetainField.alwaysRetain.set(this, true);
    }

    @Override
    public void initializeDescription() {
        getSated();
        super.initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADED_DAMAGE);
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            initializeDescription();
        }
    }

    public void getSated() {
        if (ArsenalRelic.revenantHunger < HUNGERCOST) {
            rawDescription = DESCRIPTION[1];
        } else {
            rawDescription = DESCRIPTION[0];
        }
    }

    public boolean canUpgrade() {
        return AbstractDungeon.player.hasRelic(GhoulskinSheathRelic.ID);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int bloodbath = TIER_TWO;
        int initialDamageBonus = p.getPower(ViciousPower.POWER_ID).amount / 5;
        int totalAttacks = EnergyPanel.totalCount;
        EnergyPanel.useEnergy(totalAttacks);
        if (p.hasRelic(ChemicalX.ID)) {
            totalAttacks += 2;
            p.getRelic(ChemicalX.ID).flash();
        }
        if (ArsenalRelic.revenantHunger >= HUNGERCOST) {
            p.getPower(ViciousPower.POWER_ID).amount += (this.magicNumber * totalAttacks);
            new RevenantStarveAction(-HUNGERCOST, false);
        } else {
            new RevenantStarveAction(0, true);
        }
        int j = 0;
        do {
            int finalDamageBonus = p.getPower(ViciousPower.POWER_ID).amount / (bloodbath);
            j++;
            AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(new DamageInfo(p, (damage - initialDamageBonus + finalDamageBonus), DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.33F));
            p.getPower(ViciousPower.POWER_ID).reducePower(bloodbath);
        } while (p.getPower(ViciousPower.POWER_ID).amount >= (bloodbath));
    }
}
