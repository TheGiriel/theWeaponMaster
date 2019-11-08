package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import theWeaponMaster.cards.*;
import theWeaponMaster.cards.Not_finished.*;

import java.util.ArrayList;

public class GiveWeaponsAction extends AbstractGameAction {

    private ArrayList<AbstractCard> weaponList = new ArrayList<>();
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
                break;
            case "Cerberus":
                weaponList.add(new CerberusSlash());
                weaponList.add(new CerberusIaiSlash());
                weaponList.add(new CerberusEssenceSlash());
                weaponList.add(new CerberusModularSlash());
                weaponList.add(new CerberusDrainSlash());
                break;
            case "Revenant Cleaver":
                weaponList.add(new RevenantRavenous());
                weaponList.add(new RevenantChopChopCHOP());
                weaponList.add(new RevenantHungrySteel());
                weaponList.add(new RevenantSnoutToTail());
                weaponList.add(new RevenantBloodbath());
                break;

                //Atropos cards
            case "Atropos' Shears":
                weaponList.add(new AtroposSeveredSource());
                weaponList.add(new AtroposSeveredScissors());
                weaponList.add(new AtroposSeveredPath());
                weaponList.add(new AtroposSeveredPain());
                weaponList.add(new AtroposSeveredSoul());
                break;
            case "Scissor Half":
                weaponList.add(new AtroposScissorHalf());
                weaponList.add(new AtroposScissorHalf());
                break;
                //Leviathan cards
            case "Leviathan Gauntlet":
                weaponList.add(new LeviathanCrush());
                weaponList.add(new LeviathanEject());
                weaponList.add(new LeviathanGroundSplitter());
                weaponList.add(new LeviathanDeepImpact());
                weaponList.add(new LeviathanEarthquake());
                break;/*
            case "Eject":
                weaponList.add(new LeviathanEject());
                break;
            case "Reload":
                weaponList.add(new LeviathanReload());
                break;*/
        }
        giveWeapons();
        weaponList.clear();
        isDone = true;
    }

    private void giveWeapons(){
        for (AbstractCard c : weaponList){
            owner.masterDeck.addToBottom(c);
            AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, true, false));
        }
    }

    @Override
    public void update() {

    }
}
