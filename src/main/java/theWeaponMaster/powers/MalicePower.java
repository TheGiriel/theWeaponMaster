package theWeaponMaster.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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

public class MalicePower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = TheWeaponMaster.makeID(MalicePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(MalicePower.class.getSimpleName());
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));
    private boolean justApplied;

    public MalicePower(final AbstractCreature owner, int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;

        type = PowerType.BUFF;
        isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.justApplied = true;

        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        ViciousPower.berserkerStanceBonus = 1;
        if (owner.hasPower(RecklessnessPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, RecklessnessPower.POWER_ID));
        }
        if (owner.hasPower(RedirectingPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, RedirectingPower.POWER_ID));
        }
    }


    @Override
    public void onInflictDamage(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (owner.hasPower(ProtectiveBladePower.POWER_ID)) {
            return;
        }
        if (info.type == DamageInfo.DamageType.NORMAL) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ViciousPower(AbstractDungeon.player, 1)));
        }
        super.onInflictDamage(info, damageAmount, target);
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
    public void onRemove() {
        ViciousPower.berserkerStanceBonus = 0;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + 1 + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new MalicePower(owner, amount);
    }
}