package theWeaponMaster.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.cards.AbstractWeaponCard;
import theWeaponMaster.util.TextureLoader;

import static theWeaponMaster.DefaultMod.makeRelicOutlinePath;
import static theWeaponMaster.DefaultMod.makeRelicPath;

public class SplinteringSteelRelic extends CustomRelic {

    public static final String ID = DefaultMod.makeID("SplinteringSteelRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("splintering_steel_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("splintering_steel_relic.png"));

    public SplinteringSteelRelic() {

        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.SOLID);
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
        if (c.cardID.equals("theWeaponMaster:FenrirLacerate") || c.cardID.equals("theWeaponMaster:FenrirDefensiveStance") || c.cardID.equals("theWeaponMaster:FenrirShieldEater") || c.cardID.equals("theWeaponMaster:FenrirHeavySwing") || c.cardID.equals("theWeaponMaster:FenrirUnleashed")) {
            c.upgrade();
        }
    }
}
