package theWeaponMaster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.util.TextureLoader;

import static theWeaponMaster.TheWeaponMaster.makePowerPath;


public class RecklessnessPower extends AbstractPower {
    public static final String POWER_ID = TheWeaponMaster.makeID(RecklessnessPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));
    public AbstractCreature source;

    public RecklessnessPower(final AbstractCreature owner, final AbstractCreature source) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = Math.max(1, owner.getPower(ViciousPower.POWER_ID).amount / 10);
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(owner, 1));
    }

    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        if (amount <= 0) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(owner, owner, this));
            return;
        }
        amount--;
    }

    @Override
    public void updateDescription() {

    }

}
