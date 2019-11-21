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
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    public static final String IMG = makeCardPath("Attack.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 6;
    private static final int UPGRADED_DAMAGE = 3;
    private static final int MAGIC_NUMBER = 3;
    private static final int UPGRADED_MAGIC_NUMBER = 2;
    private final int HUNGERCOST = 4;
    private static int turnCount = 0;

    public RevenantRavenous() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.damage = baseDamage = DAMAGE;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.defaultSecondMagicNumber = defaultBaseSecondMagicNumber = ArsenalRelic.revenantHunger;

        AlwaysRetainField.alwaysRetain.set(this, true);

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

    public void getSated() {
        if (ArsenalRelic.revenantHunger < HUNGERCOST) {
            this.rawDescription = DESCRIPTION[1];
        } else {
            this.rawDescription = DESCRIPTION[0];
        }
    }

    public boolean canUpgrade() {
        return AbstractDungeon.player.hasRelic(GhoulskinSheathRelic.ID);
    }

    //TODO: Improve method
    public void use(AbstractPlayer p, AbstractMonster m) {

        if (ArsenalRelic.revenantHunger >= HUNGERCOST) {
            new RevenantStarveAction(-HUNGERCOST, false);
            AbstractDungeon.actionManager.addToTurnStart(new ApplyPowerAction(p, p, new ViciousPower(p, this.magicNumber)));
        } else {
            new RevenantStarveAction(0, true);
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        AbstractDungeon.actionManager.addToTurnStart(new ApplyPowerAction(p, p, new ViciousPower(p, this.magicNumber)));

        getSated();
    }

    public void atTurnStart() {
        turnCount++;
        if (AbstractDungeon.player.hand.contains(this) && turnCount > 1) {
            baseMagicNumber += 2;
        }
    }
}
