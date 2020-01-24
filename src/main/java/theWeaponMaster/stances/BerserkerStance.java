package theWeaponMaster.stances;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.AbstractStance;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.powers.ViciousPower;

public class BerserkerStance extends AbstractStance {

    public static String stanceID = TheWeaponMaster.makeID(BerserkerStance.class.getSimpleName());

    public BerserkerStance() {
        this.ID = stanceID;
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ViciousPower(AbstractDungeon.player, 1)));
        return super.atDamageReceive(damage, damageType);
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ViciousPower(AbstractDungeon.player, 1)));
        return super.atDamageGive(damage, type);
    }

    @Override
    public void onEnterStance() {
        ViciousPower.berserkerStanceBonus = 1;
        super.onEnterStance();
    }

    @Override
    public void onExitStance() {
        ViciousPower.berserkerStanceBonus = 0;
        super.onExitStance();
    }

    @Override
    public void updateDescription() {

    }
}
