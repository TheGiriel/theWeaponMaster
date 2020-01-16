package theWeaponMaster.cards.bully;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.abstractcards.AbstractBullyCard;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;
import static theWeaponMaster.patches.WeaponMasterTags.BULLY;

public class BullyIntimidatingPresence extends AbstractBullyCard {

    public static final String ID = TheWeaponMaster.makeID(BullyIntimidatingPresence.class.getSimpleName());
    public static final String IMG = makeCardPath("Power.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final CardRarity RARITY = CardRarity.UNCOMMON;
    public static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    public static final CardType TYPE = CardType.SKILL;

    public static final int COST = 2;
    public static final int DAMAGE = 0;
    public static final int UPGRADED_DAMAGE = 0;
    public static final int MAGIC_NUMBER = 3;
    public static final int UPGRADED_MAGIC_NUMBER = 1;
    public static final int BULLY_NUMBER = 8;

    public BullyIntimidatingPresence() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.damage = baseDamage = DAMAGE;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.bullyNumber = baseBullyNumber = BULLY_NUMBER;

        tags.add(BULLY);
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.getMonsters().monsters.forEach(e -> AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(e, p, new StrengthPower(e, -magicNumber))));
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADED_DAMAGE);
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            initializeDescription();
        }
    }
}
