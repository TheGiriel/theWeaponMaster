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
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.graalvm.compiler.nodes.NodeView;
import theGodHunters.powers.LancePower;
import theGodHunters.powers.ShieldPower;

import java.util.ArrayList;

import static theGodHunters.DefaultMod.makeID;

public class TheArtForger extends CustomPlayer {
    public static final Logger logger = LogManager.getLogger(NodeView.Default.class.getName());

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_DEFAULT;
        @SpireEnum(name = "DEFAULT_GRAY_COLOR") // These two HAVE to have the same absolutely identical name.
        public static AbstractCard.CardColor COLOR_GRAY;
        @SpireEnum(name = "DEFAULT_GRAY_COLOR")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }


    private static final int ENERGY_PER_TURN = 3;
    private static final int STARTING_HP = 80;
    private static final int MAX_HP = 80;
    private static final int STARTING_GOLD = 80;
    private static final int CARD_DRAW = 5;
    private static final int ORB_SLOTS = 6;

    private static final String ID = makeID("ArtForger");
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


    public TheArtForger(String name, PlayerClass setClass) {
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
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ShieldPower()));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LancePower()));
    }

    @Override
    public CharSelectInfo getLoadout() {
        return null;
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return null;
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
        CardCrawlGame.sound.playA("ATTACK_DAGGER_1", 1.25f);
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_DAGGER_1";
    }

    @Override
    public String getLocalizedCharacterName() {
        return null;
    }

    @Override
    public AbstractPlayer newInstance() {
        return null;
    }

    @Override
    public Color getSlashAttackColor() {
        return null;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }

    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    @Override
    public String getVampireText() {
        return TEXT[2];
    }
}
