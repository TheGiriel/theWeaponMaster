package theWeaponMaster.cards.abstractcards;

public abstract class AbstractWeapons extends AbstractDynamicCard{

    public AbstractWeapons(
            String id,
            String name,
            String img,
            int cost,
            CardType type,
            CardColor color,
            CardRarity rarity,
            CardTarget target) {

        super(id, img, cost, type, color, rarity, target);
    }
}
