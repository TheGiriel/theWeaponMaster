package theWeaponMaster.cards.tempCards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.abstractcards.AbstractRevolverCard;
import theWeaponMaster.cards.revolver.RevolverTwinned;
import theWeaponMaster.relics.RevolverRelic;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;
import static theWeaponMaster.patches.WeaponMasterTags.AMMUNITION;

public class RevolverSingleShot extends AbstractRevolverCard {

    public static final String ID = TheWeaponMaster.makeID(RevolverSingleShot.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] DESCRIPTIONS = cardStrings.EXTENDED_DESCRIPTION;
    public static final CardRarity RARITY = CardRarity.BASIC;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final CardType TYPE = CardType.ATTACK;
    public static final int COST = 0;
    public static final int DAMAGE = RevolverTwinned.DAMAGE;

    public static final int UPGRADED_DAMAGE = RevolverTwinned.UPGRADED_DAMAGE;

    public RevolverSingleShot() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.damage = baseDamage = DAMAGE;
        this.tags.add(AMMUNITION);
        isEthereal = true;
        exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADED_DAMAGE);
            initializeDescription();
        }
    }
    @Override
    public void setNormalDescription() {
        cost = COST;
        costForTurn = COST;
        rawDescription = DESCRIPTIONS[0];
        type = TYPE;
        target = TARGET;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.getRelic(RevolverRelic.ID).counter <= 0) {
            return;
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage + block, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }
}
