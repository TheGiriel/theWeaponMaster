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
    public AbstractCreature source;

    public static final String POWER_ID = "LaceratePower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public LaceratePower(AbstractCreature owner, AbstractCreature source, int bleedStack){
        name = NAME;
        ID = POWER_ID;

        bleedStack += bleedStack;
        this.owner = owner;
        this.amount = (int)Math.ceil(owner.maxHealth*0.02*bleedStack);
        if (bleedStack > 3){
            this.amount = (int)Math.ceil(owner.maxHealth*0.06);
        }
        this.source = source;

        this.type = PowerType.DEBUFF;
        isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.description = DESCRIPTION[0] + this.amount + DESCRIPTION[1];
        updateDescription();
    }

    @Override
    public void atStartOfTurn(){
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flashWithoutSound();
            AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this.owner, this.source, this.amount, AbstractGameAction.AttackEffect.POISON));
        }
    }

}
