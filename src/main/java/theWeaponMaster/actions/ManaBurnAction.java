package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class ManaBurnAction extends AbstractGameAction {

    private final AbstractCreature owner;
    private final AbstractCreature source;
    private AbstractMonster m;
    private int manaBurnIntensity;

    public ManaBurnAction(AbstractCreature owner, AbstractCreature source) {
        this.owner = owner;
        this.m = (AbstractMonster) this.owner;
        this.source = source;

        this.manaBurnIntensity = (int)(Math.ceil(this.m.maxHealth*0.03D));

    }

    public int burnDamage (AbstractMonster m, int multiplier){
        return (int)(Math.ceil(this.m.maxHealth*0.03D*multiplier));
    }

    @Override
    public void update() {
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            if (this.m.intent == AbstractMonster.Intent.ATTACK_BUFF || this.m.intent == AbstractMonster.Intent.ATTACK_DEBUFF || this.m.intent == AbstractMonster.Intent.DEFEND_BUFF || this.m.intent == AbstractMonster.Intent.DEFEND_DEBUFF) {
                AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this.owner, this.source, burnDamage(m, 1), AbstractGameAction.AttackEffect.FIRE));
            } else if (this.m.intent == AbstractMonster.Intent.BUFF || this.m.intent == AbstractMonster.Intent.DEBUFF) {
                AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this.owner, this.source, burnDamage(m, 2), AbstractGameAction.AttackEffect.FIRE));
            } else if (this.m.intent == AbstractMonster.Intent.STRONG_DEBUFF || this.m.intent == AbstractMonster.Intent.MAGIC) {
                AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this.owner, this.source, burnDamage(m, 3), AbstractGameAction.AttackEffect.FIRE));
            }
        }
        isDone = true;
    }
}
