package theWeaponMaster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.util.TextureLoader;

public class IntimidatePower extends AbstractPower {

    private static final String POWER_ID = DefaultMod.makeID(IntimidatePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("IntimidatePower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(DefaultMod.makePowerPath("leviathan_placeholder_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(DefaultMod.makePowerPath("leviathan_placeholder_32.png"));

    public AbstractCreature source;
    public AbstractMonster m;
    private int bonusBlock = 0;
    private byte originalMove;
    private AbstractMonster.Intent originalIntent;

    public IntimidatePower(AbstractCreature owner, AbstractCreature source) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.m = (AbstractMonster) owner;
        this.source = source;

        type = AbstractPower.PowerType.DEBUFF;
        isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        originalMove = this.m.nextMove;
        originalIntent = this.m.intent;
        if (m.hasPower("TauntPower")) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, "TauntPower"));
        }
        this.m.setMove((byte) -2, AbstractMonster.Intent.DEFEND);
        this.m.createIntent();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTION[0] + (10 + getBonusBlock());
    }

    public int getBonusBlock() {
        if (source.hasPower("ViciousPower")) {
            return bonusBlock = source.getPower("ViciousPower").amount / 2;
        } else return 0;

    }

    public void atEndOfRound() {
        updateDescription();
        AbstractDungeon.actionManager.addToTop(new GainBlockAction(owner, owner, 10 + bonusBlock));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, source, this));
        this.m.setMove(originalMove, originalIntent);
        this.m.createIntent();
    }
}