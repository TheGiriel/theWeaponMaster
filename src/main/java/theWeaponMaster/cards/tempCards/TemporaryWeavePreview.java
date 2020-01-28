package theWeaponMaster.cards.tempCards;

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
import theWeaponMaster.cards.martialarts.MartialBob;
import theWeaponMaster.patches.WeaponMasterTags;

import java.util.HashSet;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class TemporaryWeavePreview extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(TemporaryWeavePreview.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(TemporaryWeavePreview.class.getSimpleName());
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int BLOCK = MartialBob.BLOCK - 2;
    private static final int UPGRADED_BLOCK = MartialBob.UPGRADED_BLOCK;
    private static final int MAGIC_NUMBER = 2;
    private static final int UPGRADED_MAGIC_NUMBER = 1;

    private static HashSet<AbstractMonster.Intent> intents;

    public TemporaryWeavePreview() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.block = baseBlock = BLOCK;

        tags.add(WeaponMasterTags.INTIMIDATE);
        tags.add(WeaponMasterTags.MARTIAL);

        intents = EnemyForceAction.getIntents(this);

        initializeDescription();
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        boolean draw = true;
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (!intents.contains(monster.intent)) {
                draw = false;
                break;
            }
        }
        if (draw) {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(secondValue));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            upgradeBlock(UPGRADED_BLOCK);
            initializeDescription();
        }
    }
}
