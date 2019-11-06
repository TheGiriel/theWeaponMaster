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

public class AtroposPower extends AbstractPower {

    public static final String POWER_ID = "AtroposPower";
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("AtroposPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(DefaultMod.makePowerPath("atropos_placeholder_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(DefaultMod.makePowerPath("atropos_placeholder_32.png"));

    public AtroposPower(AbstractPlayer player) {
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
        AbstractDungeon.player.masterDeck.addToBottom(new AtroposSeveredSource());
        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(new AtroposSeveredSource(), true, false));
        AbstractDungeon.player.masterDeck.addToBottom(new AtroposSeveredScissors());
        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(new AtroposSeveredScissors(), true, false));
        AbstractDungeon.player.masterDeck.addToBottom(new AtroposSeveredPath());
        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(new AtroposSeveredPath(), true, false));
        AbstractDungeon.player.masterDeck.addToBottom(new AtroposSeveredPain());
        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(new AtroposSeveredPain(), true, false));
        AbstractDungeon.player.masterDeck.addToBottom(new AtroposSeveredSoul());
        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(new AtroposSeveredSoul(), true, false));
    }

    @Override
    public void onRemove() {
        AbstractDungeon.player.masterDeck.removeCard("theWeaponMaster:AtroposSeveredSource");
        AbstractDungeon.player.masterDeck.removeCard("theWeaponMaster:AtroposSeveredScissors");
        AbstractDungeon.player.masterDeck.removeCard("theWeaponMaster:AtroposSeveredPath");
        AbstractDungeon.player.masterDeck.removeCard("theWeaponMaster:AtroposSeveredPain");
        AbstractDungeon.player.masterDeck.removeCard("theWeaponMaster:AtroposSeveredSoul");

        removeAtropos();
    }

    private HashSet<AbstractCard> removeAtropos() {
        HashSet<AbstractCard> atroposCards = new HashSet<AbstractCard>();

        AbstractCard card = AbstractDungeon.player.cardInUse;
        if (card != null && card.cardID.equals(this)) {
            atroposCards.remove(card);
        }

        Iterator cardIterator = AbstractDungeon.player.drawPile.group.iterator();
        removeCards(atroposCards, cardIterator);

        cardIterator = AbstractDungeon.player.discardPile.group.iterator();
        removeCards(atroposCards, cardIterator);

        cardIterator = AbstractDungeon.player.exhaustPile.group.iterator();
        removeCards(atroposCards, cardIterator);

        cardIterator = AbstractDungeon.player.limbo.group.iterator();
        removeCards(atroposCards, cardIterator);

        cardIterator = AbstractDungeon.player.hand.group.iterator();
        removeCards(atroposCards, cardIterator);
        return atroposCards;
    }

    private void removeCards(HashSet<AbstractCard> atroposCards, Iterator cardIterator) {
        AbstractCard c;
        while (cardIterator.hasNext()) {
            c = (AbstractCard) cardIterator.next();
            if (
                    c.cardID.equals("theWeaponMaster:AtroposSeveredSource") ||
                    c.cardID.equals("theWeaponMaster:AtroposSeveredScissors") ||
                    c.cardID.equals("theWeaponMaster:AtroposSeveredPath") ||
                    c.cardID.equals("theWeaponMaster:AtroposSeveredPain") ||
                    c.cardID.equals("theWeaponMaster:AtroposSeveredSoul")
            ) {
                atroposCards.remove(c);
            }
        }
    }
}
