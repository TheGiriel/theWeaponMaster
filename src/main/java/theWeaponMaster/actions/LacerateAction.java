package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class LacerateAction extends AbstractGameAction{

    private final AbstractCreature owner;
    private final AbstractCreature source;

    public LacerateAction(AbstractCreature owner, AbstractCreature source, int lacerateStack){
        this.owner = owner;
        this.source = source;
        this.amount = lacerateStack;
    }

    public int bleedDamage() {
        return (int) (Math.ceil(this.owner.maxHealth * 0.02D * this.amount));
    }

    @Override
    public void update() {
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this.owner, this.source, bleedDamage(), AbstractGameAction.AttackEffect.POISON));
        }
        isDone = true;
    }
}
