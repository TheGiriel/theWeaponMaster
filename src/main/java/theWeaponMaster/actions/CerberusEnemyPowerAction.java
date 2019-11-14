package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;

import java.util.HashMap;
import java.util.HashSet;

public class CerberusEnemyPowerAction extends AbstractGameAction {

    HashSet<AbstractPower> legalPower = new HashSet<>();
    HashMap<String, Integer> enemyPowers = new HashMap<>();
    private AbstractPlayer p = AbstractDungeon.player;

    @Override
    public void update() {
        //Standard applicable
        enemyPowers.put(RitualPower.POWER_ID, 3);
        enemyPowers.put(AngryPower.POWER_ID, 1);
        enemyPowers.put(MetallicizePower.POWER_ID, 3);
        enemyPowers.put(ArtifactPower.POWER_ID, 1);
        enemyPowers.put(SharpHidePower.POWER_ID, 3);
        enemyPowers.put(PlatedArmorPower.POWER_ID, 4);
        enemyPowers.put(BarricadePower.POWER_ID, null);

        //Do something special with them. Offensive
        enemyPowers.put(PainfulStabsPower.POWER_ID, null);
        enemyPowers.put(MalleablePower.POWER_ID, 4);
        enemyPowers.put(SlowPower.POWER_ID, 1);
    }
}
