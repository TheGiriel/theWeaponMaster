package theWeaponMaster.cards.legendary_weapons;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
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
import theWeaponMaster.relics.ArsenalRelic;
import theWeaponMaster.relics.ShockwaveModulatorRelic;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class LeviathanDeepImpact extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(LeviathanDeepImpact.class.getSimpleName());
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    public static final String IMG = makeCardPath("Attack.png");

    public static final CardRarity RARITY = CardRarity.SPECIAL;
    public static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    public static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    public static final int COST = 2;
    public static final int DAMAGE = 8;
    public static final int UPGRADED_DAMAGE = 2;
    public static final int MAGIC_NUMBER = 25;
    public static final int UPGRADED_MAGIC_NUMBER = 8;
    private final int CHARGECOST = 2;

    public LeviathanDeepImpact() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.damage = baseDamage = DAMAGE;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.secondValue = baseSecondValue = ArsenalRelic.leviathanCharges;

        this.setBackgroundTexture("theWeaponMasterResources/images/512/bg_leviathan_skill_3_charge_sm.png",
                "theWeaponMasterResources/images/1024/bg_leviathan_skill_3_charge.png");

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
        return AbstractDungeon.player.hasRelic(ShockwaveModulatorRelic.ID);
    }

    public int impactDamage(AbstractMonster monster){
        if (monster.currentBlock>0){
            this.damage += magicNumber;
        }
        return this.damage;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean charged = false;
        if (ArsenalRelic.leviathanCharges >= CHARGECOST) {
            charged = true;
            AbstractDungeon.actionManager.addToBottom(new LeviathanChargeAction(-CHARGECOST));
        }
        for (AbstractMonster target : AbstractDungeon.getMonsters().monsters) {
            if (target.currentBlock != 0 || charged) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(p, impactDamage(target)), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            } else {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(p, impactDamage(target)), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                break;
            }
        }
    }
}
