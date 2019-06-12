package theWeaponMaster.cards.legendary_weapons.leviathan;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.cards.AbstractDynamicCard;

public class LeviathanEject extends AbstractDynamicCard {
    public LeviathanEject(String id, String img, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, color, rarity, target);
    }

    @Override
    public void upgrade() {

    }
    //TODO: Create a modular card to reload Leviathan attacks.
    // The first effect is a 0 energy card that ejects the three spent cartridges to deal damage to random enemies
    // and deals extra damage for every cartridge used. (Sent back to the deck.)
    // The second effect is a 0 energy card that reloads all cartridges and returns 1 energy if three were expended previously.
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}
