package theGodHunters.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theGodHunters.DefaultMod;
import theGodHunters.actions.ManaBurnAction;
import theGodHunters.util.TextureLoader;

public class ManaBurnPower extends AbstractPower {
    private static final String POWER_ID = "ManaBurnPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("ManaBurnPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(DefaultMod.makePowerPath("Mana_Burn_placeholder_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(DefaultMod.makePowerPath("Mana_Burn_placeholder_32.png"));

    private AbstractCreature source;
    private int manaBurnIntensity;
    private AbstractMonster m;

    public ManaBurnPower(AbstractCreature owner, AbstractCreature source, int manaBurn) {
        this.name = NAME;
        this.ID = "ManaBurnPower";
        this.owner = owner;
        this.amount = manaBurn;
        this.source = source;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.type = AbstractPower.PowerType.DEBUFF;
        this.m = (AbstractMonster)this.owner;

        this.manaBurnIntensity = (int)Math.ceil(owner.maxHealth * 0.01D * this.amount);
        this.isTurnBased = true;
        updateDescription();
    }

    private void updateDamage() {
        if (this.amount > 3) {
            this.amount = 3;
        }
        this.manaBurnIntensity = (int)Math.ceil(this.owner.maxHealth * 0.01D * this.amount);
        updateDescription();
    }

    public void updateDescription() {
        if ((int)Math.ceil(this.m.maxHealth*0.01D*this.amount*3) == 1){
            this.description = DESCRIPTION[0] + (int) Math.ceil(this.m.maxHealth * 0.01D * this.amount) + DESCRIPTION[2] + this.amount;
        } else {
            this.description = DESCRIPTION[0] + (int) Math.ceil(this.m.maxHealth * 0.01D * this.amount) + DESCRIPTION[1] + (int) Math.ceil(this.m.maxHealth * 0.01D * this.amount * 3) + DESCRIPTION[2] + this.amount;
        }
    }

    public void stackPower(int stackAmount) {
        this.amount += stackAmount;
        if (this.amount > 2) {
            this.amount = 3;
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.source, "ManaBurnPower"));
            DefaultMod.logger.info("Removing Mana burn, applying Manablaze.");
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.source, new ManablazePower(this.owner, this.source)));
        }
        updateDamage();

    }

    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new ManaBurnAction(this.owner, this.source, this.amount));
    }
}
