package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theWeaponMaster.cards.revolver.*;
import theWeaponMaster.relics.RevolverRelic;
import theWeaponMaster.relics.UncommonRelicHeavyDrum;

import java.util.ArrayList;
import java.util.HashSet;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class ReloadAction extends AbstractGameAction {

    public static int baseDraw = 1;

    public ReloadAction() {
        if (player.hasRelic(UncommonRelicHeavyDrum.ID)) {
            baseDraw++;
        }
        int drawNumber = 0;
        HashSet<String> discardedAmmoList = new HashSet<>();

        discardedAmmoList.add(Strike_WeaponMaster.ID);
        discardedAmmoList.add(RevolverTwinned.ID);
        discardedAmmoList.add(RevolverMagnum.ID);
        discardedAmmoList.add(RevolverHollowPoint.ID);
        discardedAmmoList.add(RevolverBuckshot.ID);
        discardedAmmoList.add(RevolverFullMetal.ID);
        discardedAmmoList.add(RevolverLowRecoil.ID);

        ArrayList<AbstractCard> addToDeck = new ArrayList<>();

        if (!player.discardPile.isEmpty()) {
            for (AbstractCard c : player.discardPile.group) {
                if (discardedAmmoList.contains(c.cardID)) {
                    addToDeck.add(c);
                    drawNumber++;
                }
            }
        }
        if (drawNumber != 0) {
            for (AbstractCard c : addToDeck) {
                player.discardPile.removeCard(c);
                player.drawPile.addToRandomSpot(c);
            }
            player.draw((drawNumber / 3) + baseDraw);
        } else {
            player.draw(baseDraw);
        }
        player.energy.use(-1);
        new RevolverRelic().stopPulse();
        if (player.hasRelic(UncommonRelicHeavyDrum.ID)) {
            player.getRelic(RevolverRelic.ID).counter = 5;
        } else player.getRelic(RevolverRelic.ID).counter = 6;
        isDone = true;
    }

    @Override
    public void update() {

    }
}
