package theWeaponMaster.stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceChangeParticleGenerator;
import com.megacrit.cardcrawl.vfx.stance.WrathParticleEffect;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.powers.ViciousPower;

public class BerserkerStance extends AbstractStance {

    public static String STANCE_ID = TheWeaponMaster.makeID(BerserkerStance.class.getSimpleName());
    private static long sfxId;

    public BerserkerStance() {
        this.ID = STANCE_ID;
        this.name = "BerserkerStance";
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ViciousPower(AbstractDungeon.player, 1)));
        return super.atDamageReceive(damage, damageType);
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ViciousPower(AbstractDungeon.player, 1)));
        return super.atDamageGive(damage, type);
    }


    public void updateAnimation() {
        if (!Settings.DISABLE_EFFECTS) {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F) {
                this.particleTimer = 0.05F;
                AbstractDungeon.effectsQueue.add(new WrathParticleEffect());
            }
        }

        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer2 < 0.0F) {
            this.particleTimer2 = MathUtils.random(0.3F, 0.4F);
            AbstractDungeon.effectsQueue.add(new StanceAuraEffect("Wrath"));
        }

    }

    @Override
    public void onEnterStance() {
        if (sfxId != -1L) {
            this.stopIdleSfx();
        }

        CardCrawlGame.sound.play("STANCE_ENTER_WRATH");
        sfxId = CardCrawlGame.sound.playAndLoop("STANCE_LOOP_WRATH");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.SCARLET, true));
        AbstractDungeon.effectsQueue.add(new StanceChangeParticleGenerator(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, "Wrath"));

        ViciousPower.berserkerStanceBonus = 1;
        super.onEnterStance();
    }

    @Override
    public void onExitStance() {
        ViciousPower.berserkerStanceBonus = 0;
        super.onExitStance();
    }

    @Override
    public void updateDescription() {

    }
}
