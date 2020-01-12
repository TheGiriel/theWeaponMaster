package theWeaponMaster.cards.generic;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.EnemyForceAction;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.patches.WeaponMasterTags;
import theWeaponMaster.util.FlipCard;

import java.util.HashSet;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class GenericBobAndWeave extends AbstractDynamicCard implements FlipCard {


    public static final String ID = TheWeaponMaster.makeID(GenericBobAndWeave.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] DESCRIPTIONS = cardStrings.EXTENDED_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int BLOCK = 6;
    private static final int UPGRADED_BLOCK = 2;

    private static final int MAGIC_NUMBER = 3;
    private static final int UPGRADED_MAGIC_NUMBER = 1;

    private static boolean flipped = false;
    private boolean initializeCard = false;
    private static HashSet<AbstractMonster.Intent> intents;

    public GenericBobAndWeave() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.block = baseBlock = BLOCK;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;

        tags.add(WeaponMasterTags.INTIMIDATE);

        intents = EnemyForceAction.getIntents(this);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (flipped) {
            flipUse(p, m);
        } else {
            standardUse(p, m);
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            upgradeBlock(UPGRADED_BLOCK);
            initializeDescription();
        }
    }

    @Override
    public void flipCard() {
        if (!flipped) {
            this.name = DESCRIPTIONS[2];
            rawDescription = DESCRIPTIONS[3];
            this.cost = 0;
            flipped = true;
        } else {
            this.name = DESCRIPTIONS[0];
            rawDescription = DESCRIPTIONS[1];
            this.cost = COST;
            flipped = false;
        }
        initializeDescription();

    }

    @Override
    public void standardUse(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (intents.contains(monster.intent)) {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, magicNumber));
            }
        }
        flipCard();
    }

    @Override
    public void flipUse(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        boolean draw = true;
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (!intents.contains(monster.intent)) {
                draw = false;
                break;
            }
        }
        if (draw) {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
        }
        flipCard();
    }


    @Override
    public void atTurnStart() {
        if (!initializeCard) {
            flipped = true;
            initializeCard = true;
            flipCard();
        }
    }
}
