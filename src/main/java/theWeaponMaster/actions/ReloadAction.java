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
        ArrayList<AbstractCard> addToDeck = new ArrayList<>();
        int drawNumber = 0;
        HashSet<String> discardedAmmoList = new HashSet<>();
        discardedAmmoList.add(RevolverStandard.ID);
        discardedAmmoList.add(RevolverDouble.ID);
        discardedAmmoList.add(RevolverHeavy.ID);
        discardedAmmoList.add(RevolverHollowPoint.ID);
        discardedAmmoList.add(RevolverBuckshot.ID);
        discardedAmmoList.add(RevolverFullMetal.ID);
        discardedAmmoList.add(RevolverLowRecoil.ID);
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
            player.draw((drawNumber / 3) + 1);
        } else {
            player.draw(drawNumber + 1);
        }
        player.getRelic(RevolverRelic.ID).counter = RevolverRelic.SHOTS + 1;
        player.energy.use(-1);
        new RevolverRelic().stopPulse();
    }

    @Override
    public void update() {

    }
}
