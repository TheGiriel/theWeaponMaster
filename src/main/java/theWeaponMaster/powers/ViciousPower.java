package theWeaponMaster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.util.TextureLoader;

import static theWeaponMaster.DefaultMod.makePowerPath;

public class ViciousPower extends TwoAmountPower {

    public static final String POWER_ID = DefaultMod.makeID(ViciousPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("ViciousPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;
    private static final int TIER_TWO = 3;
    private static final int TIER_THREE = TIER_TWO*2;
    private static int bonusDamage = 0;


    private static final Texture vicious1_84 = TextureLoader.getTexture(makePowerPath("vicious1_placeholder_84.png"));
    private static final Texture vicious1_32 = TextureLoader.getTexture(makePowerPath("vicious1_placeholder_32.png"));
    private static final Texture vicious2_84 = TextureLoader.getTexture(makePowerPath("vicious2_placeholder_84.png"));
    private static final Texture vicious2_32 = TextureLoader.getTexture(makePowerPath("vicious2_placeholder_32.png"));
    private static final Texture vicious3_84 = TextureLoader.getTexture(makePowerPath("vicious3_placeholder_84.png"));
    private static final Texture vicious3_32 = TextureLoader.getTexture(makePowerPath("vicious3_placeholder_32.png"));

    private static Texture tex84;
    private static Texture tex32;

    public ViciousPower(final AbstractCreature owner, int amnt) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amnt;
        this.amount2 = amount / 5;

        type = PowerType.BUFF;
        isTurnBased = false;

        tex32 = vicious1_32;
        tex84 = vicious1_84;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48  = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    private void setBonusDamage() {
        amount2 = this.amount / 5;
    }

    public void stackPower(int stackAmount){
        this.amount += stackAmount;
        setBonusDamage();
        if (this.amount >= TIER_THREE) {
            setTierThree();
            updateDescription();
        } else if (this.amount >= TIER_TWO) {
            setTierTwo();
            updateDescription();
        } else {
            updateDescription();
        }
    }

    public void reducePower(int stackAmount){
        this.amount -= stackAmount;
        setBonusDamage();
        if (this.amount < TIER_THREE && this.amount > TIER_TWO) {
            setTierTwo();
            updateDescription();
        } else if (this.amount < TIER_TWO) {
            setTierOne();
            updateDescription();
        } else {
            updateDescription();
        }
    }

    @Override
    public void updateDescription() {
        setBonusDamage();
        if (this.amount >= TIER_TWO) {
            description = DESCRIPTION[0] + amount2 + DESCRIPTION[1] + amount2 + DESCRIPTION[3];
        }
        else {
            description = DESCRIPTION[0] + amount2 + DESCRIPTION[1] + amount2 + DESCRIPTION[2];
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

    public void atEndOfRound() {
        int reduceVicious = (this.amount / 3);
        this.amount -= reduceVicious;
        setBonusDamage();
    }

    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        DefaultMod.logger.info("taking extra damage.");
        return damage + amount2;
    }
}
