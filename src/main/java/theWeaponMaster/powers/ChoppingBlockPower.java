package theWeaponMaster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.util.TextureLoader;

public class ChoppingBlockPower extends TwoAmountPower {

    public static final String POWER_ID = TheWeaponMaster.makeID(ChoppingBlockPower.class.getSimpleName());
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ChoppingBlockPower.class.getSimpleName());
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(TheWeaponMaster.makePowerPath("designate_placeholder_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(TheWeaponMaster.makePowerPath("designate_placeholder_32.png"));

    private AbstractCreature source;
    static int baseInc = 0;

    public ChoppingBlockPower(AbstractCreature owner, AbstractCreature source, int hungryBoost) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = baseInc = hungryBoost;
        this.source = source;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.type = AbstractPower.PowerType.DEBUFF;
        this.isTurnBased = true;

        updateDescription();
    }

    @Override
    public void updateDescription() {

        this.description = DESCRIPTION[0] + amount + DESCRIPTION[1] + amount2;
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        amount2 += damageAmount;
        amount = (int) Math.floor((double) amount2 / 20) + baseInc;
        updateDescription();
        return super.onAttacked(info, damageAmount);
    }

    @Override
    public void onDeath() {
        AbstractDungeon.player.increaseMaxHp(amount, true);
    }

    @Override
    public void atEndOfRound() {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, source, this));
    }
}
