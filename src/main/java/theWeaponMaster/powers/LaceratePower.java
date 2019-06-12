package theWeaponMaster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.util.TextureLoader;

import static theWeaponMaster.DefaultMod.makePowerPath;

public class LaceratePower extends AbstractPower {

    private static final String POWER_ID = "LaceratePower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    private AbstractCreature source;
    private int bleedDamage;

    public LaceratePower(AbstractCreature owner, AbstractCreature source, int bleedStack){
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = bleedStack;
        //this.bleedDamage = (int)Math.ceil(owner.maxHealth*0.02*amount);
        this.source = source;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDamage();
        updateDescription();
        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;
    }

    public void updateDamage(){
        if(this.amount > 3){
            this.amount = 3;
        }
        this.bleedDamage = (int)Math.ceil(owner.maxHealth*0.02*this.amount);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (this.amount<3) {
            this.description = DESCRIPTION[0] + this.amount * 2 + DESCRIPTION[1] + this.bleedDamage + DESCRIPTION[2] + this.amount;
        } else {
            this.description = DESCRIPTION[0] + this.amount*2 + DESCRIPTION[1] + this.bleedDamage + DESCRIPTION[3];
        }
    }

    public void stackPower(int stackAmount){
        DefaultMod.logger.info("updating stack. was " + this.amount);
        this.amount += stackAmount;
        if (this.amount > 3) {
            this.amount = 3;
        }
        this.bleedDamage = (int)Math.ceil(owner.maxHealth*0.02*amount);
        DefaultMod.logger.info("updated damage 2. is " + this.bleedDamage);
        updateDescription();
        DefaultMod.logger.info("updated stack. is " + this.amount);
    }

    @Override
    public void atStartOfTurn(){
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flashWithoutSound();
            AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this.owner, this.source, this.bleedDamage, AbstractGameAction.AttackEffect.POISON));
        }
    }

}
