package theWeaponMaster.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.cards.LeviathanCrush;
import theWeaponMaster.cards.LeviathanGroundSplitter;
import theWeaponMaster.cards.Not_finished.LeviathanDeepImpact;
import theWeaponMaster.cards.Not_finished.LeviathanEarthquake;
import theWeaponMaster.cards.Not_finished.LeviathanEject;
import theWeaponMaster.util.TextureLoader;

import java.util.HashSet;

import static theWeaponMaster.DefaultMod.*;

public class ShockwaveModulatorRelic extends CustomRelic {

    public static final String ID = DefaultMod.makeID(ShockwaveModulatorRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("shockwave_modulator_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("shockwave_modulator_relic.png"));

    public static boolean level1 = true;
    public static boolean level2 = false;
    public static boolean level3 = false;
    public static boolean level4 = false;
    public static boolean level5 = false;
    public static HashSet<String> weaponUpgrade = new HashSet<>();

    public ShockwaveModulatorRelic() {
        super(ID, IMG, OUTLINE, AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.SOLID);
        setWeaponUpgrade();
    }

    public static HashSet<String> getWeaponUpgrade() {
        return weaponUpgrade;
    }

    public void setWeaponUpgrade() {
        weaponUpgrade.clear();
        if(level1) {
            logger.info("adding Crush");
            weaponUpgrade.add(LeviathanCrush.ID);
        }
        if(level2) {
            logger.info("adding Eject");
            weaponUpgrade.add(LeviathanEject.ID);
            //weaponSetHash.add(LeviathanReload.ID);//unlisted
        }
        if(level3) {
            weaponUpgrade.add(LeviathanGroundSplitter.ID);
        }
        if(level4) {
            weaponUpgrade.add(LeviathanDeepImpact.ID);
        }
        if(level5) {
            weaponUpgrade.add(LeviathanEarthquake.ID);
        }
    }

    @Override
    public void atBattleStartPreDraw() {
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
