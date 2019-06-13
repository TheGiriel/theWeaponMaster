package theWeaponMaster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.util.TextureLoader;

import static theWeaponMaster.DefaultMod.makePowerPath;

public class ViciousPower extends AbstractPower {

    public static final String POWER_ID = "ViciousPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("ViciousPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public ViciousPower(final AbstractCreature owner, int amnt) {
        this.name = NAME;
        this.ID = "ViciousPower";
        this.owner = owner;
        this.amount = amnt;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {

        return damage + this.amount;

    }

    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        DefaultMod.logger.info("taking extra damage.");
        return damage + this.amount;
    }
}
