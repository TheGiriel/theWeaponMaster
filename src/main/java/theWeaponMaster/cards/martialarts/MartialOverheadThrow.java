package theWeaponMaster.cards.martialarts;

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
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.patches.WeaponMasterTags;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class MartialOverheadThrow extends AbstractDynamicCard {


    public static final String ID = TheWeaponMaster.makeID(MartialOverheadThrow.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(MartialOverheadThrow.class.getSimpleName());
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int DAMAGE = 4;
    private static final int UPGRADED_DAMAGE = 2;
    private static final int BLOCK = 6;
    private static final int UPGRADED_BLOCK = 4;
    private static final int SECOND_VALUE = 4;
    private static final int UPGRADED_SECOND_VALUE = 2;

    private int played = 0;

    public MartialOverheadThrow() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = baseDamage = DAMAGE;
        this.block = baseBlock = BLOCK;
        this.secondValue = baseSecondValue = SECOND_VALUE;

        tags.add(WeaponMasterTags.MARTIAL);
    }

    @Override
    public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster mo, float tmp) {
        return tmp + tmp * AbstractDungeon.actionManager.cardsPlayedThisTurn.stream().filter(e -> e.hasTag(WeaponMasterTags.MARTIAL) && !e.equals(this)).count();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            upgradeDamage(UPGRADED_DAMAGE);
            upgradeSecondValue(UPGRADED_SECOND_VALUE);
            upgradeBlock(UPGRADED_BLOCK);
            initializeDescription();
        }
    }
}
