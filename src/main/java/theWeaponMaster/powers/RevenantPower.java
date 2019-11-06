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
import theWeaponMaster.cards.Not_finished.*;
import theWeaponMaster.cards.*;
import theWeaponMaster.util.TextureLoader;

import java.util.HashSet;
import java.util.Iterator;

public class RevenantPower extends AbstractPower {


    public static final String POWER_ID = "RevenantPower";
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("LeviathanPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(DefaultMod.makePowerPath("leviathan_placeholder_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(DefaultMod.makePowerPath("leviathan_placeholder_32.png"));

    public RevenantPower(AbstractPlayer player) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = player;
        type = PowerType.BUFF;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        this.description = DESCRIPTION[0];
        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        AbstractDungeon.player.masterDeck.addToBottom(new RevenantRavenous());
        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(new RevenantRavenous(), true, false));
        AbstractDungeon.player.masterDeck.addToBottom(new RevenantChopChopCHOP());
        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(new RevenantChopChopCHOP(), true, false));
        AbstractDungeon.player.masterDeck.addToBottom(new RevenantHungrySteel());
        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(new RevenantHungrySteel(), true, false));
        AbstractDungeon.player.masterDeck.addToBottom(new RevenantSnoutToTail());
        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(new RevenantSnoutToTail(), true, false));
        AbstractDungeon.player.masterDeck.addToBottom(new RevenantBloodbath());
        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(new RevenantBloodbath(), true, false));
    }

    @Override
    public void onRemove() {
        AbstractDungeon.player.masterDeck.removeCard("theWeaponMaster:RevenantRavenous");
        AbstractDungeon.player.masterDeck.removeCard("theWeaponMaster:RevenantChopChopCHOP");
        AbstractDungeon.player.masterDeck.removeCard("theWeaponMaster:RevenantHungrySteel");
        AbstractDungeon.player.masterDeck.removeCard("theWeaponMaster:RevenantSnoutToTail");
        AbstractDungeon.player.masterDeck.removeCard("theWeaponMaster:RevenantBloodbath");

        removeRevenant();
    }

    private HashSet<AbstractCard> removeRevenant() {
        HashSet<AbstractCard> revenantCards = new HashSet<AbstractCard>();

        AbstractCard card = AbstractDungeon.player.cardInUse;
        if (card != null && card.cardID.equals(this)) {
            revenantCards.remove(card);
        }

        Iterator cardIterator = AbstractDungeon.player.drawPile.group.iterator();
        removeCards(revenantCards, cardIterator);

        cardIterator = AbstractDungeon.player.discardPile.group.iterator();
        removeCards(revenantCards, cardIterator);

        cardIterator = AbstractDungeon.player.exhaustPile.group.iterator();
        removeCards(revenantCards, cardIterator);

        cardIterator = AbstractDungeon.player.limbo.group.iterator();
        removeCards(revenantCards, cardIterator);

        cardIterator = AbstractDungeon.player.hand.group.iterator();
        removeCards(revenantCards, cardIterator);
        return revenantCards;
    }

    private void removeCards(HashSet<AbstractCard> revenantCards, Iterator cardIterator) {
        AbstractCard c;
        while (cardIterator.hasNext()) {
            c = (AbstractCard) cardIterator.next();
            if (
                    c.cardID.equals("theWeaponMaster:RevenantRavenous")|
                    c.cardID.equals("theWeaponMaster:RevenantChopChopCHOP")||
                    c.cardID.equals("theWeaponMaster:RevenantHungrySteel")||
                    c.cardID.equals("theWeaponMaster:RevenantSnoutToTail")||
                    c.cardID.equals("theWeaponMaster:RevenantBloodbath")
            ) {
                revenantCards.remove(c);
            }
        }
    }

}
