package theWeaponMaster.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.util.TextureLoader;

import static theWeaponMaster.DefaultMod.makeRelicOutlinePath;
import static theWeaponMaster.DefaultMod.makeRelicPath;

public class HellhoundOilRelic extends CustomRelic {

    public static final String ID = DefaultMod.makeID("HellhoundOilRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("hellhound_oil_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("hellhound_oil_relic.png"));

    public HellhoundOilRelic() {

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
        if (c.cardID.equals("theWeaponMaster:CerberusSlash") || c.cardID.equals("theWeaponMaster:CerberusIaiSlash") || c.cardID.equals("theWeaponMaster:CerberusEssenceSlash") || c.cardID.equals("theWeaponMaster:CerberusModularSlash") || c.cardID.equals("theWeaponMaster:CerberusDrainSlash")) {
            c.upgrade();
        }
    }
    //Fenrir Relic 1
}
