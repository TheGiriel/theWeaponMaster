package theWeaponMaster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.actions.ManaBurnAction;
import theWeaponMaster.util.TextureLoader;

public class ManablazePower extends AbstractPower{
    private static final String POWER_ID = DefaultMod.makeID("ManablazePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("ManablazePower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(DefaultMod.makePowerPath("manablaze_placeholder_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(DefaultMod.makePowerPath("manablaze_placeholder_32.png"));

    private AbstractCreature source;
    private int manablazeIntensity;
    private AbstractMonster m;

    ManablazePower(AbstractCreature owner, AbstractCreature source) {
        this.name = NAME;
        this.ID = "ManablazePower";
        this.owner = owner;
        this.source = source;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.type = AbstractPower.PowerType.DEBUFF;
        this.m = (AbstractMonster)this.owner;

        this.manablazeIntensity = (int)Math.ceil(this.owner.maxHealth * 0.03D);


        updateDescription();
    }

    public void updateDescription(){
        if ((int)Math.ceil(this.m.maxHealth*0.03D*this.amount*3) == 1){
            this.description = DESCRIPTION[0] + (int) Math.ceil(this.m.maxHealth * 0.03D * this.amount) + DESCRIPTION[2];
        } else {
            this.description = DESCRIPTION[0] + (int) Math.ceil(this.m.maxHealth * 0.03D) + DESCRIPTION[1] + (int) Math.ceil(this.m.maxHealth * 0.03D * 3) + DESCRIPTION[2];
        }
    }

    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new ManaBurnAction(this.owner, this.source,3));
    }
}