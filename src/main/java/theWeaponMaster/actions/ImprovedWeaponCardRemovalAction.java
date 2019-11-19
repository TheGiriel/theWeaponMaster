package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAndPoofAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theWeaponMaster.cards.legendary_weapons.*;
import theWeaponMaster.cards.wip.*;

import java.util.ArrayList;
import java.util.HashSet;

public class ImprovedWeaponCardRemovalAction extends AbstractGameAction {

    private HashSet<String> weaponSetHash = new HashSet<>();
    private ArrayList<AbstractCard> deleteGroup = new ArrayList<>();

    //TODO: Almost perfect, just gotta get the purge effect to happen before the cards are removed.
    private void populateArrayList(){
        weaponSetHash.add(FenrirLacerate.ID);
        weaponSetHash.add(FenrirHeavySwing.ID);
        weaponSetHash.add(FenrirShieldEater.ID);
        weaponSetHash.add(FenrirDefensiveStance.ID);
        weaponSetHash.add(FenrirUnleashed.ID);

        weaponSetHash.add(CerberusSlash.ID);
        weaponSetHash.add(CerberusIaiSlash.ID);
        weaponSetHash.add(CerberusEssenceSlash.ID);
        weaponSetHash.add(CerberusModularSlash.ID);
        weaponSetHash.add(CerberusDrainSlash.ID);

        weaponSetHash.add(RevenantRavenous.ID);
        weaponSetHash.add(RevenantChopChopCHOP.ID);
        weaponSetHash.add(RevenantHungrySteel.ID);
        weaponSetHash.add(RevenantNoseToTail.ID);
        weaponSetHash.add(RevenantBloodbath.ID);

        weaponSetHash.add(AtroposSeveredSource.ID);
        weaponSetHash.add(AtroposSeveredScissors.ID);
        weaponSetHash.add(AtroposScissorHalf.ID);
        weaponSetHash.add(AtroposSeveredPath.ID);
        weaponSetHash.add(AtroposSeveredPain.ID);
        weaponSetHash.add(AtroposSeveredSoul.ID);

        weaponSetHash.add(LeviathanCrush.ID);
        weaponSetHash.add(LeviathanEject.ID);
        weaponSetHash.add(LeviathanGroundSplitter.ID);
        weaponSetHash.add(LeviathanDeepImpact.ID);
        weaponSetHash.add(LeviathanEarthquake.ID);
    }

    public ImprovedWeaponCardRemovalAction() {
        populateArrayList();


        for (AbstractCard copyCheck : AbstractDungeon.player.masterDeck.group) {
            collectMethod(copyCheck);
        }
        deleteMethod("Master");
        deleteGroup.clear();

        for (AbstractCard copyCheck : AbstractDungeon.player.hand.group) {
            collectMethod(copyCheck);
        }
        deleteMethod("Hand");
        deleteGroup.clear();

        for (AbstractCard copyCheck : AbstractDungeon.player.drawPile.group) {
            collectMethod(copyCheck);
        }
        deleteMethod("Draw");
        deleteGroup.clear();

        for (AbstractCard copyCheck : AbstractDungeon.player.discardPile.group) {
            collectMethod(copyCheck);
        }

        deleteMethod("Discard");
        deleteGroup.clear();
    }

    public void collectMethod(AbstractCard copyCheck){
        if (weaponSetHash.contains(copyCheck.cardID)) {
            deleteGroup.add(copyCheck);
        }
    }

    private void deleteMethod(String group){
        switch (group){
            case "Master":
                for (AbstractCard card : deleteGroup) {
                    AbstractDungeon.player.masterDeck.removeCard(card);
                }
                break;
            case "Hand":
                for (AbstractCard card : deleteGroup) {
                    AbstractDungeon.actionManager.addToBottom(new ShowCardAndPoofAction(card));
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5f));
                    AbstractDungeon.player.hand.removeCard(card);
                }
                break;
            case "Draw":
                for (AbstractCard card : deleteGroup) {
                    AbstractDungeon.player.drawPile.removeCard(card);
                }
                break;
            case "Discard":
                for (AbstractCard card : deleteGroup) {
                    AbstractDungeon.player.discardPile.removeCard(card);
                }
                break;
        }
    }

    @Override
    public void update() {

    }
}
