package theWeaponMaster.cards.legendary_weapons;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.cards.AbstractDynamicCard;
import theWeaponMaster.characters.TheWeaponMaster;

import static theWeaponMaster.DefaultMod.makeCardPath;

public class LeviathanEject extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(LeviathanEject.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG = makeCardPath("Attack.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheWeaponMaster.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int UPGRADED_DAMAGE = 3;
    private static final int MAGIC_NUMBER = 1;

    public LeviathanEject() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void upgrade() {

    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        //TODO: Create a modular card to reload Leviathan attacks. # The first effect is a 0 energy card that ejects the three spent cartridges to deal damage to random enemies and deals extra damage for every cartridge used. (Sent back to the deck.) # The second effect is a 0 energy card that reloads all cartridges and returns 1 energy if three were expended previously.

    }
}
