package theWeaponMaster.cards.abstractcards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.HashSet;

public abstract class AbstractRevolverCard extends AbstractDynamicCard {

    private HashSet<AbstractMonster.Intent> intents = new HashSet<>();

    public AbstractRevolverCard(String id,
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
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    @Override
    public void applyPowers() {
    }
}
