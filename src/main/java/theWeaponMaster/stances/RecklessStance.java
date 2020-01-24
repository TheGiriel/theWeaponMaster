package theWeaponMaster.stances;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.AbstractStance;
import theWeaponMaster.TheWeaponMaster;

public class RecklessStance extends AbstractStance {

    public static String stanceID = TheWeaponMaster.makeID(RecklessStance.class.getSimpleName());

    public RecklessStance() {
        this.ID = stanceID;
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        if (damageType == DamageInfo.DamageType.NORMAL) {
            return damage * 1.15F;
        }
        return super.atDamageReceive(damage, damageType);
    }

    @Override
    public void onEnterStance() {
        AbstractDungeon.player.gameHandSize++;
    }

    @Override
    public void onExitStance() {
        AbstractDungeon.player.gameHandSize--;
    }

    @Override
    public void updateDescription() {

    }
}
