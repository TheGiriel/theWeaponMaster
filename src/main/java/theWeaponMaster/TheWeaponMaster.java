package theWeaponMaster;

import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theWeaponMaster.cards.bully.*;
import theWeaponMaster.cards.generic.*;
import theWeaponMaster.cards.legendary_weapons.*;
import theWeaponMaster.cards.revolver.*;
import theWeaponMaster.events.IdentityCrisisEvent;
import theWeaponMaster.potions.PlaceholderPotion;
import theWeaponMaster.relics.*;
import theWeaponMaster.util.IDCheckDontTouchPls;
import theWeaponMaster.util.TextureLoader;
import theWeaponMaster.variables.BullyVariable;
import theWeaponMaster.variables.DefaultCustomVariable;
import theWeaponMaster.variables.SecondVariable;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

//TODO: DON'T MASS RENAME/REFACTOR
// Please don't just mass replace "theWeaponMaster" with "yourMod" everywhere.
// It'll be a bigger pain for you. You only need to replace it in 3 places.
// I comment those places below, under the place where you set your ID.

//TODO: FIRST THINGS FIRST: RENAME YOUR PACKAGE AND ID NAMES FIRST-THING!!!
// Right click the package (Open the project pane on the left. Folder with black dot on it. The name's at the very top) -> Refactor -> Rename, and name it whatever you wanna call your mod.
// Scroll down in this file. Change the ID from "theWeaponMaster:" to "yourModName:" or whatever your heart desires (don't use spaces). Dw, you'll see it.
// In the JSON strings (resources>localization>eng>[all them files] make sure they all go "yourModName:" rather than "theWeaponMaster". You can ctrl+R to replace in 1 file, or ctrl+shift+r to mass replace in specific files/directories (Be careful.).
// Start with the DefaultCommon cards - they are the most commented cards since I don't feel it's necessary to put identical comments on every card.
// After you sorta get the hang of how to make cards, check out the card template which will make your life easier

/*
 * With that out of the way:
 * Welcome to this super over-commented Slay the Spire modding base.
 * Use it to make your own mod of any type. - If you want to add any standard in-game content (character,
 * cards, relics), this is a good starting point.
 * It features 1 character with a minimal set of things: 1 card of each type, 1 debuff, couple of relics, etc.
 * If you're new to modding, you basically *need* the BaseMod wiki for whatever you wish to add
 * https://github.com/daviscook477/BaseMod/wiki - work your way through with this base.
 * Feel free to use this in any way you like, of course. MIT licence applies. Happy modding!
 *
 * And pls. Read the comments.
 */

@SpireInitializer
public class TheWeaponMaster implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber {
    // Make sure to implement the subscribers *you* are using (read basemod wiki). Editing cards? EditCardsSubscriber.
    // Making relics? EditRelicsSubscriber. etc., etc., for a full list and how to make your own, visit the basemod wiki.
    public static final Logger logger = LogManager.getLogger(TheWeaponMaster.class.getName());
    private static String modID;
    public static final String THE_WEAPON_MASTER_SHOULDER_1 = "theWeaponMasterResources/images/char/defaultCharacter/shoulder.png";
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true; // The boolean we'll be setting on/off (true/false)

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "Weapon Master";
    private static final String AUTHOR = "TheGiriel"; // And pretty soon - You!
    private static final String DESCRIPTION = "A new character that focuses on using his revolver and different weapons.";
    
    // =============== INPUT TEXTURE LOCATION =================
    
    // Colors (RGB)
    // Character Color
    public static final Color DEFAULT_GRAY = CardHelper.getColor(100.0f, 53.0f, 59.0f);
    
