package theWeaponMaster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theWeaponMaster.util.TextureLoader;

import static theWeaponMaster.DefaultMod.makePowerPath;

public class SeveredPainPower extends AbstractPower {


    public static final String POWER_ID = "SeveredPainPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("SeveredPainPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;
    private int turnsLeft;

    public SeveredPainPower(final AbstractCreature owner, int turns) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        turnsLeft = turns;

        type = AbstractPower.PowerType.BUFF;
        isTurnBased = false;

        Texture tex32 = TextureLoader.getTexture(makePowerPath("severed_pain_placeholder_32.png"));
        Texture tex84 = TextureLoader.getTexture(makePowerPath("severed_pain_placeholder_32.png"));

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        return damage / 2;
    }

    @Override
    public void atEndOfRound() {
        turnsLeft--;
        if (turnsLeft <= 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }
}
