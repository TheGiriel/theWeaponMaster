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

import static theWeaponMaster.TheWeaponMaster.makePowerPath;

public class SeveredPainDelayedPower extends AbstractPower {


    public static final String POWER_ID = "SeveredPainDelayedPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("SeveredPainDelayedPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;
    int turns;
    private int delayed;

    SeveredPainDelayedPower(final AbstractCreature owner, int amount, int turns) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        delayed = this.amount = amount;

        type = AbstractPower.PowerType.DEBUFF;
        isTurnBased = false;

        Texture tex32 = TextureLoader.getTexture(makePowerPath("severed_pain_placeholder_32.png"));
        Texture tex84 = TextureLoader.getTexture(makePowerPath("severed_pain_placeholder_32.png"));

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void updateDescription(){
        description = DESCRIPTION[0] + amount + DESCRIPTION[1];
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (turns <= 1) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(owner, new DamageInfo(owner, delayed, DamageInfo.DamageType.HP_LOSS)));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }
}
