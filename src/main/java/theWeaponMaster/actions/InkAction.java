package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class InkAction extends AbstractGameAction {


    private final AbstractCreature owner;
    private final AbstractCreature source;
    private int inkStrength;

    public InkAction(AbstractCreature owner, AbstractCreature source, int inkStrength ){
        this.owner = owner;
        this.source = source;

    }

    public float atDamageRececive(float damage, DamageInfo.DamageType damageType){
        if (this.inkStrength >= damage ){
            this.inkStrength -= damage;
        } else {
            damage -= this.inkStrength;
            this.inkStrength = 0;
        }
        return damage;
    }

    @Override
    public void update() {

    }


}
