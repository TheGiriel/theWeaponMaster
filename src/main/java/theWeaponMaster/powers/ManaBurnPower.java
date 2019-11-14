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
import theWeaponMaster.DefaultMod;
import theWeaponMaster.actions.ManaBurnAction;
import theWeaponMaster.util.TextureLoader;

public class ManaBurnPower extends AbstractPower implements HealthBarRenderPower {
    private static final String POWER_ID = DefaultMod.makeID(ManaBurnPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("ManaBurnPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;

    private static final Texture manaburn_84 = TextureLoader.getTexture(DefaultMod.makePowerPath("Mana_Burn_placeholder_84.png"));
    private static final Texture manaburn_32 = TextureLoader.getTexture(DefaultMod.makePowerPath("Mana_Burn_placeholder_32.png"));
    private static final Texture manablaze_84 = TextureLoader.getTexture(DefaultMod.makePowerPath("manablaze_placeholder_84.png"));
    private static final Texture manablaze_32 = TextureLoader.getTexture(DefaultMod.makePowerPath("manablaze_placeholder_32.png"));

    private AbstractCreature source;
    private int manaBurnIntensity;
    private AbstractMonster m;

    public ManaBurnPower(AbstractCreature owner, AbstractCreature source, int manaBurn) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = manaBurn;
        this.source = source;

        this.region128 = new TextureAtlas.AtlasRegion(manaburn_84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(manaburn_32, 0, 0, 32, 32);

        this.type = AbstractPower.PowerType.DEBUFF;
        this.m = (AbstractMonster)this.owner;

        this.manaBurnIntensity = manaBurnDamage();
        this.isTurnBased = true;
        updateDescription();
        getHealthBarAmount();
    }

    private void updateDamage() {
        this.manaBurnIntensity = (int) Math.ceil(this.owner.maxHealth * 0.03D * this.amount);
        updateDescription();
    }

    private int manaBurnDamage() {
        return (int) Math.ceil(this.owner.maxHealth * 0.03D * this.amount);
    }

    public void updateDescription() {
        if ((int) Math.ceil(this.m.maxHealth * 0.01D * this.amount) == 1) {
            this.description = DESCRIPTION[0] + manaBurnDamage() + DESCRIPTION[2] + this.amount;
        } else {
            this.description = DESCRIPTION[0] + manaBurnDamage() + DESCRIPTION[1] + manaBurnDamage() + DESCRIPTION[2] + this.amount;
        }
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
        updateDamage();
    }

    public void reducePower(int stackAmount) {
        this.amount -= stackAmount;
        if (this.amount <= 2) {
            this.name = NAME;
            this.region128 = new TextureAtlas.AtlasRegion(manaburn_84, 0, 0, 84, 84);
            this.region48 = new TextureAtlas.AtlasRegion(manaburn_32, 0, 0, 32, 32);
        }
    }

    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new ManaBurnAction(this.owner, this.source, this.amount));
    }
}
