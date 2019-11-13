package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAndPoofAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.HashSet;

public class ImprovedWeaponCardRemovalAction extends AbstractGameAction {

    private ArrayList<AbstractCard> deleteGroup = new ArrayList<>();
    private HashSet<String> weaponSetHash = new HashSet<>();

    //TODO: Almost perfect, just gotta get the purge effect to happen before the cards are removed.
    private void populateArrayList(){
        weaponSetHash.add("theWeaponMaster:FenrirLacerate");
        weaponSetHash.add("theWeaponMaster:FenrirHeavySwing");
        weaponSetHash.add("theWeaponMaster:FenrirShieldEater");
        weaponSetHash.add("theWeaponMaster:FenrirDefensiveStance");
        weaponSetHash.add("theWeaponMaster:FenrirUnleashed");

        weaponSetHash.add("theWeaponMaster:CerberusSlash");
        weaponSetHash.add("theWeaponMaster:CerberusIaiSlash");
        weaponSetHash.add("theWeaponMaster:CerberusEssenceSlash");
        weaponSetHash.add("theWeaponMaster:CerberusModularSlash");
        weaponSetHash.add("theWeaponMaster:CerberusDrainSlash");

        weaponSetHash.add("theWeaponMaster:RevenantRavenous");
        weaponSetHash.add("theWeaponMaster:RevenantChopChopCHOP");
        weaponSetHash.add("theWeaponMaster:RevenantHungrySteel");
        weaponSetHash.add("theWeaponMaster:RevenantNoseToTail");
        weaponSetHash.add("theWeaponMaster:RevenantBloodbath");

        weaponSetHash.add("theWeaponMaster:AtroposSeveredSource");
        weaponSetHash.add("theWeaponMaster:AtroposSeveredScissors");
        weaponSetHash.add("theWeaponMaster:AtroposScissorHalf");//unlisted
        weaponSetHash.add("theWeaponMaster:AtroposSeveredPath");
        weaponSetHash.add("theWeaponMaster:AtroposSeveredPain");
        weaponSetHash.add("theWeaponMaster:AtroposSeveredSoul");

        weaponSetHash.add("theWeaponMaster:LeviathanCrush");
        weaponSetHash.add("theWeaponMaster:LeviathanEject");
        weaponSetHash.add("theWeaponMaster:LeviathanReload");//unlisted
        weaponSetHash.add("theWeaponMaster:LeviathanGroundSplitter");
        weaponSetHash.add("theWeaponMaster:LeviathanDeepImpact");
        weaponSetHash.add("theWeaponMaster:LeviathanEarthquake");
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
