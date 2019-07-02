package theGodHunters.characters;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theGodHunters.DefaultMod;
import theGodHunters.powers.BiomassPower;

import java.util.ArrayList;

import static theGodHunters.DefaultMod.makeID;

public class TheEvolvingQueen extends CustomPlayer {
    public static final Logger logger = LogManager.getLogger(DefaultMod.class.getName());

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass EVOLVING;
        @SpireEnum(name = "DEFAULT_GRAY_COLOR") // These two HAVE to have the same absolutely identical name.
        public static AbstractCard.CardColor COLOR_GRAY;
        @SpireEnum(name = "DEFAULT_GRAY_COLOR")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }

    private static final int ENERGY_PER_TURN = 3;
    private static final int STARTING_HP = 70;
    private static final int MAX_HP = 70;
    private static final int STARTING_GOLD = 99;
    private static final int CARD_DRAW = 5;
    private static final int ORB_SLOTS = 0;

    private static final String ID = makeID("EvolvingQueen");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    private static final String[] orbTextures = {
            "theGodHuntersResources/images/char/defaultCharacter/orb/layer1.png",
            "theGodHuntersResources/images/char/defaultCharacter/orb/layer2.png",
            "theGodHuntersResources/images/char/defaultCharacter/orb/layer3.png",
            "theGodHuntersResources/images/char/defaultCharacter/orb/layer4.png",
            "theGodHuntersResources/images/char/defaultCharacter/orb/layer5.png",
            "theGodHuntersResources/images/char/defaultCharacter/orb/layer6.png",
            "theGodHuntersResources/images/char/defaultCharacter/orb/layer1d.png",
            "theGodHuntersResources/images/char/defaultCharacter/orb/layer2d.png",
            "theGodHuntersResources/images/char/defaultCharacter/orb/layer3d.png",
            "theGodHuntersResources/images/char/defaultCharacter/orb/layer4d.png",
            "theGodHuntersResources/images/char/defaultCharacter/orb/layer5d.png",};


    public TheEvolvingQueen(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures, "theGodHuntersResources/images/char/defaultCharacter/orb/vfx.png", null,
                new SpriterAnimation(
                        "theGodHuntersResources/images/char/defaultCharacter/Spriter/theDefaultAnimation.scml"));
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        return null;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        return null;
    }

    @Override
    public void preBattlePrep() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BiomassPower()));
    }

    @Override
    public CharSelectInfo getLoadout() {
        return null;
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return null;
    }

    @Override
    public Color getCardRenderColor() {
        return null;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return null;
    }

    @Override
    public Color getCardTrailColor() {
        return null;
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 0;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return null;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {

    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return null;
    }

    @Override
    public String getLocalizedCharacterName() {
        return null;
    }

    @Override
    public AbstractPlayer newInstance() {
        return new TheEvolvingQueen(name, chosenClass);
    }

    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    @Override
    public Color getSlashAttackColor() {
        return null;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[0];
    }

    @Override
    public String getVampireText() {
        return TEXT[2];
    }
}
