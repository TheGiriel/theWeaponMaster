package theWeaponMaster.cards.legendary_weapons;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.ShakeScreenAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.LeviathanChargeAction;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.relics.ArsenalRelic;
import theWeaponMaster.relics.ShockwaveModulatorRelic;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class LeviathanEarthquake extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(LeviathanEarthquake.class.getSimpleName());
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    public static final String IMG = makeCardPath("Attack.png");

    public static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.SPECIAL;
    public static final AbstractCard.CardTarget TARGET = CardTarget.ALL_ENEMY;
    public static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    public static final AbstractCard.CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    public static final int COST = 3;
    public static final int DAMAGE = 20;
    public static final int UPGRADED_DAMAGE = 5;
    public static final int MAGIC_NUMBER = 1;
    public static final int CHARGECOST = 3;

    public LeviathanEarthquake() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.damage = baseDamage = DAMAGE;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.secondValue = baseSecondValue = ArsenalRelic.leviathanCharges;

        this.setBackgroundTexture("theWeaponMasterResources/images/512/bg_leviathan_skill_3_charge_sm.png",
                "theWeaponMasterResources/images/1024/bg_leviathan_skill_3_charge.png");

        purgeOnUse = true;
        isMultiDamage = true;

        initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADED_DAMAGE);
            initializeDescription();
        }
    }

    public boolean canUpgrade() {
        return AbstractDungeon.player.hasRelic(ShockwaveModulatorRelic.ID);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean charged = false;
        if (ArsenalRelic.leviathanCharges >= CHARGECOST) {
            charged = true;
            AbstractDungeon.actionManager.addToBottom(new LeviathanChargeAction(-CHARGECOST));
        }

        AbstractDungeon.actionManager.addToBottom(new ShakeScreenAction(0.25F, ScreenShake.ShakeDur.LONG, ScreenShake.ShakeIntensity.HIGH));
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(p, damage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SMASH));
        }

        if (charged) {
            AbstractDungeon.actionManager.addToBottom(new ShakeScreenAction(0.25F, ScreenShake.ShakeDur.SHORT, ScreenShake.ShakeIntensity.HIGH));
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(p, damage / 2, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SMASH));
            }
        }
    }
}
