package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import theWeaponMaster.relics.HeavyDrum;
import theWeaponMaster.relics.RevolverRelic;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class ReloadAction extends AbstractGameAction {


    public ReloadAction() {

        if (!player.discardPile.isEmpty()) {
            for (int i = Math.min(player.discardPile.size(), 3); i > 0; i--) {
                player.discardPile.moveToDeck(player.discardPile.getBottomCard(), true);
            }
            player.draw(1);
        }

        if (player.hasRelic(HeavyDrum.ID)) {
            player.getRelic(RevolverRelic.ID).counter = 5;
        } else player.getRelic(RevolverRelic.ID).counter = 6;

        isDone = true;
    }

    @Override
    public void update() {

    }
}
