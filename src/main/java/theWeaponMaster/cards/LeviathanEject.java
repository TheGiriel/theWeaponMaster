package theWeaponMaster.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.relics.ArsenalRelic;
import theWeaponMaster.util.FlipCard;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class LeviathanEject extends AbstractDynamicCard implements FlipCard {

    public static final String ID = TheWeaponMaster.makeID(LeviathanEject.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    public static final String IMG = makeCardPath("Attack.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int UPGRADED_DAMAGE = 3;
    private static final int BLOCK = 8;
    private static final int UPGRADED_BLOCK = 8;

    public static boolean ejectFlip = false;

    public LeviathanEject() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = baseDamage = DAMAGE;
        this.block = baseBlock = BLOCK;
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
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (ejectFlip) {
            flipUse(p, m);
        } else {
            standardUse(p, m);
        }
    }

    @Override
    public void flipCard() {
        if (ejectFlip) {
            this.name = DESCRIPTION[2];
            rawDescription = DESCRIPTION[3];
            this.cost = 0;
        } else {
            this.name = cardStrings.NAME;
            rawDescription = DESCRIPTION[1];
            this.cost = COST;
        }
        initializeDescription();
    }

    @Override
    public void standardUse(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < ArsenalRelic.leviathanCharges; i++) {
            AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(new DamageInfo(m, this.damage), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
        ejectFlip = true;
        flipCard();
        ArsenalRelic.leviathanCharges = 0;
    }

    @Override
    public void flipUse(AbstractPlayer p, AbstractMonster m) {
        ArsenalRelic.leviathanCharges = 3;
        AbstractDungeon.player.masterDeck.removeCard(this.cardID);
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        ejectFlip = false;
        flipCard();
    }
}
