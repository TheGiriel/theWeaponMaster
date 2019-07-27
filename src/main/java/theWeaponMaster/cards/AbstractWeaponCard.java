package theWeaponMaster.cards;

public abstract class AbstractWeaponCard extends AbstractDynamicCard {

    public static String weapon;

    public AbstractWeaponCard(String id, String img, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, color, rarity, target);
    }
}
