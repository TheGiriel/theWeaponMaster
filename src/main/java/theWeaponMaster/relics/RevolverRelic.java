package theWeaponMaster.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.util.TextureLoader;

import static theWeaponMaster.DefaultMod.makeRelicOutlinePath;
import static theWeaponMaster.DefaultMod.makeRelicPath;
import static theWeaponMaster.patches.WeaponMasterTags.REVOLVER;

public class RevolverRelic extends CustomRelic {

    public static final String ID = DefaultMod.makeID("RevolverRelic");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));
    public static int SHOTS = 6;
    public static int shotsLeft;

    public RevolverRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.CLINK);
        this.counter = SHOTS;
    }

    public static int getShotsLeft() {
        return shotsLeft;
    }

    public static void setShotsLeft(int shotsLeft) {
        RevolverRelic.shotsLeft = shotsLeft;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.hasTag(REVOLVER)) {
            this.counter--;
            shotsLeft = this.counter;
        }
    }

    public void onEquip() {
    }

    @Override
    public void onEnterRestRoom() {
        super.onEnterRestRoom();
    }
}
