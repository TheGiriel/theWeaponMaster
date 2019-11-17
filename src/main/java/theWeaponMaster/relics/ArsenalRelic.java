package theWeaponMaster.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.OctopusAction;
import theWeaponMaster.util.TextureLoader;

import static theWeaponMaster.TheWeaponMaster.makeRelicOutlinePath;
import static theWeaponMaster.TheWeaponMaster.makeRelicPath;

public class ArsenalRelic extends CustomRelic implements ClickableRelic {

    public static final String ID = TheWeaponMaster.makeID(ArsenalRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("arsenal_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("arsenal_relic.png"));

    private boolean usedThisTurn = false;
    private boolean isPlayerTurn = false;

    //as the name implies this is where the respective weapons are marked as (un)locked
    //Fenrir and Cerberus should be the two basic weapons that the player has access to under normal circumstances.
    public static boolean fenrirUnlocked = true;
    public static boolean cerberusUnlocked = true;
    public static boolean atroposUnlocked = true;
    public static boolean leviathanUnlocked = true;
    public static boolean revenantUnlocked = true;
    public static int currentWeapon = 0;
    public static int leviathanShots = 3;

    public AbstractPlayer player = AbstractDungeon.player;

    public ArsenalRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.CLINK);
    }

    public void atPreBattle() {
        usedThisTurn = false;
        beginLongPulse();
    }

    public void atTurnStart() {
        usedThisTurn = false;
        isPlayerTurn = true;
        beginLongPulse();
    }

    @Override
    public void onPlayerEndTurn() {
        isPlayerTurn = false; // Not our turn now.
        stopPulse();
    }

    public void setCurrentWeapon(int newWeapon) {
        currentWeapon = newWeapon;
        this.description = DESCRIPTIONS[currentWeapon];
        TheWeaponMaster.logger.info(DESCRIPTIONS[currentWeapon]);
    }

    public void updateDescription(){
        this.description = DESCRIPTIONS[currentWeapon];
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[currentWeapon];
    }

    @Override
    public void onVictory() {
        stopPulse();
    }


    @Override
    public void onUnequip() {
        AbstractDungeon.player.getRelic(ArsenalRelic.ID);
    }

    @Override
    public void onRightClick() {
        if (!isObtained || usedThisTurn || !isPlayerTurn) {
            return;

        }
        if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            usedThisTurn = true;
            flash();
            AbstractDungeon.actionManager.addToBottom(new OctopusAction());
        }
        stopPulse();
        getUpdatedDescription();
    }
}
