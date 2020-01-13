package theWeaponMaster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.util.TextureLoader;

public class TauntPower extends AbstractPower {

    public static final String POWER_ID = TheWeaponMaster.makeID(TauntPower.class.getSimpleName());
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(TauntPower.class.getSimpleName());
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture(TheWeaponMaster.makePowerPath("taunt_placeholder_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(TheWeaponMaster.makePowerPath("taunt_placeholder_32.png"));

    public AbstractCreature source;
    public AbstractMonster m;
    private static byte originalMove;
    private static AbstractMonster.Intent originalIntent;
    private int baseDamage;

    public TauntPower(AbstractCreature owner, AbstractCreature source) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.m = (AbstractMonster) owner;
        this.source = source;
        baseDamage = source.maxHealth / 10;
        this.amount = baseDamage + source.getPower(ViciousPower.POWER_ID).amount;

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
        this.m.setMove((byte) -2, AbstractMonster.Intent.ATTACK, amount);
        this.m.createIntent();
        updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTION[0];
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        amount = baseDamage + source.getPower(ViciousPower.POWER_ID).amount;
        super.onPlayCard(card, m);
    }

    @Override
    public void atEndOfRound() {
        updateDescription();
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(source, amount, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, source, this));
        this.m.setMove(originalMove, originalIntent);
        this.m.createIntent();
    }
}