    // Potion Colors in RGB
    public static final Color PLACEHOLDER_POTION_LIQUID = CardHelper.getColor(209.0f, 53.0f, 18.0f); // Orange-ish Red
    public static final Color PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f); // Near White
    public static final Color PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f); // Super Dark Red/Brown

    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
  
    // Card backgrounds - The actual rectangular card.
    private static final String ATTACK_DEFAULT_GRAY = "theWeaponMasterResources/images/512/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY = "theWeaponMasterResources/images/512/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY = "theWeaponMasterResources/images/512/bg_power_default_gray.png";
    
    private static final String ENERGY_ORB_DEFAULT_GRAY = "theWeaponMasterResources/images/512/card_default_gray_orb.png";
    private static final String CARD_ENERGY_ORB = "theWeaponMasterResources/images/512/card_small_orb.png";
    
    private static final String ATTACK_DEFAULT_GRAY_PORTRAIT = "theWeaponMasterResources/images/1024/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY_PORTRAIT = "theWeaponMasterResources/images/1024/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY_PORTRAIT = "theWeaponMasterResources/images/1024/bg_power_default_gray.png";
    private static final String ENERGY_ORB_DEFAULT_GRAY_PORTRAIT = "theWeaponMasterResources/images/1024/card_default_gray_orb.png";
    public static final String THE_WEAPON_MASTER_SHOULDER_2 = "theWeaponMasterResources/images/char/defaultCharacter/shoulder2.png";
    public static final String THE_WEAPON_MASTER_CORPSE = "theWeaponMasterResources/images/char/defaultCharacter/corpse.png";
    // Atlas and JSON files for the Animations
    public static final String THE_WEAPON_MASTER_SKELETON_ATLAS = "theWeaponMasterResources/images/char/defaultCharacter/skeleton.atlas";
    public static final String THE_WEAPON_MASTER_SKELETON_JSON = "theWeaponMasterResources/images/char/defaultCharacter/skeleton.json";
    // Character assets
    private static final String THE_WEAPON_MASTER_BUTTON = "theWeaponMasterResources/images/charSelect/stupidCharacterButton.png";
    
    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "theWeaponMasterResources/images/Badge.png";
    private static final String THE_WEAPON_MASTER_PORTRAIT = "theWeaponMasterResources/images/charSelect/DefaultCharacterPortraitBG.png";
    // Mod-settings settings. This is if you want an on/off savable button
    public static Properties theWeaponMasterSettings = new Properties();

    // =============== MAKE IMAGE PATHS =================
    
    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }
    
    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }
    
    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }
    
    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/images/orbs/" + resourcePath;
    }
    
    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }
    
    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }
    
    // =============== /MAKE IMAGE PATHS/ =================
    
    // =============== /INPUT TEXTURE LOCATION/ =================
    
    
    // =============== SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE =================

    public TheWeaponMaster() {
        logger.info("Subscribe to BaseMod hooks");
        
        BaseMod.subscribe(this);
        
      /*
           (   ( /(  (     ( /( (            (  `   ( /( )\ )    )\ ))\ )
           )\  )\()) )\    )\()))\ )   (     )\))(  )\()|()/(   (()/(()/(
         (((_)((_)((((_)( ((_)\(()/(   )\   ((_)()\((_)\ /(_))   /(_))(_))
         )\___ _((_)\ _ )\ _((_)/(_))_((_)  (_()((_) ((_|_))_  _(_))(_))_
        ((/ __| || (_)_\(_) \| |/ __| __| |  \/  |/ _ \|   \  |_ _||   (_)
         | (__| __ |/ _ \ | .` | (_ | _|  | |\/| | (_) | |) |  | | | |) |
          \___|_||_/_/ \_\|_|\_|\___|___| |_|  |_|\___/|___/  |___||___(_)
      */
      
        setModID("theWeaponMaster");
        // cool
        // TODO: NOW READ THIS!!!!!!!!!!!!!!!:
        
        // 1. Go to your resources folder in the project panel, and refactor> rename theWeaponMasterResources to
        // yourModIDResources.
        
        // 2. Click on the localization > eng folder and press ctrl+shift+r, then select "Directory" (rather than in Project)
        // replace all instances of theWeaponMaster with yourModID.
        // Because your mod ID isn't the default. Your cards (and everything else) should have Your mod id. Not mine.
        
        // 3. FINALLY and most importantly: Scroll up a bit. You may have noticed the image locations above don't use getModID()
        // Change their locations to reflect your actual ID rather than theWeaponMaster. They get loaded before getID is a thing.
        
        logger.info("Done subscribing");

        logger.info("Creating the color " + theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY.toString());

        BaseMod.addColor(theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY,
                DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY,
                ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY, ENERGY_ORB_DEFAULT_GRAY,
                ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT, POWER_DEFAULT_GRAY_PORTRAIT,
                ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);
        
        logger.info("Done creating the color");
        
        
        logger.info("Adding mod settings");
        // This loads the mod settings.
        // The actual mod Button is added below in receivePostInitialize()
        theWeaponMasterSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE"); // This is the default setting. It's actually set...
        try {
            SpireConfig config = new SpireConfig("theWeaponMaster", "theWeaponMasterConfig", theWeaponMasterSettings); // ...right here
            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
            config.load(); // Load the setting and set the boolean to equal it
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");
        
    }
    
    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP
    
    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = TheWeaponMaster.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THIS ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        logger.info("You are attempting to set your mod ID as: " + ID); // NO WHY
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
        logger.info("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
    } // NO
    
    private static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH
    
    public static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NNOPE DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i still hate u btw Gdx.files
        InputStream in = TheWeaponMaster.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = TheWeaponMaster.class.getPackage().getName(); // STILL NO EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE, THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO
    
    // ====== YOU CAN EDIT AGAIN ======
    
    
    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Initializing Default Mod. Hi. =========================");
        TheWeaponMaster theWeaponMaster = new TheWeaponMaster();
        logger.info("========================= /Default Mod Initialized. Hello World./ =========================");
    }

    // ============== /SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE/ =================
    
    
    // =============== LOAD THE CHARACTER =================
    
    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + theWeaponMaster.characters.TheWeaponMaster.Enums.THE_WEAPON_MASTER.toString());

        BaseMod.addCharacter(new theWeaponMaster.characters.TheWeaponMaster("the Weapon Master", theWeaponMaster.characters.TheWeaponMaster.Enums.THE_WEAPON_MASTER),
                THE_WEAPON_MASTER_BUTTON, THE_WEAPON_MASTER_PORTRAIT, theWeaponMaster.characters.TheWeaponMaster.Enums.THE_WEAPON_MASTER);
        
        receiveEditPotions();
        logger.info("Added " + theWeaponMaster.characters.TheWeaponMaster.Enums.THE_WEAPON_MASTER.toString());
    }
    
    // =============== /LOAD THE CHARACTER/ =================
    
    
    // =============== POST-INITIALIZE =================
    
    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");
        
        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        
        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();
        
        // Create the on/off button:
        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("This is the text which goes next to the checkbox.",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                enablePlaceholder, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) -> {}, // thing??????? idk
                (button) -> { // The actual button:
            
            enablePlaceholder = button.enabled; // The boolean true/false will be whether the button is enabled or not
            try {
                // And based on that boolean, set the settings and save them
                SpireConfig config = new SpireConfig("theWeaponMaster", "theWeaponMasterConfig", theWeaponMasterSettings);
                config.setBool(ENABLE_PLACEHOLDER_SETTINGS, enablePlaceholder);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        settingsPanel.addUIElement(enableNormalsButton); // Add the button to the settings panel. Button is a go.
        
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        
        // =============== EVENTS =================
        
        // This event will be exclusive to the City (act 2). If you want an event that's present at any
        // part of the game, simply don't include the dungeon ID
        // If you want to have a character-specific event, look at slimebound (CityRemoveEventPatch).
        // Essentially, you need to patch the game and say "if a player is not playing my character class, remove the event from the pool"
        BaseMod.addEvent(IdentityCrisisEvent.ID, IdentityCrisisEvent.class, TheCity.ID);
        
        // =============== /EVENTS/ =================
        logger.info("Done loading badge Image and mod options");
    }
    
    // =============== / POST-INITIALIZE/ =================
    
    
    // ================ ADD POTIONS ===================
    
    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");
        
        // Class Specific Potion. If you want your potion to not be class-specific,
        // just remove the player class at the end (in this case the "TheDefaultEnum.THE_WEAPON_MASTER".
        // Remember, you can press ctrl+P inside parentheses like addPotions)
        BaseMod.addPotion(PlaceholderPotion.class, PLACEHOLDER_POTION_LIQUID, PLACEHOLDER_POTION_HYBRID, PLACEHOLDER_POTION_SPOTS, PlaceholderPotion.POTION_ID, theWeaponMaster.characters.TheWeaponMaster.Enums.THE_WEAPON_MASTER);
        
        logger.info("Done editing potions");
    }
    
    // ================ /ADD POTIONS/ ===================
    
    
    // ================ ADD RELICS ===================
    
    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");
        
        // This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
        BaseMod.addRelicToCustomPool(new RevolverRelic(), theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY);
        BaseMod.addRelicToCustomPool(new BottledPlaceholderRelic(), theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY);
        BaseMod.addRelicToCustomPool(new DefaultClickableRelic(), theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY);
        BaseMod.addRelicToCustomPool(new ArsenalRelic(), theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY);
        BaseMod.addRelicToCustomPool(new SplinteringSteelRelic(), theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY);
        BaseMod.addRelicToCustomPool(new HellhoundOilRelic(), theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY);
        BaseMod.addRelicToCustomPool(new ManaWhetstoneRelic(), theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY);
        BaseMod.addRelicToCustomPool(new ShockwaveModulatorRelic(), theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY);
        BaseMod.addRelicToCustomPool(new GhoulskinSheathRelic(), theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY);
        BaseMod.addRelicToCustomPool(new UncommonRelicHeavyDrum(), theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY);
        
        // This adds a relic to the Shared pool. Every character can find this relic.
        BaseMod.addRelic(new PlaceholderRelic2(), RelicType.SHARED);
        
        // Mark relics as seen (the others are all starters so they're marked as seen in the character file
        UnlockTracker.markRelicAsSeen(BottledPlaceholderRelic.ID);
        UnlockTracker.markRelicAsSeen(SplinteringSteelRelic.ID);
        UnlockTracker.markRelicAsSeen(HellhoundOilRelic.ID);
        UnlockTracker.markRelicAsSeen(ManaWhetstoneRelic.ID);
        UnlockTracker.markRelicAsSeen(ShockwaveModulatorRelic.ID);
        UnlockTracker.markRelicAsSeen(GhoulskinSheathRelic.ID);
        UnlockTracker.markRelicAsSeen(UncommonRelicHeavyDrum.ID);
        logger.info("Done adding relics!");
    }
    
    // ================ /ADD RELICS/ ===================
    
    
    // ================ ADD CARDS ===================
    
    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        //Ignore this
        pathCheck();
        // Add the Custom Dynamic Variables
        logger.info("Add variables");
        // Add the Custom Dynamic variabls
        BaseMod.addDynamicVariable(new DefaultCustomVariable());
        BaseMod.addDynamicVariable(new BullyVariable());
        BaseMod.addDynamicVariable(new SecondVariable());
        
        logger.info("Adding cards");
        // Add the cards
        // Don't comment out/delete these cards (yet). You need 1 of each type and rarity (technically) for your game not to crash
        // when generating card rewards/shop screen items.
        //TODO: Add new cards whenever they are added to the pool.

        //BaseMod.addCard(new OrbSkill());

        BaseMod.addCard(new FenrirLacerate());
        BaseMod.addCard(new FenrirShieldEater());
        BaseMod.addCard(new FenrirHeavySwing());
        BaseMod.addCard(new FenrirUnleashed());
        BaseMod.addCard(new FenrirDefensiveStance());
        BaseMod.addCard(new CerberusSlash());
        BaseMod.addCard(new CerberusEssenceSlash());
        BaseMod.addCard(new CerberusModularSlash());
        BaseMod.addCard(new CerberusIaiSlash());
        BaseMod.addCard(new CerberusDrainSlash());
        BaseMod.addCard(new RevenantRavenous());
        BaseMod.addCard(new RevenantChopChopCHOP());
        BaseMod.addCard(new RevenantHungrySteel());
        BaseMod.addCard(new RevenantNoseToTail());
        BaseMod.addCard(new RevenantBloodbath());
        BaseMod.addCard(new AtroposSeveredSource());
        BaseMod.addCard(new AtroposSeveredScissors());
        BaseMod.addCard(new AtroposSeveredPath());
        BaseMod.addCard(new AtroposSeveredPain());
        BaseMod.addCard(new AtroposSeveredSoul());
        BaseMod.addCard(new LeviathanCrush());
        BaseMod.addCard(new LeviathanGauntletCharger());
        BaseMod.addCard(new LeviathanGroundSplitter());
        BaseMod.addCard(new LeviathanDeepImpact());
        BaseMod.addCard(new LeviathanEarthquake());

        BaseMod.addCard(new BullyTaunt());
        BaseMod.addCard(new BullyMeanToEveryone());
        BaseMod.addCard(new BullyConfident());
        BaseMod.addCard(new BullyWimp());
        BaseMod.addCard(new BullyIntimidate());
        BaseMod.addCard(new BullyTerrifyingHowl());
        BaseMod.addCard(new BullyDinerArgument());
        BaseMod.addCard(new BullySlap());
        BaseMod.addCard(new BullyShakedown());
        BaseMod.addCard(new BullyReminder());
        //BaseMod.addCard(new BullySuckerPunch());
        BaseMod.addCard(new BullyIntimidatingPresence());
        //BaseMod.addCard(new BullyTrip());
        //BaseMod.addCard(new BullyClothesline());
        //BaseMod.addCard(new BullyCard15());

        BaseMod.addCard(new Strike_WeaponMaster());
        BaseMod.addCard(new RevolverMagnum());
        BaseMod.addCard(new RevolverTwinned());
        BaseMod.addCard(new RevolverLowRecoil());
        BaseMod.addCard(new RevolverBuckshot());
        BaseMod.addCard(new RevolverWarningShot());
        BaseMod.addCard(new RevolverHollowPoint());
        BaseMod.addCard(new RevolverFullMetal());
        BaseMod.addCard(new RevolverSpecialGradeAmmo());
        //BaseMod.addCard(new RevolverPowerMarksmanship());
        //BaseMod.addCard(new RevolverSkillCustomCartridge());
        //BaseMod.addCard(new RevolverSkillDoubleTap());
        //BaseMod.addCard(new RevolverSkillPreload());
        BaseMod.addCard(new RevolverQuickload());
        BaseMod.addCard(new RevolverUnload());

        BaseMod.addCard(new Defend_WeaponMaster());
        BaseMod.addCard(new CounterBlow());
        BaseMod.addCard(new BerserkerStance());
        BaseMod.addCard(new SleightOfHand());
        BaseMod.addCard(new FreshApple());
        BaseMod.addCard(new LimberUp());
        BaseMod.addCard(new ThrowingKnives());
        //BaseMod.addCard(new GenericAttackDoubleStrike());
        //BaseMod.addCard(new GenericAttackUncommon());
        BaseMod.addCard(new GenericPowerAnticipation());
        BaseMod.addCard(new Flashbang());
        //BaseMod.addCard(new GenericRareDraw());
        //BaseMod.addCard(new GenericRareSkillRedirectBlow());
        BaseMod.addCard(new GenericSkillDodge());
        BaseMod.addCard(new Investigate());
        //BaseMod.addCard(new GenericSkillObserve());
        //BaseMod.addCard(new GenericUncommonPower());
        //BaseMod.addCard(new GenericUncommonPowerHuntersIntuition());
        //BaseMod.addcard(new GenericSkillRelaxRecollect());
        //BaseMod.addcard(new GenericCard20());

        logger.info("Making sure the cards are unlocked.");
        // Unlock the cards
        // This is so that they are all "seen" in the library, for people who like to look at the card list
        // before playing your mod.

        UnlockTracker.unlockCard(FenrirLacerate.ID);
        UnlockTracker.unlockCard(FenrirShieldEater.ID);
        UnlockTracker.unlockCard(FenrirHeavySwing.ID);
        UnlockTracker.unlockCard(FenrirUnleashed.ID);
        UnlockTracker.unlockCard(FenrirDefensiveStance.ID);
        UnlockTracker.unlockCard(CerberusSlash.ID);
        UnlockTracker.unlockCard(CerberusEssenceSlash.ID);
        UnlockTracker.unlockCard(CerberusModularSlash.ID);
        UnlockTracker.unlockCard(CerberusIaiSlash.ID);
        UnlockTracker.unlockCard(CerberusDrainSlash.ID);
        UnlockTracker.unlockCard(RevenantRavenous.ID);
        UnlockTracker.unlockCard(RevenantChopChopCHOP.ID);
        UnlockTracker.unlockCard(RevenantHungrySteel.ID);
        UnlockTracker.unlockCard(RevenantNoseToTail.ID);
        UnlockTracker.unlockCard(RevenantBloodbath.ID);
        UnlockTracker.unlockCard(AtroposSeveredSource.ID);
        UnlockTracker.unlockCard(AtroposSeveredScissors.ID);
        UnlockTracker.unlockCard(AtroposSeveredPath.ID);
        UnlockTracker.unlockCard(AtroposSeveredPain.ID);
        UnlockTracker.unlockCard(AtroposSeveredSoul.ID);
        UnlockTracker.unlockCard(LeviathanCrush.ID);
        UnlockTracker.unlockCard(LeviathanGauntletCharger.ID);
        UnlockTracker.unlockCard(LeviathanGroundSplitter.ID);
        UnlockTracker.unlockCard(LeviathanDeepImpact.ID);
        UnlockTracker.unlockCard(LeviathanEarthquake.ID);

        UnlockTracker.unlockCard(BullyTaunt.ID);
        UnlockTracker.unlockCard(BullyMeanToEveryone.ID);
        UnlockTracker.unlockCard(BullyConfident.ID);
        UnlockTracker.unlockCard(BullyWimp.ID);
        UnlockTracker.unlockCard(BullyIntimidate.ID);
        UnlockTracker.unlockCard(BullyTerrifyingHowl.ID);
        UnlockTracker.unlockCard(BullyDinerArgument.ID);
        UnlockTracker.unlockCard(BullySlap.ID);
        UnlockTracker.unlockCard(BullyShakedown.ID);
        UnlockTracker.unlockCard(BullyReminder.ID);
        //UnlockTracker.unlockCard(BullySuckerPunch.ID);
        //UnlockTracker.unlockCard(BullyIntimidatingPresence.ID);
        //UnlockTracker.unlockCard(BullyTrip.ID);
        //UnlockTracker.unlockCard(BullyClothesline.ID);
        //UnlockTracker.unlockCard(BullyCard15.ID);

        UnlockTracker.unlockCard(Strike_WeaponMaster.ID);
        UnlockTracker.unlockCard(RevolverMagnum.ID);
        UnlockTracker.unlockCard(RevolverTwinned.ID);
        UnlockTracker.unlockCard(RevolverLowRecoil.ID);
        UnlockTracker.unlockCard(RevolverBuckshot.ID);
        UnlockTracker.unlockCard(RevolverWarningShot.ID);
        UnlockTracker.unlockCard(RevolverHollowPoint.ID);
        UnlockTracker.unlockCard(RevolverFullMetal.ID);
        UnlockTracker.unlockCard(RevolverSpecialGradeAmmo.ID);
        //UnlockTracker.unlockCard(RevolverPowerMarksmanship.ID);
        //UnlockTracker.unlockCard(RevolverSkillCustomCartridge.ID);
        UnlockTracker.unlockCard(RevolverSkillDoubleTap.ID);
        //UnlockTracker.unlockCard(RevolverSkillPreload.ID);
        UnlockTracker.unlockCard(RevolverQuickload.ID);
        UnlockTracker.unlockCard(RevolverUnload.ID);

        UnlockTracker.unlockCard(Defend_WeaponMaster.ID);
        UnlockTracker.unlockCard(CounterBlow.ID);
        UnlockTracker.unlockCard(BerserkerStance.ID);
        UnlockTracker.unlockCard(SleightOfHand.ID);
        UnlockTracker.unlockCard(FreshApple.ID);
        UnlockTracker.unlockCard(LimberUp.ID);
        UnlockTracker.unlockCard(ThrowingKnives.ID);
        //UnlockTracker.unlockCard(GenericAttackDoubleStrike.ID);
        //UnlockTracker.unlockCard(GenericAttackUncommon.ID);
        //UnlockTracker.unlockCard(GenericPowerAnticipation.ID);
        //UnlockTracker.unlockCard(Flashbang.ID);
        //UnlockTracker.unlockCard(GenericRareDraw.ID);
        //UnlockTracker.unlockCard(GenericRareSkillRedirectBlow.ID);
        UnlockTracker.unlockCard(GenericSkillDodge.ID);
        UnlockTracker.unlockCard(Investigate.ID);
        //UnlockTracker.unlockCard(GenericSkillObserve.ID);
        //UnlockTracker.unlockCard(GenericUncommonPower.ID);
        //UnlockTracker.unlockCard(GenericUncommonPowerHuntersIntuition.ID());
        //UnlockTracker.unlockCard(GenericSkillRelaxRecollect.ID);
        //UnlockTracker.unlockCard(GenericCard20.ID);
        UnlockTracker.unlockCard(BerserkerStance.ID);

        
        logger.info("Done adding cards!");
    }
    
    // There are better ways to do this than listing every single individual card, but I do not want to complicate things
    // in a "tutorial" mod. This will do and it's completely ok to use. If you ever want to clean up and
    // shorten all the imports, go look take a look at other mods, such as Hubris.
    
    // ================ /ADD CARDS/ ===================
    
    
    // ================ LOAD THE TEXT ===================
    
    @Override
    public void receiveEditStrings() {
        logger.info("You seeing this?");
        logger.info("Beginning to edit strings for mod with ID: " + getModID());
        
        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/eng/TheWeaponMaster-Card-Strings.json");
        
        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/eng/TheWeaponMaster-Power-Strings.json");
        
        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/eng/TheWeaponMaster-Relic-Strings.json");
        
        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/eng/TheWeaponMaster-Event-Strings.json");
        
        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/eng/TheWeaponMaster-Potion-Strings.json");
        
        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/eng/TheWeaponMaster-Character-Strings.json");
        
        // OrbStrings
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                getModID() + "Resources/localization/eng/TheWeaponMaster-Orb-Strings.json");
        
        logger.info("Done edittting strings");
    }
    
    // ================ /LOAD THE TEXT/ ===================
    
    // ================ LOAD THE KEYWORDS ===================
    
    @Override
    public void receiveEditKeywords() {
        // Keywords on cards are supposed to be Capitalized, while in Keyword-String.json they're lowercase
        //
        // Multiword keywords on cards are done With_Underscores
        //
        // If you're using multiword keywords, the first element in your NAMES array in your keywords-strings.json has to be the same as the PROPER_NAME.
        // That is, in Card-Strings.json you would have #yA_Long_Keyword (#y highlights the keyword in yellow).
        // In Keyword-Strings.json you would have PROPER_NAME as A Long Keyword and the first element in NAMES be a long keyword, and the second element be a_long_keyword
        
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/TheWeaponMaster-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);
        
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                //  getModID().toLowerCase() makes your keyword mod specific (it won't show up in other cards that use that word)
            }
        }
    }
    
    // ================ /LOAD THE KEYWORDS/ ===================    
    
    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }
}
