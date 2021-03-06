package theWeaponMaster.cards.bully;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.GainGoldTextEffect;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.abstractcards.AbstractBullyCard;

import java.util.Random;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;
import static theWeaponMaster.patches.WeaponMasterTags.BULLY;

public class BullyShakedown extends AbstractBullyCard {

    public static final String ID = TheWeaponMaster.makeID(BullyShakedown.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final CardRarity RARITY = CardRarity.UNCOMMON;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    public static final int COST = 1;
    public static final int DAMAGE = 5;
    public static final int UPGRADED_DAMAGE = 2;
    public static final int MAGIC_NUMBER = 1;
    public static final int UPGRADED_MAGIC_NUMBER = 1;
    public static final int BULLY_COST = 2;
    public static final int UPGRADED_BULLY_COST = 2;

    public BullyShakedown() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.damage = baseDamage = DAMAGE;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.bullyNumber = baseBullyNumber = BULLY_COST;
        tags.add(BULLY);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int stolenMoney = new Random().nextInt(3) + 3 * magicNumber;
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), stolenMoney));
        AbstractDungeon.player.gainGold(stolenMoney);
        AbstractDungeon.effectList.add(new RainingGoldEffect(stolenMoney));
        AbstractDungeon.effectList.add(new GainGoldTextEffect(stolenMoney));
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            upgradeDamage(UPGRADED_DAMAGE);
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            upgradeBullyNumber(UPGRADED_BULLY_COST);
            initializeDescription();
        }
    }
}
