package theWeaponMaster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.abstractcards.AbstractBullyCard;
import theWeaponMaster.cards.legendary_weapons.RevenantBloodbath;
import theWeaponMaster.cards.legendary_weapons.RevenantRavenous;
import theWeaponMaster.cards.tempCards.RevolverUnloadShot;
import theWeaponMaster.patches.WeaponMasterTags;
import theWeaponMaster.util.TextureLoader;

import static theWeaponMaster.TheWeaponMaster.makePowerPath;
import static theWeaponMaster.patches.WeaponMasterTags.BULLY;

public class ViciousPower extends TwoAmountPower {

    public static final String POWER_ID = TheWeaponMaster.makeID(ViciousPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ViciousPower.class.getSimpleName());
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;

    private static final Texture vicious1_84 = TextureLoader.getTexture(makePowerPath("vicious1_placeholder_84.png"));
    private static final Texture vicious1_32 = TextureLoader.getTexture(makePowerPath("vicious1_placeholder_32.png"));
    public static final int TIER_TWO = 5;
    private static final Texture vicious2_84 = TextureLoader.getTexture(makePowerPath("vicious2_placeholder_84.png"));
    private static final Texture vicious2_32 = TextureLoader.getTexture(makePowerPath("vicious2_placeholder_32.png"));
    public static final int TIER_THREE = TIER_TWO * 3;
    private static final Texture vicious3_84 = TextureLoader.getTexture(makePowerPath("vicious3_placeholder_84.png"));
    private static final Texture vicious3_32 = TextureLoader.getTexture(makePowerPath("vicious3_placeholder_32.png"));

    private static Texture tex84;
    private static Texture tex32;
    private int reduceVicious;
    public static int berserkerStanceBonus = 0;
    public static int publicAmount;
    public static int viciousBonusOnAttack = 2;


    public ViciousPower(final AbstractCreature owner, int amnt) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amnt;
        this.amount2 = amount / 5;
        viciousBonusOnAttack = publicAmount = 2;

        type = PowerType.BUFF;
        isTurnBased = false;

        tex32 = vicious1_32;
        tex84 = vicious1_84;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    private void setBonusDamage() {
        amount2 = this.amount / 5;
    }

    @Override
    public void onInitialApplication() {
        if (owner.hasPower(MalicePower.POWER_ID)) {
            berserkerStanceBonus = 1;
        }
    }

    public static int getPublicAmount() {
        return publicAmount;
    }

    @Override
    public void onRemove() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new ViciousPower(owner, 1)));
    }

    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if (owner.hasPower(ProtectiveBladePower.POWER_ID) || targetCard.cardID.equals(RevolverUnloadShot.ID)) {
            return;
        }
        if (targetCard.hasTag(BULLY)) {
            AbstractBullyCard bullyCard = (AbstractBullyCard) targetCard;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new ViciousPower(owner, bullyCard.bullyNumber + berserkerStanceBonus)));
        } else if (targetCard.type == AbstractCard.CardType.ATTACK && (!targetCard.equals(RevenantRavenous.ID) || !targetCard.equals(RevenantBloodbath.ID)) && !targetCard.hasTag(WeaponMasterTags.TEMPORARY)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new ViciousPower(owner, viciousBonusOnAttack + berserkerStanceBonus)));
        }
    }

    public void stackPower(int stackAmount) {
        if (owner.hasPower(ProtectiveBladePower.POWER_ID)) {
            return;
        }
        this.amount += stackAmount;
        setBonusDamage();
        if (this.amount >= TIER_THREE) {
            setTierThree();
        } else if (this.amount >= TIER_TWO) {
            setTierTwo();
        }
        updateDescription();
    }

    public void reducePower(int stackAmount) {
        this.amount -= stackAmount;
        setBonusDamage();
        if (this.amount < TIER_THREE && this.amount > TIER_TWO) {
            setTierTwo();
        } else if (this.amount < TIER_TWO) {
            setTierOne();
        }
        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (this.amount <= TIER_TWO) {
            setReduceVicious(1);
            description = DESCRIPTION[0] + amount2 + DESCRIPTION[1] + amount2 + DESCRIPTION[2];
            setTierOne();
        } else if (this.amount > TIER_THREE) {
            setTierThree();
            setReduceVicious(3 + berserkerStanceBonus);
            description = DESCRIPTION[0] + amount2 + DESCRIPTION[1] + amount2 + DESCRIPTION[2] + DESCRIPTION[3] + reduceVicious + DESCRIPTION[4];
        } else {
            setTierTwo();
            setReduceVicious(4 + berserkerStanceBonus);
            description = DESCRIPTION[0] + amount2 + DESCRIPTION[1] + amount2 + DESCRIPTION[2] + DESCRIPTION[3] + reduceVicious + DESCRIPTION[4];
        }
    }

    private void setTierTwo() {
        this.region128 = new TextureAtlas.AtlasRegion(vicious2_84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(vicious2_32, 0, 0, 32, 32);
    }

    private void setTierThree() {
        this.region128 = new TextureAtlas.AtlasRegion(vicious3_84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(vicious3_32, 0, 0, 32, 32);
    }

    private void setTierOne() {
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return damage + amount2;
    }

    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        return damage + amount2;
    }

    private void setReduceVicious(int modifier) {
        reduceVicious = (this.amount / modifier);
    }

    public void atEndOfRound() {
        if (owner.hasPower(MalicePower.POWER_ID)) {
            berserkerStanceBonus = 1;
        } else berserkerStanceBonus = 0;
        updateDescription();
        if (amount > TIER_THREE) {
            setReduceVicious(3 + berserkerStanceBonus);
            this.amount -= reduceVicious;
            setBonusDamage();
        } else if (amount > TIER_TWO) {
            setReduceVicious(4 + berserkerStanceBonus);
            this.amount -= reduceVicious;
            setBonusDamage();
        } else {
            setReduceVicious(1);
            setBonusDamage();
        }
        updateDescription();
    }
}
