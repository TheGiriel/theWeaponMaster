package theWeaponMaster.cards.legendary_weapons;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
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
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.powers.ViciousPower;
import theWeaponMaster.relics.ArsenalRelic;
import theWeaponMaster.relics.GhoulskinSheathRelic;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;
import static theWeaponMaster.patches.WeaponMasterTags.REVENANT;

public class RevenantRavenous extends AbstractDynamicCard {

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
    //use damage as magic number and vice versa since I'm lazy
    public static final int DAMAGE_TO_MAGIC = 3;
    public static final int UPGRADED_DAMAGE = 2;
    public static final int MAGIC_NUMBER_TO_DAMAGE = 6;
    public static final int UPGRADED_MAGIC_NUMBER = 2;
    private final int HUNGERCOST = 4;
    private int turnCount = 0;

    public RevenantRavenous() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.damage = baseDamage = DAMAGE_TO_MAGIC;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER_TO_DAMAGE;
        this.secondValue = baseSecondValue = ArsenalRelic.revenantHunger;

        AlwaysRetainField.alwaysRetain.set(this, true);

        getSated();
        tags.add(REVENANT);
        initializeDescription();
        AlwaysRetainField.alwaysRetain.set(this, true);
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
        if (ArsenalRelic.revenantHunger < HUNGERCOST) {
            this.rawDescription = DESCRIPTION[1];
            TheWeaponMaster.logger.info(this.rawDescription);
        } else {
            this.rawDescription = DESCRIPTION[0];
        }
    }

    public boolean canUpgrade() {
        return AbstractDungeon.player.hasRelic(GhoulskinSheathRelic.ID);
    }

    @Override
    public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster mo, float tmp) {
        if (turnCount > 0) {
            return super.calculateModifiedCardDamage(player, mo, tmp + turnCount);
        } else
            return super.calculateModifiedCardDamage(player, mo, tmp);
    }

    //TODO: Improve method
    public void use(AbstractPlayer p, AbstractMonster m) {

        if (ArsenalRelic.revenantHunger >= HUNGERCOST) {
            new RevenantStarveAction(-HUNGERCOST, false);
            AbstractDungeon.actionManager.addToTurnStart(new ApplyPowerAction(p, p, new ViciousPower(p, 3)));
        } else {
            new RevenantStarveAction(0, true);
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.magicNumber, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        AbstractDungeon.actionManager.addToTurnStart(new ApplyPowerAction(p, p, new ViciousPower(p, this.damage)));

        TheWeaponMaster.logger.info("Getting sated change");
        getSated();
    }

    public void atTurnStart() {
        if (AbstractDungeon.player.hand.contains(this)) {
            turnCount++;
        }
    }
}
