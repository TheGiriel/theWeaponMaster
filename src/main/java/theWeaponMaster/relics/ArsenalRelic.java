package theWeaponMaster.relics;

import basemod.abstracts.CustomPlayer;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.actions.OctopusAction;
import theWeaponMaster.cards.Not_finished.*;
import theWeaponMaster.cards.WeaponSwap;
import theWeaponMaster.powers.*;
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
    public boolean fenrirUnlocked = true;
    public boolean cerberusUnlocked = true;
    public boolean atroposUnlocked = false;
    public boolean leviathanUnlocked = false;
    public boolean revenantUnlocked = false;
    public static String currentWeapon; //this is where the current weapon is saved under

    public AbstractPlayer player = AbstractDungeon.player;

    public ArsenalRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.CLINK);
        this.currentWeapon = "Fenrir";
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
