package theWeaponMaster.cards.bully;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.EnemyForceAction;
import theWeaponMaster.cards.abstractcards.AbstractBullyCard;
import theWeaponMaster.patches.WeaponMasterTags;

import java.util.HashSet;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class BullyMockery extends AbstractBullyCard {

    public static final String ID = TheWeaponMaster.makeID(BullyMockery.class.getSimpleName());
    public static final String IMG = makeCardPath("bullyslap.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 1;
    private static final int MAGIC_NUMBER = 2;
    private static final int UPGRADED_MAGIC_NUMBER = 1;
    private static final int BULLY_COST = 2;
    private static final int UPGRADED_BULLY_COST = 3;

    private HashSet<AbstractMonster.Intent> intents;

    public BullyMockery() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.bullyNumber = baseBullyNumber = BULLY_COST;

        tags.add(WeaponMasterTags.INTIMIDATE);
        intents = EnemyForceAction.getIntents(this);

        initializeDescription();
    }

    @Override
    public void upgrade() {
        upgradeName();
        upgradeBullyNumber(UPGRADED_BULLY_COST);
        upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
        initializeDescription();

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!upgraded) {
            if (intents.contains(m.intent)) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, magicNumber, false)));
            } else {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false)));
            }
        } else {
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if (intents.contains(monster.intent)) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, magicNumber, false)));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false)));
                }
            }
        }

    }
}
