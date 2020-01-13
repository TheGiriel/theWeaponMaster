package theWeaponMaster.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
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



public class NickedPower extends AbstractPower implements HealthBarRenderPower {
    public static final String POWER_ID = TheWeaponMaster.makeID(NickedPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(NickedPower.class.getSimpleName());
    public AbstractCreature source;
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    private int bleedDamage;

    public NickedPower(final AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;

        this.owner = owner;
        this.amount = 3;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDamage();
        updateDescription();
    }


    private void updateDamage() {
        this.bleedDamage = (int) Math.ceil(this.owner.maxHealth * 0.033D);
        getHealthBarAmount();
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        updateDamage();
        AbstractDungeon.actionManager.addToBottom(new LoseHPAction(owner, owner, bleedDamage, AbstractGameAction.AttackEffect.POISON));
        amount--;
        if (amount <= 0) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(owner, owner, this));
            updateDamage();
        }
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        updateDamage();
        return super.onAttacked(info, damageAmount);
    }

    @Override
    public void stackPower(int stackAmount) {
        amount = 3;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + bleedDamage + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

    @Override
    public int getHealthBarAmount() {
        return bleedDamage;
    }

    @Override
    public Color getColor() {
        return Color.valueOf("#9f0000");
    }
}
