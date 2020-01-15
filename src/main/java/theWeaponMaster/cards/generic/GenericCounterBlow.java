package theWeaponMaster.cards.generic;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.powers.CounterBlowPower;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class GenericCounterBlow extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(GenericCounterBlow.class.getSimpleName());
    public static final String IMG = makeCardPath("Power.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(GenericCounterBlow.class.getSimpleName());
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final CardRarity RARITY = CardRarity.RARE;
    public static final CardTarget TARGET = CardTarget.SELF;
    public static final CardType TYPE = CardType.POWER;
    public static final int COST = 3;
    public static final int DAMAGE = 4;
    public static final int UPGRADED_DAMAGE = 2;

    public GenericCounterBlow() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = baseDamage = DAMAGE;

        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new CounterBlowPower(p, damage)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADED_DAMAGE);
            initializeDescription();
        }
    }
}
