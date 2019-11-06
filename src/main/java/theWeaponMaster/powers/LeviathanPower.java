package theWeaponMaster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.cards.*;
import theWeaponMaster.cards.Not_finished.*;
import theWeaponMaster.util.TextureLoader;

import java.util.HashSet;
import java.util.Iterator;

public class LeviathanPower extends AbstractPower {

    public static final String POWER_ID = "LeviathanPower";
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("LeviathanPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(DefaultMod.makePowerPath("leviathan_placeholder_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(DefaultMod.makePowerPath("leviathan_placeholder_32.png"));

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
        AbstractDungeon.player.masterDeck.removeCard("theWeaponMaster:LeviathanCrush");
        AbstractDungeon.player.masterDeck.removeCard("theWeaponMaster:LeviathanEject");
        AbstractDungeon.player.masterDeck.removeCard("theWeaponMaster:LeviathanGroundSplitter");
        AbstractDungeon.player.masterDeck.removeCard("theWeaponMaster:LeviathanDeepImpact");
        AbstractDungeon.player.masterDeck.removeCard("theWeaponMaster:LeviathanEarthquake");

        removeLeviathan();
    }

    private HashSet<AbstractCard> removeLeviathan() {
        HashSet<AbstractCard> leviathanCards = new HashSet<AbstractCard>();

        AbstractCard card = AbstractDungeon.player.cardInUse;
        if (card != null && card.cardID.equals(this)) {
            leviathanCards.remove(card);
        }

        Iterator cardIterator = AbstractDungeon.player.drawPile.group.iterator();
        removeCards(leviathanCards, cardIterator);

        cardIterator = AbstractDungeon.player.discardPile.group.iterator();
        removeCards(leviathanCards, cardIterator);

        cardIterator = AbstractDungeon.player.exhaustPile.group.iterator();
        removeCards(leviathanCards, cardIterator);

        cardIterator = AbstractDungeon.player.limbo.group.iterator();
        removeCards(leviathanCards, cardIterator);

        cardIterator = AbstractDungeon.player.hand.group.iterator();
        removeCards(leviathanCards, cardIterator);
        return leviathanCards;
    }

    private void removeCards(HashSet<AbstractCard> leviathanCards, Iterator cardIterator) {
        AbstractCard c;
        while (cardIterator.hasNext()) {
            c = (AbstractCard) cardIterator.next();
            if (
                    c.cardID.equals("theWeaponMaster:LeviathanCrush") ||
                    c.cardID.equals("theWeaponMaster:LeviathanEject") ||
                    c.cardID.equals("theWeaponMaster:LeviathanGroundSplitter") ||
                    c.cardID.equals("theWeaponMaster:LeviathanDeepImpact") ||
                    c.cardID.equals("theWeaponMaster:LeviathanEarthquake")
            ) {
                leviathanCards.remove(c);
            }
        }
    }
}
