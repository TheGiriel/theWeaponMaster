package theWeaponMaster.cards.revolver;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.abstractcards.AbstractRevolverCard;
import theWeaponMaster.relics.RevolverRelic;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class RevolverUnload extends AbstractRevolverCard {

    public static final String ID = TheWeaponMaster.makeID(RevolverUnload.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String[] DESCRIPTIONS = cardStrings.EXTENDED_DESCRIPTION;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    public static final CardRarity RARITY = CardRarity.RARE;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final CardType TYPE = CardType.ATTACK;
    public static final int COST = 3;
    public static final int DAMAGE = 5;
    public static final int UPGRADED_DAMAGE = 2;
    public static int SECOND_VALUE = 6;

    public RevolverUnload() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.damage = baseDamage = DAMAGE;
        this.secondValue = baseSecondValue = SECOND_VALUE;
        initializeDescription();

        exhaust = true;
        this.cardsToPreview = new RevolverUnloadShot();
        if (upgraded) {
            this.cardsToPreview.upgraded = true;
        }
    }

    @Override
    public void upgrade() {
        upgradeName();
        upgradeDamage(UPGRADED_DAMAGE);
        initializeDescription();
    }

    @Override
    public void setNormalDescription() {
        cost = COST;
        rawDescription = DESCRIPTIONS[0];
        type = TYPE;
        target = TARGET;
        baseSecondValue = RevolverRelic.shotsLeft;
        initializeDescription();
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        baseSecondValue = RevolverRelic.shotsLeft;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        exhaust = true;
        if (RevolverRelic.shotsLeft <= 0) {
            exhaust = false;
            return;
        }
        baseSecondValue = RevolverRelic.shotsLeft;
        //actionManager.addToBottom(new Discard(p.discardPile, p.drawPile, secondValue));
        for (int i = 0; i < RevolverRelic.shotsLeft; i++) {
            p.drawPile.moveToDiscardPile(p.drawPile.getTopCard());
            AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(new RevolverUnloadShot(), m, 0, true));
        }
    }

    @Override
    public void initializeDescription() {
        baseSecondValue = RevolverRelic.shotsLeft;
        super.initializeDescription();
    }

    @Override
    public void atTurnStart() {
        initializeDescription();
        super.atTurnStart();
    }
}
