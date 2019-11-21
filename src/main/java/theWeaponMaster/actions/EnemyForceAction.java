package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.HashSet;

import static com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.*;
import static theWeaponMaster.patches.WeaponMasterTags.*;

public class EnemyForceAction extends AbstractGameAction {

    //public static HashSet<AbstractMonster.Intent> intents = new HashSet<>();

    public EnemyForceAction() {

    }

    public static String doesntDefend() {
        return "This enemy isn't defending.";
    }
/*
    private static void INTIMIDATE(){
        intents.add(ATTACK);
        intents.add(ATTACK_DEFEND);
        intents.add(ATTACK_BUFF);
        intents.add(ATTACK_DEBUFF);
    }

    private static void MAGIC(){
        intents.add(ATTACK_BUFF);
        intents.add(ATTACK_DEBUFF);
        intents.add(DEFEND_BUFF);
        intents.add(DEFEND_DEBUFF);
        intents.add(BUFF);
        intents.add(DEBUFF);
        intents.add(STRONG_DEBUFF);
        intents.add(MAGIC);
    }

    private static void TAUNT(){
        intents.add(DEFEND);
        intents.add(DEFEND_BUFF);
        intents.add(DEFEND_DEBUFF);
        intents.add(BUFF);
        intents.add(DEBUFF);
        intents.add(STRONG_DEBUFF);
        intents.add(MAGIC);
    }*/

    public static String doesntAttack() {
        return "This enemy isn't defending.";
    }

    public static String noneDefending() {
        return "This enemy isn't defending.";
    }

    public static String noneAttacking() {
        return "This enemy isn't defending.";
    }

    public static HashSet<AbstractMonster.Intent> getIntents(AbstractCard card) {
        HashSet<AbstractMonster.Intent> intents = new HashSet<>();
        if (card.hasTag(TAUNT)) {
            intents.add(DEFEND);
            intents.add(DEFEND_BUFF);
            intents.add(DEFEND_DEBUFF);
            intents.add(BUFF);
            intents.add(DEBUFF);
            intents.add(STRONG_DEBUFF);
            intents.add(MAGIC);
        }
        if (card.hasTag(INTIMIDATE)) {
            intents.add(ATTACK);
            intents.add(ATTACK_DEFEND);
            intents.add(ATTACK_BUFF);
            intents.add(ATTACK_DEBUFF);
        }
        if (card.hasTag(IGNITE)) {
            intents.add(ATTACK_BUFF);
            intents.add(ATTACK_DEBUFF);
            intents.add(DEFEND_BUFF);
            intents.add(DEFEND_DEBUFF);
            intents.add(BUFF);
            intents.add(DEBUFF);
            intents.add(STRONG_DEBUFF);
            intents.add(MAGIC);
        }
        return intents;
    }

    @Override
    public void update() {

    }
}