package theWeaponMaster.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.util.TextureLoader;

public class LaceratePower extends AbstractPower implements HealthBarRenderPower {

    public static final Color bleed = new Color((float) (250 / 255), (float) (128 / 255), (float) (17 / 255), 1.0F);
    public static final String POWER_ID = TheWeaponMaster.makeID(LaceratePower.class.getSimpleName());
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(LaceratePower.class.getSimpleName());
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final Texture lacerate_84 = TextureLoader.getTexture(TheWeaponMaster.makePowerPath("Lacerate_placeholder_84.png"));
    public static final Texture lacerate_32 = TextureLoader.getTexture(TheWeaponMaster.makePowerPath("Lacerate_placeholder_32.png"));
    public static final Texture hemorrhage_84 = TextureLoader.getTexture(TheWeaponMaster.makePowerPath("Hemorrhage_placeholder_84.png"));
    private static final Texture hemorrhage_32 = TextureLoader.getTexture(TheWeaponMaster.makePowerPath("Hemorrhage_placeholder_32.png"));

    private int bleedDamage;

    public LaceratePower(AbstractCreature owner, int bleedStack) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = bleedStack;

        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(lacerate_84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(lacerate_32, 0, 0, 32, 32);

        updateDamage();
        updateDescription();
    }

    private void updateDamage() {
        this.bleedDamage = (int) Math.round(this.owner.maxHealth * 0.02D * this.amount);
        getHealthBarAmount();
        updateDescription();
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID.equals(NickedPower.POWER_ID) || power.ID.equals(KneecappedPower.POWER_ID)) {
            updateDescription();
        }
        super.onApplyPower(power, target, source);
    }

    public void updateDescription() {
        int bonusStacks = 0;
        if (owner.hasPower(NickedPower.POWER_ID)) {
            bonusStacks++;
        }
        if (owner.hasPower(KneecappedPower.POWER_ID)) {
            bonusStacks++;
        }
        this.description = DESCRIPTIONS[0] + (this.amount * 2) + DESCRIPTIONS[1] + this.bleedDamage + DESCRIPTIONS[2] + (bonusStacks + amount);
    }

    public void stackPower(int stackAmount) {
        this.amount += stackAmount;
        if (this.amount >= 3) {
            this.region128 = new TextureAtlas.AtlasRegion(hemorrhage_84, 0, 0, 84, 84);
            this.region48 = new TextureAtlas.AtlasRegion(hemorrhage_32, 0, 0, 32, 32);
        }
        this.bleedDamage = (int) Math.ceil(owner.maxHealth * 0.02D * this.amount);
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
        AbstractDungeon.actionManager.addToBottom(new LoseHPAction(owner, owner, (int) (Math.ceil(this.owner.maxHealth * 0.02D * this.amount)), AbstractGameAction.AttackEffect.POISON));
    }

    @Override
    public int getHealthBarAmount() {
        return bleedDamage;
    }

    @Override
    public Color getColor() {
        return Color.valueOf("#9f0000");
    }
}