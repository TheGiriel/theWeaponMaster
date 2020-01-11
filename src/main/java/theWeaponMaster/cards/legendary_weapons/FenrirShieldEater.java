package theWeaponMaster.cards.legendary_weapons;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.FenrirEvolveAction;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.relics.SplinteringSteelRelic;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class FenrirShieldEater extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(FenrirShieldEater.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG = makeCardPath("fenrirshieldeater.png");

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 6;
    private static final int UPGRADED_DAMAGE = 3;
    private static final int MAGIC_NUMBER = 2;
    private static final int UPGRADED_MAGIC_NUMBER = 1;
    private static final int EVOLUTION = 1;

    public FenrirShieldEater() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.damage = this.baseDamage = DAMAGE;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;

    }

    @Override
    public boolean canUpgrade() {
        return AbstractDungeon.player.hasRelic(SplinteringSteelRelic.ID);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int shieldEater = 0;
        boolean canEvolve = false;
        if (m.currentBlock != 0) {
            if (damage >= m.currentBlock) {
                shieldEater = m.currentBlock;
                canEvolve = true;
            } else {
                shieldEater = damage;
            }
        }

        AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        if (shieldEater >= 1) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, shieldEater));
        }
        if (canEvolve) {
            new FenrirEvolveAction();
        }
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADED_DAMAGE);
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            initializeDescription();
        }
    }
}