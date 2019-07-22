package theWeaponMaster.cards.bully;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.cards.AbstractDynamicCard;
import theWeaponMaster.characters.TheWeaponMaster;

import static theWeaponMaster.DefaultMod.makeCardPath;

public class BullyWimp extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(BullyWimp.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheWeaponMaster.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int MAGIC_NUMBER = 2;
    private static final int UPGRADED_MAGIC_NUMBER = 1;

    public BullyWimp() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, CardTarget.ALL_ENEMY);

        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            for (AbstractMonster monster : (AbstractDungeon.getMonsters()).monsters) {
                if (!monster.isDead && !monster.isDying) {
                     AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new WeakPower(monster, magicNumber, false), magicNumber));
                }
            }
        }
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
