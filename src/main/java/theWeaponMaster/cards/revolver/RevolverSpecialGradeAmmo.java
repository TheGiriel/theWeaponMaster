package theWeaponMaster.cards.revolver;

import com.evacipated.cardcrawl.mod.stslib.actions.common.ModifyExhaustiveAction;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.ReloadAction;
import theWeaponMaster.actions.SpecialGradeAmmoAction;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.relics.RevolverRelic;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;
import static theWeaponMaster.patches.WeaponMasterTags.AMMUNITION;

public class RevolverSpecialGradeAmmo extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(RevolverSpecialGradeAmmo.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int UPGRADED_DAMAGE = 3;
    private static final int MAGIC_NUMBER = 1;
    private static final int UPGRADED_MAGIC_NUMBER = 1;
    private static final int SECOND_VALUE = 1;
    private static final int UPGRADED_SECOND_VALUE = 1;
    public static int publicDamage;
    public static int publicMagic;

    public RevolverSpecialGradeAmmo() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = publicDamage = baseDamage = DAMAGE;
        this.magicNumber = publicMagic = baseMagicNumber = MAGIC_NUMBER;
        this.defaultSecondMagicNumber = defaultBaseSecondMagicNumber = SECOND_VALUE;

        tags.add(AMMUNITION);
        ExhaustiveField.ExhaustiveFields.exhaustive.set(this, defaultSecondMagicNumber);
        ExhaustiveField.ExhaustiveFields.baseExhaustive.set(this, defaultSecondMagicNumber);
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
            upgradeDefaultSecondMagicNumber(UPGRADED_SECOND_VALUE);
            ExhaustiveField.ExhaustiveFields.isExhaustiveUpgraded.set(this, true);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.getRelic(RevolverRelic.ID).counter <= 0) {
            new ReloadAction();
            AbstractDungeon.actionManager.addToBottom(new ModifyExhaustiveAction(this, 1));
            return;
        }
        AbstractDungeon.actionManager.addToBottom(new SpecialGradeAmmoAction(m));
    }

}