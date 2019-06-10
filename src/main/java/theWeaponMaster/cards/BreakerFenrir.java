package theWeaponMaster.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.characters.TheDefault;

import java.util.Iterator;

import static theWeaponMaster.DefaultMod.makeCardPath;

public class BreakerFenrir extends AbstractDynamicCard{


    public static final String ID = DefaultMod.makeID(BreakerFenrir.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static String UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    public static final String IMG = makeCardPath("Attack.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 8;
    //private static final int UPGRADED_DAMAGE = 2;

    public BreakerFenrir(){
        this(0);
    }

    public BreakerFenrir(int upgrades) {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.damage = this.baseDamage = DAMAGE;
        this.timesUpgraded = upgrades;
    }
    //TODO: Basic: Create a weapon that increases in power permanently whenever you destroy enemy block. Drawback: Lose 3 HP whenever you use the card. Upgrade: Increase card power whenever you destroy block and deal extra damage to enemy hp when you destroy block.

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        if (damage > m.currentBlock && m.currentBlock > 0){
            if (upgraded) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, (int) (damage*1.25), damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            } else {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            }
            this.upgrade();
        } else {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(p, new DamageInfo(p, 3, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.POISON));
    }

    @Override
    public void upgrade() {
        this.upgradeDamage(2+timesUpgraded/2);
        this.timesUpgraded++;
        this.upgraded = true;
        this.name = NAME + "+" + this.timesUpgraded;
        this.initializeTitle();
    }

    @Override
    public boolean canUpgrade() {
        return true;
    }
}
