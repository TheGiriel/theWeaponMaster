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
import theWeaponMaster.cards.CerberusIaiSlash;
import theWeaponMaster.cards.Not_finished.*;
import theWeaponMaster.util.TextureLoader;

import java.util.HashSet;
import java.util.Iterator;

public class CerberusPower extends AbstractPower {

    public static final String POWER_ID = "CerberusPower";
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("CerberusPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(DefaultMod.makePowerPath("cerberus_placeholder_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(DefaultMod.makePowerPath("cerberus_placeholder_32.png"));

    public CerberusPower(AbstractPlayer player) {
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
        AbstractDungeon.player.masterDeck.addToBottom(new CerberusSlash());
        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(new CerberusSlash(), true, false));
        AbstractDungeon.player.masterDeck.addToBottom(new CerberusIaiSlash());
        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(new CerberusIaiSlash(), true, false));
        AbstractDungeon.player.masterDeck.addToBottom(new CerberusEssenceSlash());
        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(new CerberusEssenceSlash(), true, false));
        AbstractDungeon.player.masterDeck.addToBottom(new CerberusModularSlash());
        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(new CerberusModularSlash(), true, false));
        AbstractDungeon.player.masterDeck.addToBottom(new CerberusDrainSlash());
        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(new CerberusDrainSlash(), true, false));
    }

    @Override
    public void onRemove() {
        AbstractDungeon.player.masterDeck.removeCard("theWeaponMaster:CerberusSlash");
        AbstractDungeon.player.masterDeck.removeCard("theWeaponMaster:CerberusIaiSlash");
        AbstractDungeon.player.masterDeck.removeCard("theWeaponMaster:CerberusEssenceSlash");
        AbstractDungeon.player.masterDeck.removeCard("theWeaponMaster:CerberusModularSlash");
        AbstractDungeon.player.masterDeck.removeCard("theWeaponMaster:CerberusDrainSlash");

        removeCerberus();
    }

    private HashSet<AbstractCard> removeCerberus() {
        HashSet<AbstractCard> cerberusCards = new HashSet<AbstractCard>();

        AbstractCard card = AbstractDungeon.player.cardInUse;
        if (card != null && card.cardID.equals(this)) {
            cerberusCards.remove(card);
        }

        Iterator cardIterator = AbstractDungeon.player.drawPile.group.iterator();
        removeCards(cerberusCards, cardIterator);

        cardIterator = AbstractDungeon.player.discardPile.group.iterator();
        removeCards(cerberusCards, cardIterator);

        cardIterator = AbstractDungeon.player.exhaustPile.group.iterator();
        removeCards(cerberusCards, cardIterator);

        cardIterator = AbstractDungeon.player.limbo.group.iterator();
        removeCards(cerberusCards, cardIterator);

        cardIterator = AbstractDungeon.player.hand.group.iterator();
        removeCards(cerberusCards, cardIterator);
        return cerberusCards;
    }

    private void removeCards(HashSet<AbstractCard> cerberusCards, Iterator cardIterator) {
        AbstractCard c;
        while (cardIterator.hasNext()) {
            c = (AbstractCard) cardIterator.next();
            if (
                    c.cardID.equals("theWeaponMaster:CerberusSlash")        ||
                    c.cardID.equals("theWeaponMaster:CerberusIaiSlash")     ||
                    c.cardID.equals("theWeaponMaster:CerberusEssenceSlash") ||
                    c.cardID.equals("theWeaponMaster:CerberusModularSlash") ||
                    c.cardID.equals("theWeaponMaster:CerberusDrainSlash")
            ) {
                cerberusCards.remove(c);
            }
        }
    }


}
