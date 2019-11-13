package theWeaponMaster.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.characters.TheWeaponMaster;

import static theWeaponMaster.DefaultMod.makeCardPath;

public class LeviathanCrush extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(LeviathanCrush.class.getSimpleName());
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

    public LeviathanCrush() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = baseDamage = DAMAGE;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADED_DAMAGE);
            rawDescription = UPGRADED_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int armorCrush = 0;
        //TODO: Review the code and write something better.
        if (m.currentBlock > 0) {

        }
        if (upgraded) {

        }
    }
}
