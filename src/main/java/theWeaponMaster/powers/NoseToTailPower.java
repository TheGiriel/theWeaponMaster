package theWeaponMaster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.util.TextureLoader;

import static theWeaponMaster.DefaultMod.makePowerPath;

public class NoseToTailPower extends TwoAmountPower {

    public static final String POWER_ID = DefaultMod.makeID(NoseToTailPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(NoseToTailPower.class.getSimpleName());
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    private static int damageTaken;
    private static int noseToTailHP;
    private static int tempHPModifier;
    private static int totalTemp;
    private static int healAmount;
    public AbstractPlayer player = AbstractDungeon.player;

    public NoseToTailPower(AbstractMonster target, int magicNumber) {

        this.name = NAME;
        this.ID = POWER_ID;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.amount = 0;
        this.amount2 = 0;
        this.type = AbstractPower.PowerType.DEBUFF;

        updateDescription();
        healAmount = amount2 / 3;

        tempHPModifier = magicNumber;
        owner = target;
    }


    public void updateDescription() {
        description = DESCRIPTION[0] + tempHPModifier + DESCRIPTION[1] + amount2 + DESCRIPTION[2];
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        damageTaken += damageAmount;
        amount2 = (damageTaken - (damageTaken % tempHPModifier)) / tempHPModifier;
        amount = damageTaken;
        updateDescription();
        return super.onAttacked(info, damageAmount);
    }

    @Override
    public void onDeath() {
        AbstractDungeon.actionManager.addToBottom(new HealAction(player, owner, healAmount));
        AbstractDungeon.actionManager.addToBottom(new AddTemporaryHPAction(AbstractDungeon.player, owner, amount2 - healAmount));
    }

    @Override
    public void atEndOfRound() {
        AbstractDungeon.actionManager.addToBottom(new AddTemporaryHPAction(AbstractDungeon.player, owner, amount2));
        totalTemp = amount = amount2 = healAmount = 0;
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this));
    }
}
