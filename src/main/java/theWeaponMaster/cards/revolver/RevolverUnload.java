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
import theWeaponMaster.patches.WeaponMasterTags;
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

        exhaust = true;
        AbstractRevolverCard unloadShot = new RevolverUnloadShot();
        if (upgraded) {
            this.cardsToPreview.upgrade();
        } else {
            this.cardsToPreview = unloadShot;
        }
        tags.remove(WeaponMasterTags.AMMUNITION);
        initializeDescription();
    }

    @Override
    public void upgrade() {
        upgradeName();
        upgradeDamage(UPGRADED_DAMAGE);
        this.cardsToPreview.upgrade();
        rawDescription = DESCRIPTIONS[1];
        initializeDescription();
    }

    @Override
    public boolean cardPlayable(AbstractMonster m) {
        if (AbstractDungeon.player.drawPile.size() > 0) {
            baseSecondValue = Math.min(AbstractDungeon.player.drawPile.size(), RevolverRelic.shotsLeft);
            initializeDescription();
            return true;
        }
        baseSecondValue = Math.min(AbstractDungeon.player.drawPile.size(), RevolverRelic.shotsLeft);
        initializeDescription();
        cantUseMessage = "I don't have any cards in my draw pile!";
        return false;
    }

    @Override
    public void setNormalDescription() {
        cost = COST;
        costForTurn = COST;
        if (upgraded) {
            rawDescription = DESCRIPTIONS[1];
        } else {
            rawDescription = DESCRIPTIONS[0];
        }
        type = TYPE;
        target = TARGET;
        baseSecondValue = Math.min(AbstractDungeon.player.drawPile.size(), RevolverRelic.shotsLeft);
        initializeDescription();
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        baseSecondValue = Math.min(AbstractDungeon.player.drawPile.size(), RevolverRelic.shotsLeft);
        initializeDescription();
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        exhaust = true;
        if (RevolverRelic.shotsLeft <= 0) {
            exhaust = false;
            return;
        }
        baseSecondValue = Math.min(AbstractDungeon.player.drawPile.size(), RevolverRelic.shotsLeft);
        for (int i = 0; i < baseSecondValue; i++) {
            if (p.drawPile.group.isEmpty()) {
                return;
            }
            AbstractRevolverCard unloadShot = new RevolverUnloadShot();
            unloadShot.upgrade();
            p.drawPile.moveToDiscardPile(p.drawPile.getTopCard());
            AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(unloadShot, m, 0, true));
        }
    }

    @Override
    public void initializeDescription() {
        if (!AbstractDungeon.isPlayerInDungeon()) {
            baseSecondValue = SECOND_VALUE;
        } else {
            baseSecondValue = Math.min(AbstractDungeon.player.drawPile.size(), RevolverRelic.shotsLeft);
        }
        super.initializeDescription();
    }

    @Override
    public void atTurnStart() {
        initializeDescription();
        super.atTurnStart();
    }
}
