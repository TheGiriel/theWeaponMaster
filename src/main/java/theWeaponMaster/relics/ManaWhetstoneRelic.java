package theWeaponMaster.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.cards.AtroposSeveredPain;
import theWeaponMaster.cards.AtroposSeveredScissors;
import theWeaponMaster.cards.AtroposSeveredSource;
import theWeaponMaster.cards.Not_finished.AtroposSeveredPath;
import theWeaponMaster.cards.Not_finished.AtroposSeveredSoul;
import theWeaponMaster.util.TextureLoader;

import java.util.HashSet;

import static theWeaponMaster.DefaultMod.makeRelicOutlinePath;
import static theWeaponMaster.DefaultMod.makeRelicPath;

public class ManaWhetstoneRelic extends CustomRelic {

    public static final String ID = DefaultMod.makeID(ManaWhetstoneRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("mana_whetstone_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("mana_whetstone_relic.png"));

    public static boolean level1 = true;
    public static boolean level2 = false;
    public static boolean level3 = false;
    public static boolean level4 = false;
    public static boolean level5 = false;
    public static HashSet<String> weaponUpgrade = new HashSet<>();

    public ManaWhetstoneRelic() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
        setWeaponUpgrade();
    }

    public static HashSet<String> getWeaponUpgrade() {
        return weaponUpgrade;
    }

    public void setWeaponUpgrade() {
        weaponUpgrade.clear();
        if (level1) {
            weaponUpgrade.add(AtroposSeveredScissors.ID);
        }
        if (level2) {
            weaponUpgrade.add(AtroposSeveredSource.ID);
        }
        if (level3) {
            weaponUpgrade.add(AtroposSeveredPain.ID);
        }
        if (level4) {
            weaponUpgrade.add(AtroposSeveredPath.ID);
        }
        if (level5) {
            weaponUpgrade.add(AtroposSeveredSoul.ID);
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
