package theGodHunters.cards.weaponmaster.legendary_weapons.leviathan;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theGodHunters.DefaultMod;
import theGodHunters.cards.AbstractDynamicCard;
import theGodHunters.characters.TheWeaponMaster;

import static theGodHunters.DefaultMod.makeCardPath;

public class LW_Leviathan extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(LW_Leviathan.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static String UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    public static final String IMG = makeCardPath("Attack.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheWeaponMaster.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 8;
    private static final int UPGRADED_DAMAGE = 2;
    private int armorCrush = 0;

    public LW_Leviathan() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
    }

    //TODO: Basic: Deal extra damage to enemies who have block. Drawback: Take 50% damage when you attack enemies without block.

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        update();

    }

    @Override
    public void upgrade() {
    }
}
