package theWeaponMaster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.actions.WeaponCardRemovalAction;
import theWeaponMaster.cards.*;
import theWeaponMaster.cards.Not_finished.*;
import theWeaponMaster.util.TextureLoader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class LeviathanPower extends AbstractPower {

    public static final String POWER_ID = "LeviathanPower";
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("LeviathanPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(DefaultMod.makePowerPath("leviathan_placeholder_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(DefaultMod.makePowerPath("leviathan_placeholder_32.png"));

    CardGroup group;
    String a = "theWeaponMaster:LeviathanCrush";
    String b = "theWeaponMaster:LeviathanEject";
    String c = "theWeaponMaster:LeviathanGroundSplitter";
    String d = "theWeaponMaster:LeviathanDeepImpact";
    String e = "theWeaponMaster:LeviathanEarthquake";
    String f = "";
    String g = "";

    public LeviathanPower(AbstractPlayer player) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = player;
        type = PowerType.BUFF;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        AbstractDungeon.player.masterDeck.addToBottom(new LeviathanCrush());
        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(new LeviathanCrush(), true, false));
        AbstractDungeon.player.masterDeck.addToBottom(new LeviathanEject());
        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(new LeviathanEject(), true, false));
        AbstractDungeon.player.masterDeck.addToBottom(new LeviathanGroundSplitter());
        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(new LeviathanGroundSplitter(), true, false));
        AbstractDungeon.player.masterDeck.addToBottom(new LeviathanDeepImpact());
        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(new LeviathanDeepImpact(), true, false));
        AbstractDungeon.player.masterDeck.addToBottom(new LeviathanEarthquake());
        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(new LeviathanEarthquake(), true, false));
    }

    @Override
    public void onRemove() {
        new WeaponCardRemovalAction(a,b,c,d,e,f,g);
    }
}
