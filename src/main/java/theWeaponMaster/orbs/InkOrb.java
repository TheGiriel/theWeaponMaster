package theWeaponMaster.orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbPassiveEffect;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.util.TextureLoader;

import static theWeaponMaster.DefaultMod.makeOrbPath;

public class InkOrb extends AbstractOrb {

    private static final String ORB_ID = DefaultMod.makeID(InkOrb.class.getSimpleName());
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    private static final String[] DESC = orbString.DESCRIPTION;

    private static Texture img1;
    private static Texture img2;
    // Animation Rendering Numbers - You can leave these at default, or play around with them and see what they change.
    private float vfxTimer = 1.0f;
    private float vfxIntervalMin = 0.1f;
    private float vfxIntervalMax = 0.4f;
    private static final float ORB_WAVY_DIST = 0.06f;
    private static final float PI_4 = 12.566371f;
    private int inkStrength;

    public InkOrb(int initialInk){
        this.ID = ORB_ID;
        this.name = orbString.NAME;
        if (img1 == null){
            img1 = TextureLoader.getTexture(makeOrbPath("ink_orb_white.png"));
            img2 = TextureLoader.getTexture(makeOrbPath("ink_orb_purple.png"));
        }
        this.inkStrength = basePassiveAmount = initialInk;
        this.description = DESC[0] + this.inkStrength + DESC[1] + this.inkStrength + DESC[2];
    }

    @Override
    public void updateDescription() {
        applyFocus();
        this.description = DESC[0] + this.inkStrength + DESC[1] + this.inkStrength + DESC[2];
    }

    @Override
    public void onEvoke() {
        AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, this.inkStrength));
    }

    @Override
    public AbstractOrb makeCopy() {
        return new InkOrb(this.passiveAmount);
    }

    public void onEndOfTurn(){
        AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, this.inkStrength/2));
    }

    public void addInk(int newInk){
        this.inkStrength += newInk;
        passiveAmount = evokeAmount = this.inkStrength;
        updateDescription();
    }

    public void updateAnimation(){
        super.updateAnimation();
        angle += Gdx.graphics.getDeltaTime() * 45.0f;
        vfxTimer -= Gdx.graphics.getDeltaTime();
        if (vfxTimer < 0.0f) {
            AbstractDungeon.effectList.add(new DarkOrbPassiveEffect(cX, cY));
            vfxTimer = MathUtils.random(vfxIntervalMin, vfxIntervalMax);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(new Color(1.0f, 1.0f, 1.0f, c.a / 2.0f));
        sb.draw(img1, cX - 48.0f, cY - 48.0f + bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, scale + MathUtils.sin(angle / PI_4) * ORB_WAVY_DIST * Settings.scale, scale, angle, 0, 0, 96, 96, false, false);
        sb.draw(img2, cX - 48.0f, cY - 48.0f + bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, scale, scale + MathUtils.sin(angle / PI_4) * ORB_WAVY_DIST * Settings.scale, angle, 0, 0, 96, 96, false, false);
        sb.setColor(new Color(1.0f, 1.0f, 1.0f, this.c.a / 2.0f));
        sb.setBlendFunction(770, 1);

        sb.setBlendFunction(770, 771);
        renderText(sb);
        hb.render(sb);
    }

    @Override
    public void playChannelSFX() {
        CardCrawlGame.sound.play("ORB_DARK_CHANNEL", 0.1F);
    }
}
