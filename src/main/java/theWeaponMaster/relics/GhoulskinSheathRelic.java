package theWeaponMaster.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.cards.*;
import theWeaponMaster.util.TextureLoader;

import java.util.HashSet;

import static theWeaponMaster.DefaultMod.makeRelicOutlinePath;
import static theWeaponMaster.DefaultMod.makeRelicPath;

public class GhoulskinSheathRelic extends CustomRelic {

    public static final String ID = DefaultMod.makeID(GhoulskinSheathRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ghoul_skin_sheath_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ghoul_skin_sheath_relic.png"));

    public static boolean level1 = true;
    public static boolean level2 = false;
    public static boolean level3 = false;
    public static boolean level4 = false;
    public static boolean level5 = false;
    public static HashSet<String> weaponUpgrade = new HashSet<>();

    public GhoulskinSheathRelic() {
        super(ID, IMG, OUTLINE, AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.SOLID);
        setWeaponUpgrade();
    }

    public static HashSet<String> getWeaponUpgrade() {
        return weaponUpgrade;
    }

    public void setWeaponUpgrade() {
        weaponUpgrade.clear();
        if (level1) {
            weaponUpgrade.add(RevenantRavenous.ID);
        }
        if (level2) {
            weaponUpgrade.add(RevenantChopChopCHOP.ID);
            //weaponSetHash.add(LeviathanReload.ID);//unlisted
        }
        if (level3) {
            weaponUpgrade.add(RevenantHungrySteel.ID);
        }
        if (level4) {
            weaponUpgrade.add(RevenantNoseToTail.ID);
        }
        if (level5) {
            weaponUpgrade.add(RevenantBloodbath.ID);
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
