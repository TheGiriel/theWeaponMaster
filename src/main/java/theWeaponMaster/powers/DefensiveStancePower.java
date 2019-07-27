package theWeaponMaster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.graalvm.compiler.core.common.type.ArithmeticOpTable;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.util.TextureLoader;

public class DefensiveStancePower extends AbstractPower {

    private static final String POWER_ID = "DefensiveStancePower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("DefensiveStancePower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(DefaultMod.makePowerPath("fenrir_placeholder_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(DefaultMod.makePowerPath("fenrir_placeholder_32.png"));
    private int stanceBlock;
    private int blockStrength;
    private int gettingReckless = 5;

    public DefensiveStancePower(AbstractPlayer player, int blockStrength) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = player;
        type = PowerType.BUFF;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        this.description = DESCRIPTION[0];
        updateDescription();
        this.stanceBlock = blockStrength;
        this.blockStrength = blockStrength;
    }

    @Override
    public void onInitialApplication() {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(owner, owner, stanceBlock));
    }

    @Override
    public void onAfterUseCard(AbstractCard c, UseCardAction action) {
        if (c.cardID.equals("theWeaponMaster:FenrirLacerate") || c.cardID.equals("theWeaponMaster:FenrirShieldEater") || c.cardID.equals("theWeaponMaster:FenrirHeavySwing") || c.cardID.equals("theWeaponMaster:FenrirUnleashed")) {
            AbstractDungeon.actionManager.addToBottom(new LoseBlockAction(owner, owner, (blockStrength / 5)));
            stanceBlock -= (stanceBlock / 5);
            gettingReckless--;
            if (gettingReckless == 0 && stanceBlock > 0) {
                AbstractDungeon.actionManager.addToBottom(new LoseBlockAction(owner, owner, (blockStrength - stanceBlock)));
            }
        }
    }

    @Override
    public void atEndOfRound() {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this));
    }
}
