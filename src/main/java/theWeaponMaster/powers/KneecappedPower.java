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
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.util.TextureLoader;

import static theWeaponMaster.TheWeaponMaster.makePowerPath;

public class KneecappedPower extends AbstractPower implements HealthBarRenderPower {

    public static final Color bleed = new Color((float) (179 / 255), (float) (98 / 255), (float) (0 / 255), 1.0F);
    public static final String POWER_ID = TheWeaponMaster.makeID(KneecappedPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(KneecappedPower.class.getSimpleName());
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));
    private static double bleedfactor = 0.05;
    public AbstractCreature source;
    private int bleedDamage;

    public KneecappedPower(AbstractMonster m) {
        name = NAME;
        ID = POWER_ID;

        this.owner = m;
        this.amount = 3;

        this.bleedDamage = (int) Math.ceil((this.owner.maxHealth - this.owner.currentHealth) * bleedfactor);


        type = PowerType.DEBUFF;
        isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }


    private void updateDamage() {
        this.bleedDamage = (int) Math.ceil((this.owner.maxHealth - this.owner.currentHealth) * bleedfactor);
        getHealthBarAmount();
        updateDescription();
    }

    public int getBleedDamage() {
        return bleedDamage;
    }

    @Override
    public void atStartOfTurn() {
        updateDamage();
        AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this.owner, this.source, getBleedDamage(), AbstractGameAction.AttackEffect.POISON));
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
        this.description = DESCRIPTIONS[0] + (int) (bleedfactor * 100) + DESCRIPTIONS[1] + getBleedDamage() + DESCRIPTIONS[2] + amount + DESCRIPTIONS[3];
    }

    @Override
    public int getHealthBarAmount() {
        return bleedDamage;
    }

    @Override
    public Color getColor() {
        if (owner.hasPower(NickedPower.POWER_ID) && owner.hasPower(LaceratePower.POWER_ID)) {
            return Color.valueOf("#6d0000");
        } else if (owner.hasPower(NickedPower.POWER_ID) || owner.hasPower(LaceratePower.POWER_ID)) {
            return Color.valueOf("#860000");
        }
        return Color.valueOf("#9f0000");
    }
}
