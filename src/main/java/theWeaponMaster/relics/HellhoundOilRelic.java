package theWeaponMaster.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.legendary_weapons.*;
import theWeaponMaster.util.TextureLoader;

import java.util.HashSet;

import static theWeaponMaster.TheWeaponMaster.makeRelicOutlinePath;
import static theWeaponMaster.TheWeaponMaster.makeRelicPath;

public class HellhoundOilRelic extends CustomRelic {

    public static final String ID = TheWeaponMaster.makeID(HellhoundOilRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("hellhound_oil_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("hellhound_oil_relic.png"));

    public static boolean level1 = true;
    public static boolean level2 = false;
    public static boolean level3 = false;
    public static boolean level4 = false;
    public static boolean level5 = false;
    public static HashSet<String> weaponUpgrade = new HashSet<>();

    public HellhoundOilRelic() {
        super(ID, IMG, OUTLINE, AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.SOLID);
        setWeaponUpgrade();
    }

    public static HashSet<String> getWeaponUpgrade() {
        return weaponUpgrade;
    }

    public void setWeaponUpgrade() {
        weaponUpgrade.clear();
        if (level1) {
            weaponUpgrade.add(CerberusSlash.ID);
        }
        if (level2) {
            weaponUpgrade.add(CerberusIaiSlash.ID);
        }
        if (level3) {
            weaponUpgrade.add(CerberusModularSlash.ID);
        }
        if (level4) {
            weaponUpgrade.add(CerberusEssenceSlash.ID);
        }
        if (level5) {
            weaponUpgrade.add(CerberusDrainSlash.ID);
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
