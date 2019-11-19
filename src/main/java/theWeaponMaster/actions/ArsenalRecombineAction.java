package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theWeaponMaster.cards.legendary_weapons.AtroposScissorHalf;
import theWeaponMaster.cards.legendary_weapons.AtroposSeveredScissors;

import java.util.HashSet;

public class ArsenalRecombineAction extends AbstractGameAction {

    public ArsenalRecombineAction() {
        recombine();
    }

    public static void recombine() {
        if (!AtroposSeveredScissors.leftHalf || !AtroposSeveredScissors.rightHalf) {
            return;
        }
        HashSet<String> toDelete = new HashSet<>();
        toDelete.add(AtroposSeveredScissors.ID);
        toDelete.add(AtroposScissorHalf.ID);

        HashSet<AbstractCard> deleteFrom = new HashSet<>();
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (toDelete.contains(c.cardID)) {
                deleteFrom.add(c);
            }
        }
        for (AbstractCard card : deleteFrom) {
            AbstractDungeon.player.exhaustPile.removeCard(card);
        }
        AtroposSeveredScissors.leftHalf = false;
        AtroposSeveredScissors.rightHalf = false;
        AtroposSeveredScissors.scissorFlip = false;
    }

    @Override
    public void update() {

    }
}
