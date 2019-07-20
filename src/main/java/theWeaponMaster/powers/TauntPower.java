package theWeaponMaster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.util.TextureLoader;

public class TauntPower extends AbstractPower {

    private static final String POWER_ID = "TauntPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("TauntPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture(DefaultMod.makePowerPath("taunt_placeholder_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(DefaultMod.makePowerPath("taunt_placeholder_32.png"));

    public AbstractCreature source;
    public AbstractMonster m;
    private byte originalMove;
    private AbstractMonster.Intent originalIntent;
    private DamageInfo.DamageType test = DamageInfo.DamageType.NORMAL;

    public TauntPower(AbstractCreature owner, AbstractCreature source) {
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
        //TODO: Lagavulin special case.
        if (m.hasPower("IntimidatePower")) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, "IntimidatePower"));
        }

        this.m.setMove((byte) -2, AbstractMonster.Intent.ATTACK, (int) Math.ceil(source.maxHealth / 10));
        this.m.createIntent();
    }

    public void updateDescription() {
        this.description = DESCRIPTION[0];
    }

    @Override
    public void atEndOfRound() {
        updateDescription();
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(source, (int) Math.ceil(source.maxHealth / 10), DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, source, this));
        this.m.setMove(originalMove, originalIntent);
        this.m.createIntent();
    }
}
