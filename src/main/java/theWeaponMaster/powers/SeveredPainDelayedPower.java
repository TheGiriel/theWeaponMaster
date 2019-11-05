package theWeaponMaster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theWeaponMaster.util.TextureLoader;

import static theWeaponMaster.DefaultMod.makePowerPath;

public class SeveredPainDelayedPower extends AbstractPower {


    public static final String POWER_ID = "SeveredPainDelayedPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("SeveredPainDelayedPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;
    private int blowback;
    private int turns = 1;

    SeveredPainDelayedPower(final AbstractCreature owner, int damage) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        blowback = damage;

        type = AbstractPower.PowerType.BUFF;
        isTurnBased = false;

        Texture tex32 = TextureLoader.getTexture(makePowerPath("severed_pain_placeholder_32.png"));
        Texture tex84 = TextureLoader.getTexture(makePowerPath("severed_pain_placeholder_32.png"));

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }
    public void atStartOfTurn(){
        turns--;
    }

    public void updateDescription(){
        description = DESCRIPTION[0] + blowback + DESCRIPTION[1];
    }

    @Override
    public void atEndOfRound() {
        if (turns<= 0) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(owner, new DamageInfo(owner, blowback)));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this));
        }
        turns--;
    }
}
