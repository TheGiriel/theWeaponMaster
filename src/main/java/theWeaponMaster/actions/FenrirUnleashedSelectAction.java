package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Collections;
import java.util.Comparator;

import static theWeaponMaster.cards.FenrirUnleashed.targetList;

public class FenrirUnleashedSelectAction extends AbstractGameAction {


    public FenrirUnleashedSelectAction() {
        targetList.addAll(AbstractDungeon.getMonsters().monsters);
        /*for (AbstractMonster m : targetList) {
            TheWeaponMaster.logger.info(m.toString() + " <- monster, monster hp -> " + m.currentHealth);
        }*/
        Collections.sort(targetList, new MonsterHPComparator());
        /*
        for (AbstractMonster m : targetList) {
            TheWeaponMaster.logger.info("Sorted list:"  + m.toString() + " <- monster, monster hp -> " + m.currentHealth);
        }*/
    }

    @Override
    public void update() {

    }

    class MonsterHPComparator implements Comparator<AbstractMonster> {

        @Override
        public int compare(AbstractMonster m1, AbstractMonster m2) {

            return m1.currentHealth - m2.currentHealth;
        }
    }
}
