package theWeaponMaster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.util.TextureLoader;

import static theWeaponMaster.TheWeaponMaster.makePowerPath;

public class StaggerPower extends AbstractPower {

    public static final String POWER_ID = TheWeaponMaster.makeID(StaggerPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(StaggerPower.class.getSimpleName());
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;
    public float staggerAmount;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("stagger_placeholder_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("stagger_placeholder_32.png"));

    public StaggerPower(final AbstractCreature owner, final AbstractCreature source, int staggerAmount, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.staggerAmount = (float) (0.75 - ((float) staggerAmount / 100));
        this.amount = amount;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        /*if (!this.owner.isPlayer && AbstractDungeon.player.hasRelic("ShockwaveModulatorRelic")){
            if (this.staggerAmount< 0.25F){
                this.staggerAmount = 0.25F;
            }
        } else*/
        if (this.staggerAmount < 0.50F) {
            this.staggerAmount = 0.50F;
        }

        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;

        updateDescription();
    }

    //TODO: Fix the single turn remaning bug!

    public void atEndOfRound() {
        /*if (!this.owner.isPlayer && AbstractDungeon.player.hasRelic("ShockwaveModulatorRelic")) {
            if (this.turns <=1){
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "StaggerPower"));
            }else
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, "StaggerPower", 2));
        }
        else {*/
        if (this.amount <= 1) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        } else
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this, 1));
        //}
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTION[0] + (int) ((1 - this.staggerAmount) * 100) + DESCRIPTION[1] + (int) ((1 - this.staggerAmount) * 100) + DESCRIPTION[2];
    }

    @Override
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type) {
        return damage * staggerAmount;
    }

    @Override
    public float modifyBlock(float blockAmount) {
        return blockAmount * staggerAmount;
    }
}