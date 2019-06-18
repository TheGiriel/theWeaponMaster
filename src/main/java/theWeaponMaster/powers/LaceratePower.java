package theWeaponMaster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.actions.LacerateAction;
import theWeaponMaster.util.TextureLoader;


public class LaceratePower
        extends AbstractPower
{
    private static final String POWER_ID = "LaceratePower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("LaceratePower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(DefaultMod.makePowerPath("Lacerate_placeholder_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(DefaultMod.makePowerPath("Lacerate_placeholder_32.png"));

    private AbstractCreature source;
    private int bleedDamage;

    public LaceratePower(AbstractCreature owner, AbstractCreature source, int bleedStack) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = bleedStack;

        this.source = source;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDamage();
        updateDescription();
        this.type = AbstractPower.PowerType.DEBUFF;
        this.isTurnBased = true;
    }

    public void updateDamage() {
        if (this.amount > 3) {
            this.amount = 3;
        }
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
        if (!owner.hasPower("HemorrhagePower")) {
            this.amount += stackAmount;
            if (this.amount > 2) {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.source, "LaceratePower"));
                DefaultMod.logger.info("Removing Lacerate, applying HemorrhagePower.");
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.source, new HemorrhagePower(this.owner, this.source)));
            }
            this.bleedDamage = (int) Math.ceil(this.owner.maxHealth * 0.02D * this.amount);
            updateDescription();
        }
    }


    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new LacerateAction(this.owner, this.source, this.amount));

    }
}