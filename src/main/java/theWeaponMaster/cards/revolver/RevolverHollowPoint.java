package theWeaponMaster.cards.revolver;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.abstractcards.AbstractRevolverCard;
import theWeaponMaster.powers.MarksmanshipPower;
import theWeaponMaster.relics.HeavyDrum;
import theWeaponMaster.relics.RevolverRelic;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;
import static theWeaponMaster.patches.WeaponMasterTags.AMMUNITION;

public class RevolverHollowPoint extends AbstractRevolverCard {

    public static final String ID = TheWeaponMaster.makeID(RevolverHollowPoint.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(RevolverHollowPoint.class.getSimpleName());
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String[] DESCRIPTIONS = cardStrings.EXTENDED_DESCRIPTION;
    public static final CardRarity RARITY = CardRarity.UNCOMMON;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final CardType TYPE = CardType.ATTACK;
    public static final int COST = 1;
    public static final int DAMAGE = 7;
    public static final int UPGRADED_DAMAGE = 2;
    public static final int MAGIC_NUMBER = 33;
    public static final int UPGRADED_MAGIC_NUMBER = 17;
    private float hollowPointBonus;

    public RevolverHollowPoint() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.damage = baseDamage = DAMAGE;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;

        hollowPointBonus = ((float) magicNumber / 100);

        tags.add(AMMUNITION);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADED_DAMAGE);
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
        }
    }

    @Override
    public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster mo, float tmp) {
        if (player.hasRelic(HeavyDrum.ID)) {
            tmp++;
        }
        if (player.hasPower(DexterityPower.POWER_ID) && player.hasPower(MarksmanshipPower.POWER_ID)) {
            return super.calculateModifiedCardDamage(player, mo, tmp + player.getPower(DexterityPower.POWER_ID).amount);
        }
        try {
            if (mo.currentBlock == 0) {
                return super.calculateModifiedCardDamage(player, mo, (float) Math.ceil((double) tmp * (1 + hollowPointBonus)));
            }
        } catch (NullPointerException e) {

        }
        return super.calculateModifiedCardDamage(player, mo, tmp);
    }

    @Override
    public void setNormalDescription() {
        this.cost = COST;
        rawDescription = DESCRIPTIONS[0];
        type = TYPE;
        target = TARGET;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.getRelic(RevolverRelic.ID).counter <= 0) {
            return;
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

}
