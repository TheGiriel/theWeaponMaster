package theWeaponMaster.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.characters.TheDefault;

import static theWeaponMaster.DefaultMod.makeCardPath;

public class BreakerCleaver extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(BreakerCleaver.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG = makeCardPath("Attack.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int UPGRADED_DAMAGE = 3;
    private int dmgDEALT = 0;


    public BreakerCleaver() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
    }

    //Basic: Heal for half the unblocked damage dealt
    //TODO: Drawback: Gain Berserk - take and deal 1 extra damage per stack. Upgrade: Additional attacks the higher your Berserk stack is (one attack per 3 stacks)
    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADED_DAMAGE);
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.damage > m.currentBlock) {
            dmgDEALT = (int) Math.ceil((baseDamage-m.currentBlock)/2);
        }

        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, baseDamage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));

        if (dmgDEALT>0) {
            AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, dmgDEALT));
        }
    }
}
