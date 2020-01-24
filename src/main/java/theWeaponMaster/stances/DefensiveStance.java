package theWeaponMaster.stances;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.AbstractStance;
import theWeaponMaster.TheWeaponMaster;

public class DefensiveStance extends AbstractStance {

    public static String stanceID = TheWeaponMaster.makeID(DefensiveStance.class.getSimpleName());
    public int i;

    public DefensiveStance() {
        this.ID = stanceID;
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return damage * 0.9F;
        }
        return super.atDamageGive(damage, type);
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        if (damageType == DamageInfo.DamageType.NORMAL) {
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, (int) (damage * totalTurns())));
        }
        return super.atDamageReceive(damage, damageType);
    }

    @Override
    public void atStartOfTurn() {

        super.atStartOfTurn();
    }

    private float totalTurns() {
        return 2F;
    }

    @Override
    public void updateDescription() {
    }
}
