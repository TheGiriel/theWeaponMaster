package theWeaponMaster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theWeaponMaster.actions.ThornBypassAction;
import theWeaponMaster.relics.ManaWhetstoneRelic;
import theWeaponMaster.util.TextureLoader;

import static theWeaponMaster.DefaultMod.makePowerPath;

public class SeveredPainPower extends AbstractPower {


    public static final String POWER_ID = "SeveredPainPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("SeveredPainPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;
    private boolean manaWhetstone = false;
    private int blowback = 0;

    public SeveredPainPower(final AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        //this.amount = 0;
        if (AbstractDungeon.player.hasRelic(ManaWhetstoneRelic.ID)){
            manaWhetstone = true;
        }

        type = AbstractPower.PowerType.BUFF;
        isTurnBased = true;

        Texture tex32 = TextureLoader.getTexture(makePowerPath("severed_pain_placeholder_32.png"));
        Texture tex84 = TextureLoader.getTexture(makePowerPath("severed_pain_placeholder_32.png"));

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void updateDescription(){
        if (manaWhetstone){
            description = DESCRIPTION[1];
        } else {
            description = DESCRIPTION[0];
        }
    }

    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        /*if (manaWhetstone){
            blowback += (int) (damage/3);
            this.amount += blowback;
            return damage /3;
        }
        blowback += (int) (damage /2);
        this.amount += blowback;*/
        return damage / 2;
    }

    @Override
    public void atEndOfRound() {
        /*AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new SeveredPainDelayedPower(owner, blowback)));
        if (manaWhetstone) {
            for (AbstractMonster c : AbstractDungeon.getMonsters().monsters) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(c, new DamageInfo(owner, blowback, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SMASH));
            }
        }*/
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this));
    }
}
