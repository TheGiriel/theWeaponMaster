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
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.util.EnemyIntentInterface;
import theWeaponMaster.util.TextureLoader;

public class IntimidatePower extends AbstractPower implements EnemyIntentInterface {

    public static final String POWER_ID = TheWeaponMaster.makeID(IntimidatePower.class.getSimpleName());
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("IntimidatePower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(TheWeaponMaster.makePowerPath("leviathan_placeholder_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(TheWeaponMaster.makePowerPath("leviathan_placeholder_32.png"));

    public AbstractCreature source;
    public AbstractMonster m;
    private int bonusBlock = 0;
    private static byte originalMove;
    private static AbstractMonster.Intent originalIntent;

    public IntimidatePower(AbstractCreature owner, AbstractCreature source) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.m = (AbstractMonster) owner;
        this.source = source;
        this.amount = source.getPower(ViciousPower.POWER_ID).amount / 3;

        type = AbstractPower.PowerType.DEBUFF;
        isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        updateDescription();
    }

    static byte getOriginalMove() {
        return originalMove;
    }

    static AbstractMonster.Intent getOriginalIntent() {
        return originalIntent;
    }

    @Override
    public void onInitialApplication() {
        /*if (m.hasPower(TauntPower.POWER_ID)) {
            m.setMove(TauntPower.getOriginalMove(), TauntPower.getOriginalIntent());
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, TauntPower.POWER_ID));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this));
            AbstractDungeon.actionManager.addToBottom(new StunMonsterAction(m, AbstractDungeon.player));
        } else*/
        {
            originalMove = this.m.nextMove;
            originalIntent = this.m.intent;
            this.m.setMove((byte) -2, AbstractMonster.Intent.DEFEND);
            this.m.createIntent();
            updateDescription();
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTION[0] + (10 + getBonusBlock()) + DESCRIPTION[1];
    }

    private int getBonusBlock() {
        if (AbstractDungeon.player.hasPower(ViciousPower.POWER_ID)) {
            return bonusBlock = AbstractDungeon.player.getPower(ViciousPower.POWER_ID).amount / 5;
        } else return 0;

    }

    public void atEndOfRound() {
        updateDescription();
        AbstractDungeon.actionManager.addToTop(new GainBlockAction(owner, owner, 10 + getBonusBlock()));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, source, this));
        this.m.setMove(originalMove, originalIntent);
        this.m.createIntent();
    }
}