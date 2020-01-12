package theWeaponMaster.cards.revolver;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.powers.MarksmanshipPower;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class RevolverMarksmanship extends AbstractDynamicCard {


    public static final String ID = TheWeaponMaster.makeID(RevolverMarksmanship.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1;
    private static final int MAGIC_NUMBER = 1;
    private static final int UPGRADED_MAGIC_NUMBER = 1;

    public RevolverMarksmanship() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;

    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, magicNumber - 1), magicNumber));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MarksmanshipPower(p, p)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            initializeDescription();
        }
    }
}