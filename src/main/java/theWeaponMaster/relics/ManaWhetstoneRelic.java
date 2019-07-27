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

public class ManaWhetstoneRelic extends CustomRelic {

    public static final String ID = DefaultMod.makeID("ManaWhetstoneRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("mana_whetstone_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("mana_whetstone_relic.png"));

    public ManaWhetstoneRelic() {

        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
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
        if (c.cardID.equals("theWeaponMaster:AtroposSeveredSource") || c.cardID.equals("theWeaponMaster:AtroposSeveredScissors") || c.cardID.equals("theWeaponMaster:AtroposSeveredPain") || c.cardID.equals("theWeaponMaster:AtroposSeveredPath") || c.cardID.equals("theWeaponMaster:AtroposSeveredSoul")) {
            c.upgrade();
        }
    }
}
