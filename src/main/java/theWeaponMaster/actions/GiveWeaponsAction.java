package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import theWeaponMaster.cards.legendary_weapons.*;
import theWeaponMaster.relics.*;

import java.util.ArrayList;
import java.util.HashSet;

public class GiveWeaponsAction extends AbstractGameAction {

    private ArrayList<AbstractCard> weaponList = new ArrayList<>();
    private HashSet<String> upgradeWeapons = new HashSet<>();
    private AbstractPlayer owner;

    public GiveWeaponsAction(String weapon){
        owner = AbstractDungeon.player;
        switch (weapon) {
            case "Fenrir":
                weaponList.add(new FenrirLacerate());
                weaponList.add(new FenrirHeavySwing());
                weaponList.add(new FenrirUnleashed());
                weaponList.add(new FenrirShieldEater());
                weaponList.add(new FenrirDefensiveStance());
                if (owner.hasRelic(SplinteringSteelRelic.ID)) {
                    upgradeWeapons.addAll(SplinteringSteelRelic.getWeaponUpgrade());
                }
                break;

            case "Cerberus":
                weaponList.add(new CerberusSlash());
                weaponList.add(new CerberusIaiSlash());
                weaponList.add(new CerberusEssenceSlash());
                weaponList.add(new CerberusModularSlash());
                weaponList.add(new CerberusDrainSlash());
                if (owner.hasRelic(HellhoundOilRelic.ID)) {
                    upgradeWeapons.addAll(HellhoundOilRelic.getWeaponUpgrade());
                }
                break;

            case "Revenant Cleaver":
                weaponList.add(new RevenantRavenous());
                weaponList.add(new RevenantChopChopCHOP());
                weaponList.add(new RevenantHungrySteel());
                weaponList.add(new RevenantNoseToTail());
                weaponList.add(new RevenantBloodbath());
                if (owner.hasRelic(GhoulskinSheathRelic.ID)) {
                    upgradeWeapons.addAll(GhoulskinSheathRelic.getWeaponUpgrade());
                }
                break;

                //Atropos cards
            case "Atropos' Shears":
                weaponList.add(new AtroposSeveredSource());
                weaponList.add(new AtroposSeveredScissors());
                weaponList.add(new AtroposSeveredPath());
                weaponList.add(new AtroposSeveredPain());
                weaponList.add(new AtroposSeveredSoul());
                if (owner.hasRelic(ManaWhetstoneRelic.ID)) {
                    upgradeWeapons.addAll(ManaWhetstoneRelic.getWeaponUpgrade());
                }
                break;
            case "Scissor Half":
                weaponList.add(new AtroposSeveredScissors());
                if (owner.hasRelic(ManaWhetstoneRelic.ID)) {
                    upgradeWeapons.addAll(ManaWhetstoneRelic.getWeaponUpgrade());
                }
                break;

            case "Leviathan Gauntlet":
                weaponList.add(new LeviathanCrush());
                weaponList.add(new LeviathanGauntletCharger());
                weaponList.add(new LeviathanGroundSplitter());
                weaponList.add(new LeviathanDeepImpact());
                weaponList.add(new LeviathanEarthquake());
                if (owner.hasRelic(ShockwaveModulatorRelic.ID)) {
                    upgradeWeapons.addAll(ShockwaveModulatorRelic.getWeaponUpgrade());
                }
                break;
        }
        giveWeapons();
        weaponList.clear();
        isDone = true;
    }

    private void giveWeapons(){
        for (AbstractCard c : weaponList){
            owner.masterDeck.addToBottom(c);
            AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, true, false));
            if (upgradeWeapons.contains(c.cardID)) {
                c.upgrade();
            }
        }
    }

    @Override
    public void update() {
    }
}
