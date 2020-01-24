package theWeaponMaster.cards.martialarts;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.patches.WeaponMasterTags;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class MartialKneeBomb extends AbstractDynamicCard {


    public static final String ID = TheWeaponMaster.makeID(MartialKneeBomb.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(MartialKneeBomb.class.getSimpleName());
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int DAMAGE = 7;
    private static final int UPGRADED_DAMAGE = 4;

    public MartialKneeBomb() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = baseDamage = DAMAGE;

        tags.add(WeaponMasterTags.MARTIAL);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int blockDamage = 0;
        if (m.currentBlock >= 0) {
            if (m.currentBlock / 2 < damage) {
                blockDamage = m.currentBlock;
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, (int) (blockDamage + Math.ceil(damage - blockDamage / 2)), damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            } else {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage * 2, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            }
        } else {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            upgradeDamage(UPGRADED_DAMAGE);
            initializeDescription();
        }
    }
}
