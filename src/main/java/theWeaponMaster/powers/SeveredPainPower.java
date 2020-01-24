package theWeaponMaster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.relics.ManaWhetstoneRelic;
import theWeaponMaster.util.TextureLoader;

import java.util.ArrayList;

import static theWeaponMaster.TheWeaponMaster.makePowerPath;

public class SeveredPainPower extends AbstractPower {


    public static final String POWER_ID = TheWeaponMaster.makeID(SeveredPainPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("SeveredPainPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;
    private boolean manaWhetstone = false;
    int[] blowback;
    private int delayedDamage = 0;

    public SeveredPainPower(final AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        if (AbstractDungeon.player.hasRelic(ManaWhetstoneRelic.ID)){
            manaWhetstone = true;
            ArrayList<AbstractMonster> m = AbstractDungeon.getCurrRoom().monsters.monsters;
            blowback = new int[m.size()];
        }
        this.amount = 0;

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

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (manaWhetstone) {
            amount += (damageAmount / 3);
            for (int i = 0; i < blowback.length; i++) {
                blowback[i] += amount;
            }
            return damageAmount / 3;
        }
        amount += (damageAmount / 2);
        return damageAmount / 2;
    }

    @Override
    public void atEndOfRound() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new SeveredPainDelayedPower(owner, amount, 1)));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this));
    }
}
