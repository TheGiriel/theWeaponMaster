package theWeaponMaster.cards.legendary_weapons;

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
import theWeaponMaster.actions.RevenantStarveAction;
import theWeaponMaster.cards.abstractcards.AbstractRevenantCard;
import theWeaponMaster.powers.ViciousPower;
import theWeaponMaster.relics.ArsenalRelic;
import theWeaponMaster.relics.GhoulskinSheathRelic;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;
import static theWeaponMaster.patches.WeaponMasterTags.REVENANT;

public class RevenantRavenous extends AbstractRevenantCard {

    public static final String ID = TheWeaponMaster.makeID(RevenantRavenous.class.getSimpleName());
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    public static final String IMG = makeCardPath("Attack.png");
    public static final CardRarity RARITY = CardRarity.SPECIAL;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    public static final int COST = 1;
    public static final int DAMAGE = 6;
    public static final int UPGRADED_DAMAGE = 2;
    public static final int MAGIC_NUMBER = 3;
    public static final int UPGRADED_MAGIC_NUMBER = 2;
    public static final int HUNGER = 4;

    public RevenantRavenous() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, HUNGER);

        this.damage = baseDamage = DAMAGE;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.secondValue = baseSecondValue = ArsenalRelic.revenantHunger;

        this.setBackgroundTexture("theWeaponMasterResources/images/512/bg_revenant_attack.png", "theWeaponMasterResources/images/1024/bg_revenant_attack.png");

        getSated();
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

    @Override
    public void initializeDescription() {
        getSated();
        super.initializeDescription();
    }

    public void getSated() {
        if (ArsenalRelic.revenantHunger < HUNGER) {
            //this.setBackgroundTexture("theWeaponMasterResources/images/512/bg_revenant_sated_attack.png", "theWeaponMasterResources/images/1024/bg_revenant_sated_attack.png");
            rawDescription = DESCRIPTION[1];
        } else {
            //this.setBackgroundTexture("theWeaponMasterResources/images/512/bg_revenant_attack.png", "theWeaponMasterResources/images/1024/bg_revenant_attack.png");
            rawDescription = DESCRIPTION[0];
        }
    }

    public boolean canUpgrade() {
        return AbstractDungeon.player.hasRelic(GhoulskinSheathRelic.ID);
    }

    //TODO: Improve method
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (ArsenalRelic.revenantHunger >= HUNGER) {
            AbstractDungeon.actionManager.addToBottom(new RevenantStarveAction(-HUNGER, false));
            AbstractDungeon.actionManager.addToTurnStart(new ApplyPowerAction(p, p, new ViciousPower(p, 3)));
        } else {
            AbstractDungeon.actionManager.addToBottom(new RevenantStarveAction(0, true));
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        AbstractDungeon.actionManager.addToTurnStart(new ApplyPowerAction(p, p, new ViciousPower(p, magicNumber)));

        TheWeaponMaster.logger.info("Getting sated change");
        getSated();
    }
}
