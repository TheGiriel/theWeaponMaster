package theWeaponMaster.cards.tempCards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.EnemyForceAction;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.cards.martialarts.MartialBob;
import theWeaponMaster.patches.WeaponMasterTags;

import java.util.HashSet;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class TemporaryWeave extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(TemporaryWeave.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(TemporaryWeave.class.getSimpleName());
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final CardRarity RARITY = CardRarity.SPECIAL;
    public static final CardTarget TARGET = CardTarget.SELF;
    public static final CardType TYPE = CardType.SKILL;
    public static final int COST = 1;
    public static final int UPGRADED_COST = 1;

    public static final int BLOCK = MartialBob.BLOCK - 2;
    public static final int UPGRADED_BLOCK = MartialBob.UPGRADED_BLOCK;
    public static final int MAGIC_NUMBER = 2;
    public static final int UPGRADED_MAGIC_NUMBER = 1;
    public static final int SECOND_VALUE = 1;
    public static final int UPGRADED_SECOND_VALUE = 1;

    public static HashSet<AbstractMonster.Intent> intents;

    public TemporaryWeave() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.block = baseBlock = BLOCK;
        this.secondValue = baseSecondValue = SECOND_VALUE;

        tags.add(WeaponMasterTags.INTIMIDATE);
        tags.add(WeaponMasterTags.MARTIAL);

        intents = EnemyForceAction.getIntents(this);

        purgeOnUse = true;
        if (AbstractDungeon.isPlayerInDungeon()) {
            this.cardsToPreview = new TemporaryBobPreview();
        }

        initializeDescription();
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        int energyGain = 0;
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (intents.contains(monster.intent)) {
                energyGain++;
            }
        }
        if (energyGain > 0) {
            Math.min(energyGain, secondValue);
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(energyGain));
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new MartialBob(), 1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            cardsToPreview.upgrade();
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            upgradeBlock(UPGRADED_BLOCK);
            initializeDescription();
        }
    }
}
