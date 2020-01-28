package theWeaponMaster.characters;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theWeaponMaster.cards.Defend_WeaponMaster;
import theWeaponMaster.cards.Strike_WeaponMaster;
import theWeaponMaster.cards.bully.BullyMock;
import theWeaponMaster.cards.legendary_weapons.FenrirLacerate;
import theWeaponMaster.powers.ViciousPower;
import theWeaponMaster.relics.ArsenalRelic;
import theWeaponMaster.relics.RevolverRelic;

import java.util.ArrayList;

import static theWeaponMaster.TheWeaponMaster.*;
import static theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

//Wiki-page https://github.com/daviscook477/BaseMod/wiki/Custom-Characters
//and https://github.com/daviscook477/BaseMod/wiki/Migrating-to-5.0
//All text (starting description and loadout, anything labeled TEXT[]) can be found in TheWeaponMaster-character-Strings.json in the resources

public class TheWeaponMaster extends CustomPlayer {

    private static final String[] orbTextures = {
            "theWeaponMasterResources/images/char/defaultCharacter/orb/layer1.png",
            "theWeaponMasterResources/images/char/defaultCharacter/orb/layer2.png",
            "theWeaponMasterResources/images/char/defaultCharacter/orb/layer3.png",
            "theWeaponMasterResources/images/char/defaultCharacter/orb/layer4.png",
            "theWeaponMasterResources/images/char/defaultCharacter/orb/layer5.png",
            "theWeaponMasterResources/images/char/defaultCharacter/orb/weapon_master_energy_.png",
            "theWeaponMasterResources/images/char/defaultCharacter/orb/layer1d.png",
            "theWeaponMasterResources/images/char/defaultCharacter/orb/layer2d.png",
            "theWeaponMasterResources/images/char/defaultCharacter/orb/layer3d.png",
            "theWeaponMasterResources/images/char/defaultCharacter/orb/layer4d.png",
            "theWeaponMasterResources/images/char/defaultCharacter/orb/layer5d.png",};
    /*
     */
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

    public static final Logger logger = LogManager.getLogger(theWeaponMaster.TheWeaponMaster.class.getName());

    // =============== CHARACTER ENUMERATORS =================
    // These are enums for your Characters color (both general color and for the card library) as well as
    // an enum for the name of the player class - IRONCLAD, THE_SILENT, DEFECT, YOUR_CLASS ...
    // These are all necessary for creating a character. If you want to find out where and how exactly they are used
    // in the basegame (for fun and education) Ctrl+click on the PlayerClass, CardColor and/or LibraryType below and go down the
    // Ctrl+click rabbit hole

    public TheWeaponMaster(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures,
                "theWeaponMasterResources/images/char/defaultCharacter/orb/vfx.png", null,
                new SpriterAnimation(
                        "theWeaponMasterResources/images/char/defaultCharacter/Spriter/theDefaultAnimation.scml"));


        // =============== TEXTURES, ENERGY, LOADOUT =================

