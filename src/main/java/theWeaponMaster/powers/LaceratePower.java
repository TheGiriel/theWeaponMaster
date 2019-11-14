package theWeaponMaster.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.actions.LacerateAction;
import theWeaponMaster.util.TextureLoader;

public class LaceratePower extends AbstractPower implements HealthBarRenderPower {

    public static final Color bleed = new Color((float) (255 / 255), (float) (128 / 255), (float) (17 / 255), 1.0F);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("LaceratePower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;
    private static final String POWER_ID = DefaultMod.makeID(LaceratePower.class.getSimpleName());
    private static final Texture lacerate_84 = TextureLoader.getTexture(DefaultMod.makePowerPath("Lacerate_placeholder_84.png"));
    private static final Texture lacerate_32 = TextureLoader.getTexture(DefaultMod.makePowerPath("Lacerate_placeholder_32.png"));
    private static final Texture hemorrhage_84 = TextureLoader.getTexture(DefaultMod.makePowerPath("Hemorrhage_placeholder_84.png"));

    private AbstractCreature source;
    private int bleedDamage;
    private static final Texture hemorrhage_32 = TextureLoader.getTexture(DefaultMod.makePowerPath("Hemorrhage_placeholder_32.png"));

    public LaceratePower(AbstractCreature owner, AbstractCreature source, int bleedStack) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = bleedStack;
        this.source = source;

        this.region128 = new TextureAtlas.AtlasRegion(lacerate_84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(lacerate_32, 0, 0, 32, 32);

        updateDamage();
        updateDescription();
        this.type = AbstractPower.PowerType.DEBUFF;
        this.isTurnBased = true;
    }

    private void updateDamage() {
        this.bleedDamage = (int)Math.ceil(this.owner.maxHealth * 0.02D * this.amount);
        updateDescription();
    }

    public void updateDescription() {
        if (this.amount < 3) {
            this.description = DESCRIPTION[0] + (this.amount * 2) + DESCRIPTION[1] + this.bleedDamage + DESCRIPTION[2] + this.amount;
        } else {
            this.description = DESCRIPTION[0] + (this.amount * 2) + DESCRIPTION[1] + this.bleedDamage + DESCRIPTION[3];
        }
    }

    public void stackPower(int stackAmount) {
        this.amount += stackAmount;
        if (this.amount >= 3) {
            this.region128 = new TextureAtlas.AtlasRegion(hemorrhage_84, 0, 0, 84, 84);
            this.region48 = new TextureAtlas.AtlasRegion(hemorrhage_32, 0, 0, 32, 32);
        }
        this.bleedDamage = (int) Math.ceil(this.owner.maxHealth * 0.02D * this.amount);
        updateDescription();
    }

    public void reducePower(int stackAmount) {
        this.amount -= stackAmount;
        if (this.amount <= 2) {
            this.name = NAME;
            this.region128 = new TextureAtlas.AtlasRegion(lacerate_84, 0, 0, 84, 84);
            this.region48 = new TextureAtlas.AtlasRegion(lacerate_32, 0, 0, 32, 32);
        }
    }

    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new LacerateAction(this.owner, this.source, this.amount));
    }

    @Override
    public int getHealthBarAmount() {
        return bleedDamage;
    }

    @Override
    public Color getColor() {
        return bleed;
    }
}