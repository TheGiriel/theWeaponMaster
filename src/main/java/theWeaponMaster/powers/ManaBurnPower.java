package theWeaponMaster.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.ManaBurnAction;
import theWeaponMaster.util.TextureLoader;

public class ManaBurnPower extends AbstractPower implements HealthBarRenderPower {
    public static final String POWER_ID = TheWeaponMaster.makeID(ManaBurnPower.class.getSimpleName());
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("ManaBurnPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;

    public static final Texture manaburn_84 = TextureLoader.getTexture(TheWeaponMaster.makePowerPath("Mana_Burn_placeholder_84.png"));
    public static final Texture manaburn_32 = TextureLoader.getTexture(TheWeaponMaster.makePowerPath("Mana_Burn_placeholder_32.png"));
    public static final Texture manablaze_84 = TextureLoader.getTexture(TheWeaponMaster.makePowerPath("manablaze_placeholder_84.png"));
    public static final Texture manablaze_32 = TextureLoader.getTexture(TheWeaponMaster.makePowerPath("manablaze_placeholder_32.png"));

    private AbstractCreature source;
    private int manaBurnIntensity;
    public static AbstractMonster m;
    public static int igniteDamage;
    public static double IGNITE = 0.03;

    public ManaBurnPower(AbstractMonster owner, AbstractCreature source, int manaBurn) {
        this.name = NAME;
        this.ID = POWER_ID;
        m = owner;
        this.owner = owner;
        this.amount = manaBurn;
        this.source = source;

        this.region128 = new TextureAtlas.AtlasRegion(manaburn_84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(manaburn_32, 0, 0, 32, 32);

        this.type = AbstractPower.PowerType.DEBUFF;
        m = (AbstractMonster) this.owner;

        this.manaBurnIntensity = manaBurnDamage();
        this.isTurnBased = true;
        getHealthBarAmount();
        updateDescription();
    }

    public static int IgniteDamage() {
        return (int) Math.ceil(m.currentHealth * 0.03D * igniteDamage);
    }

    private void updateDamage() {
        this.manaBurnIntensity = (int) Math.ceil(this.owner.maxHealth * 0.03D * this.amount);
        getHealthBarAmount();
        updateDescription();
    }

    private int manaBurnDamage() {
        return (int) Math.ceil(this.owner.maxHealth * 0.03D * this.amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTION[0] + manaBurnDamage() + DESCRIPTION[1] + this.amount;
        if (this.amount >= 3) {
            this.description += DESCRIPTION[2];
        }
        getHealthBarAmount();
    }

    public int getHealthBarAmount() {
        if (ManaBurnAction.intent.contains(m.intent)) {
            return manaBurnDamage();
        }
        return 0;
    }

    @Override
    public Color getColor() {
        return Color.CYAN;
    }

    public void stackPower(int stackAmount) {
        this.amount += stackAmount;
        if (this.amount >= 3) {
            this.name = "Manablaze";
            this.region128 = new TextureAtlas.AtlasRegion(manablaze_84, 0, 0, 84, 84);
            this.region48 = new TextureAtlas.AtlasRegion(manablaze_32, 0, 0, 32, 32);
        }
        getHealthBarAmount();
        updateDamage();
    }

    public void reducePower(int stackAmount) {
        this.amount -= stackAmount;
        if (this.amount <= 2) {
            this.name = NAME;
            this.region128 = new TextureAtlas.AtlasRegion(manaburn_84, 0, 0, 84, 84);
            this.region48 = new TextureAtlas.AtlasRegion(manaburn_32, 0, 0, 32, 32);
        }
        getHealthBarAmount();
        updateDamage();
    }

    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new ManaBurnAction(this.owner, this.source, this.amount));
    }
}
