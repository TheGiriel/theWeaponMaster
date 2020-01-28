package theWeaponMaster.cards.revolver;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.abstractcards.AbstractRevolverCard;
import theWeaponMaster.cards.tempCards.RevolverSingleShot;
import theWeaponMaster.relics.RevolverRelic;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;
import static theWeaponMaster.patches.WeaponMasterTags.AMMUNITION;

public class RevolverTwinned extends AbstractRevolverCard {

    public static final String ID = TheWeaponMaster.makeID(RevolverTwinned.class.getSimpleName());
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int DAMAGE = 4;

    public static final String IMG = makeCardPath("Attack.png");

    public static final CardRarity RARITY = CardRarity.COMMON;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    public static final int COST = 1;
    public static final String[] DESCRIPTIONS = cardStrings.EXTENDED_DESCRIPTION;
    public static final int UPGRADED_DAMAGE = 2;

    public RevolverTwinned() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.damage = baseDamage = DAMAGE;
        tags.add(AMMUNITION);

        this.cardsToPreview = new RevolverSingleShot();

        initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADED_DAMAGE);
            cardsToPreview.upgrade();
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
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        if (damage >= m.currentHealth + m.currentBlock) {
            if (upgraded) {
                AbstractCard c = new RevolverSingleShot();
                c.upgrade();
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c));
            } else {
                if (upgraded) {
                    AbstractCard tempCard = new RevolverSingleShot();
                    tempCard.upgrade();
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(tempCard));
                } else
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new RevolverSingleShot()));
                return;
            }
        } else
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }
}
