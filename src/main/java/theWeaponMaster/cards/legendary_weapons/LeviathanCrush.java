package theWeaponMaster.cards.legendary_weapons;

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

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 8;
    private static final int UPGRADED_DAMAGE = 2;
    private static int MAGIC_NUMBER = 25;
    private static int UPGRADED_MAGIC_NUMBER = 25;
    private final int CHARGECOST = 1;

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
        int armorCrush = 0;
        //TODO: Review the code and write something better.
        if (m.currentBlock > 0) {
            if (this.damage > m.currentBlock) {
                armorCrush = (int) Math.ceil((this.damage - m.currentBlock) * (magicNumber / 100));
            } else {
                armorCrush = (int) Math.ceil((this.damage) * (magicNumber / 100));
            }
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL)));
        if (armorCrush != 0 && ArsenalRelic.leviathanCharges >= CHARGECOST) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, armorCrush * 2, DamageInfo.DamageType.HP_LOSS)));
            new LeviathanChargeAction(-CHARGECOST);
        } else if (armorCrush != 0) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, armorCrush, DamageInfo.DamageType.HP_LOSS)));
        }
    }
}
