package theWeaponMaster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.tempCards.*;
import theWeaponMaster.util.TextureLoader;

import java.util.Random;

import static theWeaponMaster.TheWeaponMaster.makePowerPath;

public class ImprovisedToolsPower extends AbstractPower {

    public static final String POWER_ID = TheWeaponMaster.makeID(ImprovisedToolsPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ImprovisedToolsPower.class.getSimpleName());
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));
    private AbstractCard toolsCard = new ImprovisedAmmo();
    private boolean upgradedTools;

    public ImprovisedToolsPower(final AbstractCreature owner, boolean upgraded) {
        this.name = NAME;
        this.ID = POWER_ID;

        this.owner = owner;

        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.upgradedTools = upgraded;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        if (upgradedTools) {
            atStartOfTurnPostDraw();
        }
    }

    @Override
    public void atStartOfTurnPostDraw() {
        Random random = new Random();
        switch (random.nextInt(5) + 1) {
            case 1:
                toolsCard = new ImprovisedAmmo();
                break;
            case 2:
                toolsCard = new ImprovisedShank();
                break;
            case 3:
                toolsCard = new ImprovisedSpear();
                break;
            case 4:
                toolsCard = new ImprovisedShield();
                break;
            case 5:
                toolsCard = new ImprovisedFlashbang();
                break;
        }
        if (upgradedTools) {
            toolsCard.upgrade();
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(toolsCard));
        } else AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(toolsCard));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}
