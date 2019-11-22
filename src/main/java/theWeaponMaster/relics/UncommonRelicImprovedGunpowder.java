package theWeaponMaster.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.util.TextureLoader;

import static theWeaponMaster.TheWeaponMaster.makeRelicOutlinePath;
import static theWeaponMaster.TheWeaponMaster.makeRelicPath;

public class UncommonRelicImprovedGunpowder extends CustomRelic {


    public static final String ID = TheWeaponMaster.makeID(UncommonRelicImprovedGunpowder.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public UncommonRelicImprovedGunpowder() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStartPreDraw() {
        flash();
    }

    @Override
    public void onEquip() {

    }

    @Override
    public void onUnequip() {

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
