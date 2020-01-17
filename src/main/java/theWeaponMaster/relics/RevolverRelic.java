package theWeaponMaster.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.ReloadAction;
import theWeaponMaster.cards.abstractcards.AbstractRevolverCard;
import theWeaponMaster.cards.revolver.RevolverUnload;
import theWeaponMaster.util.TextureLoader;

import java.util.ArrayList;

import static theWeaponMaster.TheWeaponMaster.makeRelicOutlinePath;
import static theWeaponMaster.TheWeaponMaster.makeRelicPath;
import static theWeaponMaster.patches.WeaponMasterTags.AMMUNITION;

public class RevolverRelic extends CustomRelic {

    public static final String ID = TheWeaponMaster.makeID(RevolverRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public static final int SHOTS = 6;
    public static int shotsLeft;
    public static ArrayList<AbstractCard> ammoCards = new ArrayList<>();

    public RevolverRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.CLINK);
        this.counter = shotsLeft = SHOTS;
        this.description = DESCRIPTIONS[0];
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.cardID.equals(RevolverUnload.ID) && card.hasTag(AMMUNITION) && counter >= 1) {
            this.counter--;
            if (counter == 0) {
                setReload();
            }
            return;
        } else if (card.hasTag(AMMUNITION) && counter <= 0) {
            AbstractDungeon.actionManager.addToBottom(new ReloadAction());
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
            resetCards();
            return;
        }
        counter = shotsLeft = Math.min(Math.max(counter, 0), 6);
        if (AbstractDungeon.player.hasRelic(HeavyDrum.ID)) {
            counter = shotsLeft = Math.min(Math.max(counter, 0), 5);
        }
    }


    private void resetCards() {
        ammoCards.addAll(AbstractDungeon.player.drawPile.group);
        ammoCards.addAll(AbstractDungeon.player.hand.group);
        ammoCards.addAll(AbstractDungeon.player.discardPile.group);
        ammoCards.addAll(AbstractDungeon.player.exhaustPile.group);
        for (AbstractCard c : ammoCards) {
            if (c instanceof AbstractRevolverCard) {
                c.type = AbstractCard.CardType.ATTACK;
                ((AbstractRevolverCard) c).setNormalDescription();
            }
        }
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        counter = shotsLeft = Math.min(Math.max(counter, 0), 6);
        if (AbstractDungeon.player.hasRelic(HeavyDrum.ID)) {
            counter = shotsLeft = Math.min(Math.max(counter, 0), 5);
        }
    }

    public void setReload() {
        ammoCards.addAll(AbstractDungeon.player.drawPile.group);
        ammoCards.addAll(AbstractDungeon.player.hand.group);
        ammoCards.addAll(AbstractDungeon.player.discardPile.group);
        ammoCards.addAll(AbstractDungeon.player.exhaustPile.group);
        for (AbstractCard c : ammoCards) {
            if (c instanceof AbstractRevolverCard) {
                c.rawDescription = "Reload";
                c.type = AbstractCard.CardType.SKILL;
                c.target = AbstractCard.CardTarget.SELF;
                c.costForTurn = 0;
                c.initializeDescription();
            }
        }
    }
}
