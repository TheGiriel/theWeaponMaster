package theWeaponMaster.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.util.TextureLoader;

import java.util.HashSet;

import static theWeaponMaster.DefaultMod.*;

public class ShockwaveModulatorRelic extends CustomRelic {

    public static final String ID = DefaultMod.makeID("ShockwaveModulatorRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("shockwave_modulator_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("shockwave_modulator_relic.png"));
    public static boolean level1 = true;
    public static boolean level2 = false;
    public static boolean level3 = false;
    public static boolean level4 = false;
    public static boolean level5 = false;

    private HashSet<String> leviathanUpgrade = new HashSet<>();

    public ShockwaveModulatorRelic() {
        super(ID, IMG, OUTLINE, AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.SOLID);
        leviathanUpgrade.clear();
        if(level1) {
            logger.info("adding Crush");
            leviathanUpgrade.add("theWeaponMaster:LeviathanCrush");
        }
        if(level2) {
            logger.info("adding Eject");
            leviathanUpgrade.add("theWeaponMaster:LeviathanEject");
            //weaponSetHash.add("theWeaponMaster:LeviathanReload");//unlisted
        }
        if(level3) {
            leviathanUpgrade.add("theWeaponMaster:LeviathanGroundSplitter");
        }
        if(level4) {
            leviathanUpgrade.add("theWeaponMaster:LeviathanDeepImpact");
        }
        if(level5) {
            leviathanUpgrade.add("theWeaponMaster:LeviathanEarthquake");
        }
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
        if (leviathanUpgrade.contains(c.cardID)) {
            c.upgrade();
            leviathanUpgrade.clear();
        }
    }
}
