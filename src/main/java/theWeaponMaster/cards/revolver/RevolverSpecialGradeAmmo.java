package theWeaponMaster.cards.revolver;

import com.evacipated.cardcrawl.mod.stslib.actions.common.ModifyExhaustiveAction;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
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
import theWeaponMaster.actions.OctopusAction;
import theWeaponMaster.actions.ReloadAction;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.powers.LaceratePower;
import theWeaponMaster.powers.ManaBurnPower;
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
    private static final int MAGIC_NUMBER = 2;
    private static final int UPGRADED_MAGIC_NUMBER = 1;
    public static boolean ethereal = false;
    public static int publicDamage;
    public static int publicMagic;

    public RevolverSpecialGradeAmmo() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = publicDamage = baseDamage = DAMAGE;
        this.magicNumber = publicMagic = baseMagicNumber = MAGIC_NUMBER;

        tags.add(AMMUNITION);
        ExhaustiveField.ExhaustiveFields.exhaustive.set(this, magicNumber);
        ExhaustiveField.ExhaustiveFields.baseExhaustive.set(this, magicNumber);
    }

    public static int getPublicDamage() {
        return publicDamage;
    }

    public static int getPublicMagic() {
        return publicMagic;
    }

    public static void flipUse(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADED_DAMAGE);
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
        new OctopusAction().specialGradeAmmo();
        if (ethereal) {
            TheWeaponMaster.logger.info("etheral");
            manaBurnShot(p, m);
        } else {
            TheWeaponMaster.logger.info("lacerate");
            lacerateShot(p, m);
        }
        flipUse(p, m);
    }

    public void lacerateShot(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, getPublicDamage(), DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new LaceratePower(m, p, getPublicMagic())));
    }

    public void manaBurnShot(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, getPublicDamage(), DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new ManaBurnPower(m, p, getPublicMagic())));
    }
}