package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theWeaponMaster.cards.revolver.*;
import theWeaponMaster.relics.RevolverRelic;

import java.util.ArrayList;
import java.util.HashSet;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class ReloadAction extends AbstractGameAction {

    public ReloadAction() {
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
        int extraDraw = 1;
        if (drawNumber != 0) {
            for (AbstractCard c : addToDeck) {
                player.discardPile.removeCard(c);
                player.drawPile.addToRandomSpot(c);
            }
            player.draw((drawNumber / 3) + extraDraw);
        } else {
            player.draw(extraDraw);
        }
        player.getRelic(RevolverRelic.ID).counter = RevolverRelic.SHOTS + 1;
        player.energy.use(-1);
        new RevolverRelic().stopPulse();
        isDone = true;
    }

    @Override
    public void update() {

    }
}
