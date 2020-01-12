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
import theWeaponMaster.actions.ReloadAction;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.powers.MarksmanshipPower;
import theWeaponMaster.relics.HeavyDrum;
import theWeaponMaster.relics.RevolverRelic;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;
import static theWeaponMaster.patches.WeaponMasterTags.AMMUNITION;

public class RevolverHollowPoint extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(RevolverHollowPoint.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 1;
    private static final int DAMAGE = 6;
    private static final int UPGRADED_DAMAGE = 2;
    private static final int MAGIC_NUMBER = 25;
    private static final int UPGRADED_MAGIC_NUMBER = 25;

    public RevolverHollowPoint() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.damage = baseDamage = DAMAGE;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
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
        } else
            return super.calculateModifiedCardDamage(player, mo, tmp);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasRelic(RevolverRelic.ID) && p.getRelic(RevolverRelic.ID).counter <= 0) {
            new ReloadAction();
            return;
        }
        double bonusDamge = 1;
        if (m.currentBlock == 0) {
            bonusDamge += ((double) magicNumber / 100);
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, (int) Math.ceil(damage * bonusDamge), DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

    }

}
