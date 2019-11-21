package theWeaponMaster.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.ArsenalRecombineAction;
import theWeaponMaster.actions.LeviathanChargeAction;
import theWeaponMaster.actions.OctopusAction;
import theWeaponMaster.actions.RevenantStarveAction;
import theWeaponMaster.powers.ViciousPower;
import theWeaponMaster.util.TextureLoader;

import java.util.HashSet;

import static theWeaponMaster.TheWeaponMaster.makeRelicOutlinePath;
import static theWeaponMaster.TheWeaponMaster.makeRelicPath;
import static theWeaponMaster.patches.WeaponMasterTags.REVENANT;

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
    public static String currentWeapon = "None";
    public static int leviathanCharges = 3;
    public static int revenantHunger = 10;
    private HashSet<String> delete = new HashSet<>();

    public AbstractPlayer player = AbstractDungeon.player;

    public ArsenalRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.CLINK);
        this.description = DESCRIPTIONS[0];
    }

    public void atPreBattle() {
        usedThisTurn = false;
        beginLongPulse();
    }

    public void atTurnStart() {
        usedThisTurn = false;
        isPlayerTurn = true;
        new ArsenalRecombineAction();
        beginLongPulse();
    }


    @Override
    public void atBattleStart() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new ViciousPower(player, 0)));
        leviathanCharges = Math.min(Math.max(leviathanCharges, 0), 3);
        revenantHunger = Math.min(Math.max(revenantHunger, 0), 10);
    }

    @Override
    public void onPlayerEndTurn() {
        for (AbstractCard card : player.hand.group) {
            if (card.hasTag(REVENANT)) {
                starveRevenant();
            }
        }
        if (leviathanCharges < 3) {
            chargeGauntlet();
        }
        isPlayerTurn = false; // Not our turn now.
        stopPulse();
    }

    public void chargeGauntlet() {
        leviathanCharges = Math.min(Math.max(leviathanCharges, 0), 3);
        new LeviathanChargeAction(1);
    }

    private void starveRevenant() {
        revenantHunger = Math.min(Math.max(revenantHunger, 0), 10);
        new RevenantStarveAction(1, false);
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        new LeviathanChargeAction(1);
        leviathanCharges = Math.min(Math.max(leviathanCharges, 0), 3);
    }

    public void setCurrentWeapon(String newWeapon) {
        currentWeapon = newWeapon;
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if (targetCard.type == AbstractCard.CardType.ATTACK) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new ViciousPower(player, 2)));
        }
        if (currentWeapon.equals("Revenant") && !targetCard.hasTag(REVENANT)) {
            starveRevenant();
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.getRelic(ArsenalRelic.ID);
    }

    public void onVictory() {
        stopPulse();
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
