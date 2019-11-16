package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.HashSet;

import static com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.*;

public class ManaBurnAction extends AbstractGameAction {

    private final AbstractCreature owner;
    private final AbstractCreature source;
    private AbstractMonster m;
    private int manaBurnIntensity;
    public static HashSet<AbstractMonster.Intent> intent = new HashSet<>();
    private int manaBurnStack;

    public ManaBurnAction(AbstractCreature owner, AbstractCreature source, int manaBurnStack, double manaburnDamage) {
        this.owner = owner;
        this.m = (AbstractMonster) this.owner;
        this.source = source;
        this.manaBurnStack = manaBurnStack;

        this.manaBurnIntensity = (int) (Math.ceil(this.m.maxHealth * manaburnDamage * this.manaBurnStack));

        intent.add(ATTACK_BUFF);
        intent.add(ATTACK_DEBUFF);
        intent.add(DEFEND_BUFF);
        intent.add(DEFEND_DEBUFF);
        intent.add(BUFF);
        intent.add(DEBUFF);
        intent.add(STRONG_DEBUFF);
        intent.add(MAGIC);
    }

    public int burnDamage() {
        return this.manaBurnIntensity;
    }

    @Override
    public void update() {
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            if (intent.contains(this.m.intent)) {
                AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this.owner, this.source, burnDamage(), AbstractGameAction.AttackEffect.FIRE));
            }
        }
        isDone = true;
    }
}
