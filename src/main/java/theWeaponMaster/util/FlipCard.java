package theWeaponMaster.util;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public interface FlipCard {

    void flipCard();

    void standardUse(AbstractPlayer p, AbstractMonster m);

    void flipUse(AbstractPlayer p, AbstractMonster m);
}
