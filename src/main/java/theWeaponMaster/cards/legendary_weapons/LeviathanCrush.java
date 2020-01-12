package theWeaponMaster.cards.legendary_weapons;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.LeviathanChargeAction;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.patches.WeaponMasterTags;
import theWeaponMaster.powers.StaggerPower;
import theWeaponMaster.relics.ArsenalRelic;
import theWeaponMaster.relics.ShockwaveModulatorRelic;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class LeviathanCrush extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(LeviathanCrush.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static String UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    public static final String IMG = makeCardPath("Attack.png");

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 8;
    private static final int UPGRADED_DAMAGE = 2;
    private static final int MAGIC_NUMBER = 25;
    private static final int UPGRADED_MAGIC_NUMBER = 25;
    private static final int CHARGECOST = 1;

    public LeviathanCrush() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = baseDamage = DAMAGE;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.secondValue = baseSecondValue = ArsenalRelic.leviathanCharges;
        tags.add(WeaponMasterTags.LEVIATHAN);
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
        return AbstractDungeon.player.hasRelic(ShockwaveModulatorRelic.ID);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int armorPiercingDamage = 0;
        //TODO: Review the code and write something better.
        if (m.currentBlock > 0) {
            armorPiercingDamage = (int) (Math.min(m.currentBlock, damage) * ((double) magicNumber / 100));
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL)));
        if (armorPiercingDamage != 0 && ArsenalRelic.leviathanCharges >= CHARGECOST) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, (int) (armorPiercingDamage * 1.5), DamageInfo.DamageType.HP_LOSS)));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new StaggerPower(m, p, (int) (m.currentBlock * 1.5))));
            new LeviathanChargeAction(-CHARGECOST);
        } else if (armorPiercingDamage != 0) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, armorPiercingDamage, DamageInfo.DamageType.HP_LOSS)));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new StaggerPower(m, p, m.currentBlock)));
        }
    }
}
