package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theWeaponMaster.cards.Not_finished.*;
import theWeaponMaster.relics.RevolverRelic;

import java.util.HashSet;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class ReloadAction extends AbstractGameAction {

    private HashSet<String> discardedAmmo = new HashSet<>();
    private int drawNumber = 0;

    public ReloadAction() {
        discardedAmmo.add(RevolverStandard.ID);
        discardedAmmo.add(RevolverDouble.ID);
        discardedAmmo.add(RevolverHeavy.ID);
        discardedAmmo.add(RevolverHollowPoint.ID);
        discardedAmmo.add(RevolverBuckshot.ID);
        discardedAmmo.add(RevolverFullMetal.ID);
        discardedAmmo.add(RevolverLowRecoil.ID);
        if (player.hasRelic(RevolverRelic.ID) && player.getRelic(RevolverRelic.ID).counter <= 0 && player.discardPile != null) {
            for (AbstractCard ammoCheck : player.discardPile.group) {
                if (discardedAmmo.contains(ammoCheck.cardID)) {
                    player.drawPile.addToRandomSpot(ammoCheck);
                    player.discardPile.removeCard(ammoCheck);
                    drawNumber++;
                }
            }
        }
        player.draw(drawNumber / 3);
        player.getRelic(RevolverRelic.ID).counter = RevolverRelic.SHOTS + 1;
    }

    @Override
    public void update() {
    }
}
