package theWeaponMaster.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.util.TextureLoader;

import static theWeaponMaster.DefaultMod.makeRelicOutlinePath;
import static theWeaponMaster.DefaultMod.makeRelicPath;

public class ShockwaveModulatorRelic extends CustomRelic {

    public static final String ID = DefaultMod.makeID("ShockwaveModulatorRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("shockwave_modulator_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("shockwave_modulator_relic.png"));

    public ShockwaveModulatorRelic() {

        super(ID, IMG, OUTLINE, AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.SOLID);
    }

    @Override
    public void atBattleStartPreDraw() {
        flash();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    //TODO doesn't work yet
    @Override
    public void onObtainCard(AbstractCard c) {
        if (c.cardID.equals("theWeaponMaster:LeviathanCrush") || c.cardID.equals("theWeaponMaster:LeviathanEarthquake") || c.cardID.equals("theWeaponMaster:LeviathanDeepImpact") || c.cardID.equals("theWeaponMaster:LeviathanReload") || c.cardID.equals("theWeaponMaster:LeviathanGroundSplitter")) {
            c.upgrade();
        }
    }

}
