package theWeaponMaster.cards;

import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.HashSet;

public abstract class AbstractBullyCard extends AbstractDynamicCard {


    public int bullyNumber;
    public int baseBullyNumber;
    public boolean upgradedBullyNumber;
    public boolean isBullyModified;
    private HashSet<AbstractMonster.Intent> intents = new HashSet<>();

    public AbstractBullyCard(String id,
                             String img,
                             int cost,
                             CardType type,
                             CardColor color,
                             CardRarity rarity,
                             CardTarget target) {

        super(id, img, cost, type, color, rarity, target);
    }

    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedBullyNumber) {
            bullyNumber = baseBullyNumber;
            isBullyModified = true;
        }
    }

    public void increaseVicious(int amount) {
        bullyNumber += amount;
        bullyNumber = baseBullyNumber;
        this.upgradedBullyNumber = true;
    }

}
