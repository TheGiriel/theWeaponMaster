package theWeaponMaster.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.cards.WeaponSwap;
import theWeaponMaster.util.TextureLoader;

import static theWeaponMaster.DefaultMod.makeRelicOutlinePath;
import static theWeaponMaster.DefaultMod.makeRelicPath;

public class ArsenalRelic extends CustomRelic {

    public static final String ID = DefaultMod.makeID("ArsenalRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("arsenal_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("arsenal_relic.png"));

    /*
    private boolean usedThisTurn = false;
    private boolean isPlayerTurn = false;
*/

    //as the name implies this is where the respective weapons are marked as (un)locked
    //Fenrir and Cerberus should be the two basic weapons that the player has access to under normal circumstances.
    public static boolean fenrirUnlocked = true;
    public static boolean cerberusUnlocked = true;
    public static boolean atroposUnlocked = true;
    public static boolean leviathanUnlocked = true;
    public static boolean revenantUnlocked = true;
    public static String currentWeapon = "Fenrir"; //this is where the current weapon is saved under
    public static int leviathanShots = 3;

    public AbstractPlayer player = AbstractDungeon.player;

    public ArsenalRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.CLINK);
    }

    @Override
    public void atBattleStartPreDraw() {
        flash();
    }

    /*
    @Override
    public void onRightClick() {
        if (!isObtained || usedThisTurn || !isPlayerTurn) {
            return;
        }
    }*/

    public void setCurrentWeapon(String newWeapon){
        currentWeapon = newWeapon;
        this.description = DESCRIPTIONS[0] + currentWeapon;
    }

    public void updateDescription(){

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + currentWeapon;
    }
/*
    @Override
    public void atPreBattle() {
        usedThisTurn = false;
        beginLongPulse();
    }

    @Override
    public void onVictory() {
        stopPulse();
    }
    */
    public void atTurnStart() {
        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(new WeaponSwap()));
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.getRelic("ArsenalRelic");
    }

}
