package theWeaponMaster.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.cards.*;
import theWeaponMaster.util.TextureLoader;

import java.util.HashSet;

import static theWeaponMaster.DefaultMod.makeRelicOutlinePath;
import static theWeaponMaster.DefaultMod.makeRelicPath;

public class SplinteringSteelRelic extends CustomRelic {

    public static final String ID = DefaultMod.makeID("SplinteringSteelRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("splintering_steel_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("splintering_steel_relic.png"));

    public static boolean level1 = true;
    public static boolean level2 = false;
    public static boolean level3 = false;
    public static boolean level4 = false;
    public static boolean level5 = false;
    public static HashSet<String> weaponUpgrade = new HashSet<>();

    public SplinteringSteelRelic() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.SOLID);
        setWeaponUpgrade();
    }

    public static HashSet<String> getWeaponUpgrade() {
        return weaponUpgrade;
    }

    public void setWeaponUpgrade() {
        weaponUpgrade.clear();
        if (level1) {
            weaponUpgrade.add(FenrirLacerate.ID);
        }
        if (level2) {
            weaponUpgrade.add(FenrirShieldEater.ID);
        }
        if (level3) {
            weaponUpgrade.add(FenrirDefensiveStance.ID);
        }
        if (level4) {
            weaponUpgrade.add(FenrirHeavySwing.ID);
        }
        if (level5) {
            weaponUpgrade.add(FenrirUnleashed.ID);
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
}
