package theWeaponMaster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.util.TextureLoader;

public class HungeringPower extends AbstractPower {

    private static final String POWER_ID = DefaultMod.makeID(HungeringPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("HungeringPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(DefaultMod.makePowerPath("designate_placeholder_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(DefaultMod.makePowerPath("designate_placeholder_32.png"));

    private AbstractCreature source;
    private int countDown;

    public HungeringPower(AbstractCreature owner, AbstractCreature source, int countDown) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = Math.min(5, Math.max(1, (owner.currentHealth / 20 + 1)));
        this.source = source;
        this.countDown = countDown;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        this.description = DESCRIPTION[0];
        updateDescription();
        this.type = AbstractPower.PowerType.DEBUFF;
        this.isTurnBased = true;
    }

    @Override
    public void onDeath() {
        AbstractDungeon.player.increaseMaxHp(amount, true);
    }

    @Override
    public void atEndOfRound() {
        countDown--;
        if (countDown < 1) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, source, this));
        }
    }
}
