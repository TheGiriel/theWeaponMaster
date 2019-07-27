package theWeaponMaster.relics;

import basemod.abstracts.CustomPlayer;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.util.TextureLoader;

import static theWeaponMaster.DefaultMod.makeRelicOutlinePath;
import static theWeaponMaster.DefaultMod.makeRelicPath;

public class ArsenalRelic extends CustomRelic implements ClickableRelic {

    public static final String ID = DefaultMod.makeID("ArsenalRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("arsenal_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("arsenal_relic.png"));

    private boolean usedThisTurn = false;
    private boolean isPlayerTurn = false;

    private boolean fenrirUnlocked = false;
    private boolean cerberusUnlocked = false;
    private boolean atroposUnlocked = false;
    private boolean leviathanUnlocked = false;
    private boolean revenantUnlocked = false;

    public ArsenalRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.CLINK);

    }

    @Override
    public void atBattleStartPreDraw() {
        flash();
    }

    //TODO: Clickable relic that allows you to modify your deck on the fly 1x per turn. Every activation costs HP.
    @Override
    public void onRightClick() {
        if (!isObtained || usedThisTurn || !isPlayerTurn) {
            return;
        }
        if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atPreBattle() {
        usedThisTurn = false;
        beginLongPulse();
    }

    @Override
    public void onVictory() {
        stopPulse();
    }

    public void atTurnStart() {
        usedThisTurn = false;
        isPlayerTurn = true;
        beginLongPulse();
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.getRelic("ArsenalRelic");
    }

}
