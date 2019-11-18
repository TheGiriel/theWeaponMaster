package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.powers.ManaBurnPower;

import java.util.HashSet;

import static com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.*;

public class ManaBurnAction extends AbstractGameAction {

    public static int manaBurnTotal;
    public static int ownerHP;
    private AbstractMonster m;
    private int manaBurnIntensity;
    public static HashSet<AbstractMonster.Intent> intent = new HashSet<>();
    private AbstractCreature owner;
    private AbstractCreature source;

    public ManaBurnAction(AbstractMonster owner, AbstractPlayer source) {
        this.owner = owner;
        this.source = source;
        manaBurnTotal = amount;
        ownerHP = owner.maxHealth;

        intent.add(ATTACK_BUFF);
        intent.add(ATTACK_DEBUFF);
        intent.add(DEFEND_BUFF);
        intent.add(DEFEND_DEBUFF);
        intent.add(BUFF);
        intent.add(DEBUFF);
        intent.add(STRONG_DEBUFF);
        intent.add(MAGIC);
    }

    public static void ignite(AbstractMonster m, int ignitePower) {
        if (m.hasPower(ManaBurnPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new LoseHPAction(m, AbstractDungeon.player, (int) Math.ceil(ownerHP * ManaBurnPower.IGNITE * ignitePower), AbstractGameAction.AttackEffect.FIRE));
        }
    }

    @Override
    public void update() {

    }

}
