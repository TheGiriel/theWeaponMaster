package theWeaponMaster.powers;

import basemod.interfaces.CloneablePowerInterface;
import basemod.interfaces.OnPowersModifiedSubscriber;
import basemod.interfaces.PostPowerApplySubscriber;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.patches.WeaponMasterTags;
import theWeaponMaster.util.TextureLoader;

import static theWeaponMaster.TheWeaponMaster.makePowerPath;


public class MarksmanshipPower extends AbstractPower implements CloneablePowerInterface, PostPowerApplySubscriber, OnPowersModifiedSubscriber {
    public static final String POWER_ID = TheWeaponMaster.makeID(MarksmanshipPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(MarksmanshipPower.class.getSimpleName());
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));
    public AbstractCreature source;

    public MarksmanshipPower(final AbstractCreature owner, final AbstractCreature source) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (card.hasTag(WeaponMasterTags.AMMUNITION) && owner.hasPower(DexterityPower.POWER_ID)) {
            card.damage += owner.getPower(DexterityPower.POWER_ID).amount;
            card.baseDamage += owner.getPower(DexterityPower.POWER_ID).amount;
            card.isDamageModified = true;
            card.calculateCardDamage((AbstractMonster) action.target);
        }
        updateDescription();
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {

        return super.atDamageGive(damage, type);
    }

    @Override
    public void atEndOfTurn(final boolean isPlayer) {

    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new CommonPower(owner, source, amount);
    }

    @Override
    public void receivePostPowerApplySubscriber(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        if (abstractPower.ID.equals(DexterityPower.POWER_ID)) {
            amount = owner.getPower(DexterityPower.POWER_ID).amount;
            updateDescription();
        }
    }

    @Override
    public void receivePowersModified() {
        updateDescription();
    }
}
