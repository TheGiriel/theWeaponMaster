package theWeaponMaster.cards.revolver;

import com.evacipated.cardcrawl.mod.stslib.actions.common.ModifyExhaustiveAction;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.SpecialGradeAmmoAction;
import theWeaponMaster.cards.abstractcards.AbstractRevolverCard;
import theWeaponMaster.relics.RevolverRelic;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;
import static theWeaponMaster.patches.WeaponMasterTags.AMMUNITION;

public class RevolverSpecialGradeAmmo extends AbstractRevolverCard {

    public static final String ID = TheWeaponMaster.makeID(RevolverSpecialGradeAmmo.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(RevolverSpecialGradeAmmo.class.getSimpleName());
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final String[] DESCRIPTIONS = cardStrings.EXTENDED_DESCRIPTION;
    public static final CardRarity RARITY = CardRarity.RARE;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final CardType TYPE = CardType.ATTACK;
    public static final int COST = 1;
    public static final int DAMAGE = 7;
    public static final int UPGRADED_DAMAGE = 3;
    public static final int MAGIC_NUMBER = 1;
    public static final int UPGRADED_MAGIC_NUMBER = 1;
    private static final int SECOND_VALUE = 1;
    private static final int UPGRADED_SECOND_VALUE = 1;
    public static int publicDamage;
    public static int publicMagic;

    public RevolverSpecialGradeAmmo() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.damage = publicDamage = baseDamage = DAMAGE;
        this.magicNumber = publicMagic = baseMagicNumber = MAGIC_NUMBER;
        this.secondValue = baseSecondValue = SECOND_VALUE;

        tags.add(AMMUNITION);
        ExhaustiveField.ExhaustiveFields.exhaustive.set(this, secondValue);
        ExhaustiveField.ExhaustiveFields.baseExhaustive.set(this, secondValue);

        initializeDescription();
    }

    public static int getPublicDamage() {
        return publicDamage;
    }

    public static int getPublicMagic() {
        return publicMagic;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADED_DAMAGE);
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            upgradeSecondValue(UPGRADED_SECOND_VALUE);
            rawDescription = UPGRADE_DESCRIPTION;
            ExhaustiveField.ExhaustiveFields.isExhaustiveUpgraded.set(this, true);
        }
    }

    @Override
    public void setNormalDescription() {
        this.cost = COST;
        if (upgraded) {
            rawDescription = DESCRIPTIONS[0];
        } else {
            rawDescription = DESCRIPTIONS[1];
        }
        type = CardType.ATTACK;
        target = TARGET;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.getRelic(RevolverRelic.ID).counter <= 0) {
            AbstractDungeon.actionManager.addToBottom(new ModifyExhaustiveAction(this, 1));
            return;
        }
        AbstractDungeon.actionManager.addToBottom(new SpecialGradeAmmoAction(m));
    }

}