        initializeClass(null, // required call to load textures and setup energy/loadout.
                // I left these in TheWeaponMaster.java (Ctrl+click them to see where they are, Ctrl+hover to see what they read.)
                THE_WEAPON_MASTER_SHOULDER_1, // campfire pose
                THE_WEAPON_MASTER_SHOULDER_2, // another campfire pose
                THE_WEAPON_MASTER_CORPSE, // dead corpse
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN)); // energy manager

        // =============== /TEXTURES, ENERGY, LOADOUT/ =================


        // =============== ANIMATIONS =================

        loadAnimation(
                THE_WEAPON_MASTER_SKELETON_ATLAS,
                THE_WEAPON_MASTER_SKELETON_JSON,
                1.0f);
        AnimationState.TrackEntry e = state.setAnimation(0, "animation", true);
        e.setTime(e.getEndTime() * MathUtils.random());

        // =============== /ANIMATIONS/ =================


        // =============== TEXT BUBBLE LOCATION =================

        dialogX = (drawX + 0.0F * Settings.scale); // set location for text bubbles
        dialogY = (drawY + 220.0F * Settings.scale); // you can just copy these values

        // =============== /TEXT BUBBLE LOCATION/ =================

    }

    @Override
    public String getPortraitImageName() {
        return null;
    }

    // =============== CHARACTER ENUMERATORS  =================


    // =============== BASE STATS =================

    private static final int ENERGY_PER_TURN = 3;
    private static final int STARTING_HP = 75;
    private static final int MAX_HP = 75;
    private static final int STARTING_GOLD = 99;
    private static final int CARD_DRAW = 5;
    private static final int ORB_SLOTS = 0;

    // =============== /BASE STATS/ =================


    // =============== STRINGS =================

    private static final String ID = makeID("DefaultCharacter");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    // =============== /STRINGS/ =================


    // =============== TEXTURES OF BIG ENERGY ORB ===============
    public static int fenrirEvolutions = 0;

    // =============== /TEXTURES OF BIG ENERGY ORB/ ===============

    // =============== CHARACTER CLASS START =================

    // Starting Deck
    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        logger.info("Begin loading starter Deck Strings");

        //ArsenalRelic.currentWeapon = "Fenrir";

        retVal.add(Strike_WeaponMaster.ID);
        retVal.add(Strike_WeaponMaster.ID);
        retVal.add(Strike_WeaponMaster.ID);
        retVal.add(Strike_WeaponMaster.ID);
        retVal.add(FenrirLacerate.ID);
        retVal.add(Defend_WeaponMaster.ID);
        retVal.add(Defend_WeaponMaster.ID);
        retVal.add(Defend_WeaponMaster.ID);
        retVal.add(Defend_WeaponMaster.ID);
        retVal.add(BullyMock.ID);


        //retVal.add(RevolverBuckshot.ID);
        //retVal.add(RevolverCustomCartridge.ID);
        //retVal.add(RevolverFullMetal.ID);
        //retVal.add(RevolverHollowPoint.ID);
        //retVal.add(RevolverKneecap.ID);
        //retVal.add(RevolverLowRecoil.ID);
        //retVal.add(RevolverMagnum.ID);
        //retVal.add(RevolverSpecialGradeAmmmo.ID);
        //retVal.add(RevolverTwinned.ID);
        //retVal.add(RevolverWarningShot.ID;
        //retVal.add(Strike_WeaponMaster.ID);

        return retVal;
    }

    // =============== /CHARACTER CLASS END/ =================

    // Starting description and loadout
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    // Should return a color object to be used to color the trail of moving cards
    @Override
    public Color getCardTrailColor() {
        return theWeaponMaster.TheWeaponMaster.DEFAULT_GRAY;
    }

    // Starting Relics	
    public ArrayList<String> getStartingRelics() {
        logger.info("Begin loading starting relic Strings");
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(ArsenalRelic.ID);
        retVal.add(RevolverRelic.ID);

        UnlockTracker.markRelicAsSeen(ArsenalRelic.ID);
        UnlockTracker.markRelicAsSeen(RevolverRelic.ID);

        return retVal;
    }


    public void preBattlePrep() {
        super.preBattlePrep();
        if (!AbstractDungeon.player.hasPower(ViciousPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ViciousPower(AbstractDungeon.player, 1)));
        }
    }


    // character Select screen effect
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_DAGGER_1", 1.25f); // Sound Effect
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false); // Screen Effect
    }

    // character Select on-button-press sound effect
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_DAGGER_1";
    }

    // Should return how much HP your maximum HP reduces by when starting a run at
    // Ascension 14 or higher. (ironclad loses 5, defect and silent lose 4 hp respectively)
    @Override
    public int getAscensionMaxHPLoss() {
        return 0;
    }

    // Should return the card color enum to be associated with your character.
    @Override
    public AbstractCard.CardColor getCardColor() {
        return COLOR_GRAY;
    }

    // Should return a Color object to be used to color the miniature card images in run history.
    @Override
    public Color getCardRenderColor() {
        return theWeaponMaster.TheWeaponMaster.DEFAULT_GRAY;
    }

    // Should return a BitmapFont object that you can use to customize how your
    // energy is displayed from within the energy orb.
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    // Should return class name as it appears in run history screen.
    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    //Which card should be obtainable from the Match and Keep event?
    @Override
    public AbstractCard getStartCardForEvent() {
        return new Strike_WeaponMaster();
    }

    // The class name as it appears next to your player name in-game
    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    // Should return a new instance of your character, sending name as its name parameter.
    @Override
    public AbstractPlayer newInstance() {
        return new TheWeaponMaster(name, chosenClass);
    }

    // Should return a Color object to be used as screen tint effect when your
    // character attacks the heart.
    @Override
    public Color getSlashAttackColor() {
        return theWeaponMaster.TheWeaponMaster.DEFAULT_GRAY;
    }


    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_WEAPON_MASTER;
        @SpireEnum(name = "DEFAULT_GRAY_COLOR") // These two HAVE to have the same absolutely identical name.
        public static AbstractCard.CardColor COLOR_GRAY;
        @SpireEnum(name = "DEFAULT_GRAY_COLOR")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }

    // Should return an AttackEffect array of any size greater than 0. These effects
    // will be played in sequence as your character's finishing combo on the heart.
    // Attack effects are the same as used in DamageAction and the like.
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.BLUNT_LIGHT,
                AbstractGameAction.AttackEffect.BLUNT_LIGHT,
                AbstractGameAction.AttackEffect.BLUNT_LIGHT,
                AbstractGameAction.AttackEffect.BLUNT_LIGHT,
                AbstractGameAction.AttackEffect.BLUNT_LIGHT,
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL,
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL};
    }

    // Should return a string containing what text is shown when your character is
    // about to attack the heart. For example, the defect is "NL You charge your
    // core to its maximum..."
    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    // The vampire events refer to the base game characters as "brother", "sister",
    // and "broken one" respectively.This method should return a String containing
    // the full text that will be displayed as the first screen of the vampires event.
    @Override
    public String getVampireText() {
        return TEXT[2];
    }

}
