package theWeaponMaster.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.FenrirEvolveAction;
import theWeaponMaster.actions.LeviathanChargeAction;
import theWeaponMaster.actions.OctopusAction;
import theWeaponMaster.actions.RevenantStarveAction;
import theWeaponMaster.util.TextureLoader;

import java.util.ArrayList;

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
    public static ArrayList<Boolean> fenrirCardsUnlocked = new ArrayList<>();
    public static boolean cerberusUnlocked = true;
    public static ArrayList<Boolean> cerberusCardsUnlocked = new ArrayList<>();
    public static boolean revenantUnlocked = true;
    public static ArrayList<Boolean> revenantCardsUnlocked = new ArrayList<>();
    public static boolean atroposUnlocked = true;
    public static ArrayList<Boolean> atroposcardsCardsUnlocked = new ArrayList<>();
    public static boolean leviathanUnlocked = true;
    public static ArrayList<Boolean> leviathanCardsUnlocked = new ArrayList<>();
    public static String currentWeapon = "None";
    public static int leviathanCharges = 3;
    public static int revenantHunger = 10;
    public static int fenrirEvolutions = 0;

    public AbstractPlayer player = AbstractDungeon.player;

    public ArsenalRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.CLINK);
        this.description = DESCRIPTIONS[0];
        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    public void atPreBattle() {
        usedThisTurn = false;
        beginLongPulse();
    }

    public void atTurnStart() {
        usedThisTurn = false;
        isPlayerTurn = true;
        beginLongPulse();
        if (leviathanCharges < 3) {
            chargeGauntlet();
        }
        if (currentWeapon.equals("Revenant")) {
            counter = revenantHunger;
        } else {
            counter = -2;
        }
    }

    @Override
    public void atBattleStart() {
        leviathanCharges = Math.min(Math.max(leviathanCharges, 0), 3);
        AbstractDungeon.actionManager.addToBottom(new LeviathanChargeAction(0));
        revenantHunger = Math.min(Math.max(revenantHunger, 0), 10);
    }

    @Override
    public void onPlayerEndTurn() {
        if (revenantHunger < 10) {
            for (AbstractCard card : player.hand.group) {
                if (card.hasTag(REVENANT)) {
                    starveRevenant();
                    counter = revenantHunger;
                }
            }
        }
        isPlayerTurn = false; // Not our turn now.
        stopPulse();
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        leviathanCharges = Math.min(Math.max(leviathanCharges, 0), 3);
        if (leviathanCharges < 3) {
            chargeGauntlet();
        }
        Math.max(revenantHunger, 0);
        if (revenantHunger < 10) {
            new RevenantStarveAction(2, false);
        }
        revenantHunger = Math.min(Math.max(revenantHunger, 0), 10);
    }

    public void chargeGauntlet() {
        leviathanCharges = Math.min(Math.max(leviathanCharges, 0), 3);
        AbstractDungeon.actionManager.addToBottom(new LeviathanChargeAction(1));
    }

    private void starveRevenant() {
        revenantHunger = Math.min(Math.max(revenantHunger, 0), 10);
        counter = revenantHunger;
        AbstractDungeon.actionManager.addToBottom(new RevenantStarveAction(1, false));
    }


    public void setCurrentWeapon(String newWeapon) {
        currentWeapon = newWeapon;
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if (currentWeapon.equals("Revenant")) {
            if (!targetCard.hasTag(REVENANT) && revenantHunger < 10) {
                starveRevenant();
            }
            counter = revenantHunger;
        }
    }

    @Override
    public void update() {
        super.update();
        setDescriptionAfterLoading();
    }

    public void setDescriptionAfterLoading() {
        this.description = DESCRIPTIONS[0] + FontHelper.colorString(currentWeapon, "r");
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    @Override
    public void atBattleStartPreDraw() {
        AbstractDungeon.player.masterDeck.group.stream().anyMatch(e -> {
            if (e.cardID.startsWith("theWeaponMaster:Fenrir")) {
                currentWeapon = "Fenrir";
                return true;
            }
            if (e.cardID.startsWith("theWeaponMaster:Cerberus")) {
                currentWeapon = "Cerberus";
                return true;
            }
            if (e.cardID.startsWith("theWeaponMaster:Revenant")) {
                currentWeapon = "Revenant";
                return true;
            }
            if (e.cardID.startsWith("theWeaponMaster:Atropos")) {
                currentWeapon = "Atropos";
                return true;
            }
            if (e.cardID.startsWith("theWeaponMaster:Leviathan")) {
                currentWeapon = "Leviathan";
                return true;
            }
            currentWeapon = "None";
            return false;
        });
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.getRelic(ArsenalRelic.ID);
    }

    public void onVictory() {
        new FenrirEvolveAction(fenrirEvolutions);
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
