package theWeaponMaster.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theWeaponMaster.cards.legendary_weapons.*;
import theWeaponMaster.relics.ArsenalRelic;

import java.util.ArrayList;

public class EndOfCombatWeaponSwap {
    ArrayList<AbstractCard> weaponArrayList = new ArrayList<>();
    ArrayList<AbstractCard> fenrirArrayList = new ArrayList<>();
    ArrayList<AbstractCard> cerberusArrayList = new ArrayList<>();
    ArrayList<AbstractCard> revenantArrayList = new ArrayList<>();
    ArrayList<AbstractCard> atroposArrayList = new ArrayList<>();
    ArrayList<AbstractCard> leviathanArrayList = new ArrayList<>();

    public EndOfCombatWeaponSwap() {
        fenrirArrayList.add(new FenrirLacerate());
        fenrirArrayList.add(new FenrirHeavySwing());
        fenrirArrayList.add(new FenrirShieldEater());
        fenrirArrayList.add(new FenrirProtectiveBlade());
        fenrirArrayList.add(new FenrirUnleashed());

        cerberusArrayList.add(new CerberusSlash());
        cerberusArrayList.add(new CerberusIaiSlash());
        cerberusArrayList.add(new CerberusEssenceSlash());
        cerberusArrayList.add(new CerberusModularSlash());
        cerberusArrayList.add(new CerberusDrainSlash());

        revenantArrayList.add(new RevenantRavenous());
        revenantArrayList.add(new RevenantChopChopCHOP());
        revenantArrayList.add(new RevenantHungrySteel());
        revenantArrayList.add(new RevenantNoseToTail());
        revenantArrayList.add(new RevenantBloodbath());

        atroposArrayList.add(new AtroposSeveredSource());
        atroposArrayList.add(new AtroposSeveredScissors());
        atroposArrayList.add(new AtroposSeveredPath());
        atroposArrayList.add(new AtroposSeveredPain());
        atroposArrayList.add(new AtroposSeveredSoul());

        leviathanArrayList.add(new LeviathanCrush());
        leviathanArrayList.add(new LeviathanGauntletCharger());
        leviathanArrayList.add(new LeviathanGroundSplitter());
        leviathanArrayList.add(new LeviathanDeepImpact());
        leviathanArrayList.add(new LeviathanEarthquake());

        weaponArrayList.addAll(fenrirArrayList);
        weaponArrayList.addAll(cerberusArrayList);
        weaponArrayList.addAll(revenantArrayList);
        weaponArrayList.addAll(atroposArrayList);
        weaponArrayList.addAll(leviathanArrayList);

        weaponArrayList.clear();

        switch (ArsenalRelic.currentWeapon) {
            case "Fenrir":
                giveWeapons(fenrirArrayList);
                break;
            case "Cerberus":
                giveWeapons(cerberusArrayList);
                break;
            case "Revenant":
                giveWeapons(revenantArrayList);
                break;
            case "Atropos":
                giveWeapons(atroposArrayList);
                break;
            case "Leviathan":
                giveWeapons(leviathanArrayList);
                break;
        }
    }

    public void giveWeapons(ArrayList toGive) {
        for (Object c : toGive) {
            AbstractDungeon.player.masterDeck.group.add((AbstractCard) c);
        }
    }
}
