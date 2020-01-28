package theWeaponMaster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
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

public class RedirectingPower extends AbstractPower {
    public static final String POWER_ID = TheWeaponMaster.makeID(RedirectingPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(RedirectingPower.class.getSimpleName());
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));
    public AbstractCreature source;
    private float damageReduction = 25;
    private boolean justApplied;

    public RedirectingPower(final AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;

        this.amount = amount;

        this.owner = owner;

        type = PowerType.BUFF;
        isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.justApplied = true;

        updateDescription();
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        return damage * 0.75F;
    }

    @Override
    public void onInitialApplication() {
        if (owner.hasPower(MalicePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, MalicePower.POWER_ID));
        }
        if (owner.hasPower(RecklessnessPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, RecklessnessPower.POWER_ID));
        }
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return damage * 0.85F;
        }
        return super.atDamageGive(damage, type);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount > owner.currentBlock) {
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(owner, owner, damageAmount - owner.currentBlock));
        }
        return damageAmount;
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (this.justApplied) {
            this.justApplied = false;
        } else {
            this.flash();
            if (this.amount == 0) {
                this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
            } else {
                this.addToBot(new ReducePowerAction(this.owner, this.owner, this, 1));
            }

        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + (int) damageReduction + DESCRIPTIONS[1];
    }


}
