package theWeaponMaster.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
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

public class RedirectBlowsPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = TheWeaponMaster.makeID(RedirectBlowsPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(RedirectBlowsPower.class.getSimpleName());
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));
    public AbstractCreature source;
    private float damageReduction = 25;

    public RedirectBlowsPower(final AbstractCreature owner, final AbstractCreature source, boolean upgraded) {
        this.name = NAME;
        this.ID = POWER_ID;

        this.owner = owner;
        this.source = source;
        if (upgraded) {
            damageReduction += 8;
        }

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        return (damage * (1 - damageReduction / 100));
    }


    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount > owner.currentBlock) {
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(owner, owner, damageAmount - owner.currentBlock));
        }
        return damageAmount;
    }

    @Override
    public void atEndOfRound() {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + (int) damageReduction + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new CommonPower(owner, source, amount);
    }

}
