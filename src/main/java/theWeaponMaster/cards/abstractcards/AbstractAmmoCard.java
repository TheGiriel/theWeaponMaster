package theWeaponMaster.cards.abstractcards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.actions.ReloadAction;

public abstract class AbstractAmmoCard extends AbstractDynamicCard {

    public AbstractAmmoCard(String id, String img, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, color, rarity, target);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

        new ReloadAction();
    }
}
