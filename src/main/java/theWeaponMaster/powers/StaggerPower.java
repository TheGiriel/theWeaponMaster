package theWeaponMaster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.patches.WeaponMasterTags;
import theWeaponMaster.relics.ShockwaveModulatorRelic;
import theWeaponMaster.util.TextureLoader;

import static theWeaponMaster.TheWeaponMaster.makePowerPath;

public class StaggerPower extends AbstractPower {

    public static final String POWER_ID = TheWeaponMaster.makeID(StaggerPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(StaggerPower.class.getSimpleName());
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;

    private float staggerAmount;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("stagger_placeholder_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("stagger_placeholder_32.png"));

    int SMRbonus = 0;

    public StaggerPower(final AbstractCreature owner, AbstractCard card, int staggerAmount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        if (!this.owner.isPlayer && AbstractDungeon.player.hasRelic(ShockwaveModulatorRelic.ID) && card.hasTag(WeaponMasterTags.LEVIATHAN)) {
            SMRbonus = 15;
        }
        this.staggerAmount = (25.0F + SMRbonus + staggerAmount) / (100.0F + staggerAmount);
        this.amount = (int) ((this.staggerAmount) * 100);
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;

        updateDescription();
    }

    //TODO: Fix the single turn remaning bug!

    public void atEndOfRound() {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    @Override
    public void stackPower(int stackAmount) {
        if (((25.0F + SMRbonus + stackAmount) / (100.0F + stackAmount)) > staggerAmount) {
            staggerAmount = ((25.0F + SMRbonus + stackAmount) / (100.0F + stackAmount));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTION[0] + (int) ((this.staggerAmount) * 100) + DESCRIPTION[1] + (int) ((this.staggerAmount) * 100) + DESCRIPTION[2];
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return damage * (1 - staggerAmount);
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        if (owner.hasPower(VulnerablePower.POWER_ID)) {
            return damage * (1 - staggerAmount / 2);
        }
        return damage * (1 + staggerAmount);
    }
}