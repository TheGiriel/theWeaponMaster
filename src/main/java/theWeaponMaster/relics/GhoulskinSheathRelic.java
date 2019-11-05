package theWeaponMaster.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.util.TextureLoader;

import static theWeaponMaster.DefaultMod.makeRelicOutlinePath;
import static theWeaponMaster.DefaultMod.makeRelicPath;

public class GhoulskinSheathRelic extends CustomRelic {

    public static final String ID = DefaultMod.makeID("GhoulskinSheathRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ghoul_skin_sheath_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ghoul_skin_sheath_relic.png"));

    public GhoulskinSheathRelic() {

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
        if (c.cardID.equals("theWeaponMaster:RevenantRavenous") || c.cardID.equals("theWeaponMaster:RevenantChopChopCHOP") || c.cardID.equals("theWeaponMaster:RevenantHungrySteel") || c.cardID.equals("theWeaponMaster:RevenantSnoutToTail") || c.cardID.equals("theWeaponMaster:RevenantBloodbath")) {
            c.upgrade();
        }
    }
}
