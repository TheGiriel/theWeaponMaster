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
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.powers.ViciousPower;
import theWeaponMaster.relics.ArsenalRelic;
import theWeaponMaster.relics.GhoulskinSheathRelic;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;
import static theWeaponMaster.patches.WeaponMasterTags.REVENANT;

public class RevenantBloodbath extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(RevenantBloodbath.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = -1;
    private static final int DAMAGE = 7;
    private static final int UPGRADED_DAMAGE = 3;
    private static final int MAGIC_NUMBER = 2;
    private static final int UPGRADED_MAGIC_NUMBER = 1;

    public RevenantBloodbath() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.damage = baseDamage = DAMAGE;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;

        AlwaysRetainField.alwaysRetain.set(this, true);
        tags.add(REVENANT);
        initializeDescription();
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

    public boolean canUpgrade() {
        return AbstractDungeon.player.hasRelic(GhoulskinSheathRelic.ID);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return p.hasPower(ViciousPower.POWER_ID) && p.getPower(ViciousPower.POWER_ID).amount >= ViciousPower.TIER_THREE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int bloodbath = ViciousPower.TIER_TWO;
        int initialDamageBonus = p.getPower(ViciousPower.POWER_ID).amount / 5;
        int totalAttacks = EnergyPanel.totalCount;
        EnergyPanel.useEnergy(totalAttacks);
        if (p.hasRelic(ChemicalX.ID)) {
            totalAttacks += 2;
            p.getRelic(ChemicalX.ID).flash();
        }
        int hungerBonus = 0;
        if (ArsenalRelic.revenantHunger >= 5) {
            hungerBonus = 1;
        }
        p.getPower(ViciousPower.POWER_ID).amount += (this.magicNumber * totalAttacks);
        int j = 0;
        do {
            int finalDamageBonus = p.getPower(ViciousPower.POWER_ID).amount / (bloodbath - hungerBonus);
            j++;
            AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(new DamageInfo(p, (damage - initialDamageBonus + finalDamageBonus), DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.33F));
            p.getPower(ViciousPower.POWER_ID).reducePower(bloodbath - hungerBonus);
        } while (p.getPower(ViciousPower.POWER_ID).amount >= (bloodbath - hungerBonus));
    }
}
