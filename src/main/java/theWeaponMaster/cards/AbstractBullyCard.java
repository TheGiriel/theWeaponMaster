package theWeaponMaster.cards;

public abstract class AbstractBullyCard extends AbstractDynamicCard {


    public int bullyNumber;
    public int baseBullyNumber;
    public boolean upgradedBullyNumber;
    public boolean isBullyModified;

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

    public void reduceBullyCost(int amount) {
        bullyNumber -= amount;
        bullyNumber = baseBullyNumber;
        upgradedBullyNumber = true;
    }

}
