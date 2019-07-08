package theWeaponMaster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.util.TextureLoader;

import static theWeaponMaster.DefaultMod.makePowerPath;

public class ViciousPower extends AbstractPower {

    public static final String POWER_ID = "ViciousPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("ViciousPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;
    public static final int TIER_TWO = 3;
    public static final int TIER_THREE = TIER_TWO*2;

    private static final Texture vicious1_84 = TextureLoader.getTexture(makePowerPath("vicious1_placeholder_84.png"));
    private static final Texture vicious1_32 = TextureLoader.getTexture(makePowerPath("vicious1_placeholder_32.png"));
    private static final Texture vicious2_84 = TextureLoader.getTexture("vicious2_placeholder_84.png");
    private static final Texture vicious2_32 = TextureLoader.getTexture("vicious2_placeholder_32.png");
    private static final Texture vicious3_84 = TextureLoader.getTexture("vicious3_placeholder_84.png");
    private static final Texture vicious3_32 = TextureLoader.getTexture("vicious3_placeholder_32.png");

    private static Texture tex84;
    private static Texture tex32;
    public ViciousPower(final AbstractCreature owner, int amnt) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amnt;

        type = PowerType.BUFF;
        isTurnBased = false;

        tex32 = vicious1_32;
        tex84 = vicious1_84;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48  = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void stackPower(int stackAmount){
        this.amount += stackAmount;
        if (this.amount >= TIER_TWO) {
            this.region128 = new TextureAtlas.AtlasRegion(vicious2_84, 0, 0, 84, 84);
            this.region48 = new TextureAtlas.AtlasRegion(vicious2_32, 0, 0, 32, 32);
            updateDescription();
        } if (this.amount >= TIER_THREE) {
            this.region128 = new TextureAtlas.AtlasRegion(vicious3_84, 0, 0, 84, 84);
            this.region48 = new TextureAtlas.AtlasRegion(vicious3_32, 0, 0, 32, 32);
            updateDescription();
        } else {
            updateDescription();
        }
    }

    public void reducePower(int stackAmount){
        this.amount -= stackAmount;
        if (this.amount <TIER_THREE) {
            this.region128 = new TextureAtlas.AtlasRegion(vicious2_84, 0, 0, 84, 84);
            this.region48 = new TextureAtlas.AtlasRegion(vicious2_32, 0, 0, 32, 32);
            updateDescription();
        } if (this.amount <TIER_TWO) {
            this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
            this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
            updateDescription();
        } else {
            updateDescription();
        }
    }

    @Override
    public void updateDescription() {
        if (this.amount >= 3) {
            if (this.amount >=6){
                description = DESCRIPTION[0] + amount + DESCRIPTION[1] + amount + DESCRIPTION[4];
            } else description = DESCRIPTION[0] + amount + DESCRIPTION[1] + amount + DESCRIPTION[3];
        }

        else {
            description = DESCRIPTION[0] + amount + DESCRIPTION[1] + amount + DESCRIPTION[2];
        }
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return damage + this.amount;
    }

    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        DefaultMod.logger.info("taking extra damage.");
        return damage + this.amount;
    }
}
