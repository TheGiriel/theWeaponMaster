package theWeaponMaster.relics;

import basemod.abstracts.CustomPlayer;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.util.TextureLoader;

import static theWeaponMaster.DefaultMod.makeRelicOutlinePath;
import static theWeaponMaster.DefaultMod.makeRelicPath;

public class ArsenalRelic extends CustomRelic implements ClickableRelic {

    private boolean fenrirUnlocked = false;
    private boolean cerberusUnlocked = false;
    private boolean atroposUnlocked = false;
    private boolean leviathanUnlocked = false;
    private boolean revenantUnlocked = false;


    public static final String ID = DefaultMod.makeID("ArsenalRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("arsenal_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("arsenal_relic.png"));

    private boolean usedThisTurn = false;
    private boolean isPlayerTurn = false;

    public ArsenalRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.CLINK);

    }

    @Override
    public AbstractRelic makeCopy() {
        return null;
    }

    @Override
    public void onRightClick() {

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    //TODO: Clickable relic that allows you to modify your deck on the fly 1x per turn. Every activation costs HP.
}
