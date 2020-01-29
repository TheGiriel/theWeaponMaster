package theWeaponMaster.cards.tempCards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.EnemyForceAction;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.patches.WeaponMasterTags;

import java.util.HashSet;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class TemporaryBobPreview extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(TemporaryBobPreview.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final CardRarity RARITY = CardRarity.UNCOMMON;
    public static final CardTarget TARGET = CardTarget.SELF;
    public static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    public static final int COST = 1;

    public static final int BLOCK = 6;
    public static final int UPGRADED_BLOCK = 3;

    public static final int MAGIC_NUMBER = 3;
    public static final int UPGRADED_MAGIC_NUMBER = 1;

    public static HashSet<AbstractMonster.Intent> intents;

    public TemporaryBobPreview() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.block = baseBlock = BLOCK;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;

        tags.add(WeaponMasterTags.INTIMIDATE);
        tags.add(WeaponMasterTags.MARTIAL);

        intents = EnemyForceAction.getIntents(this);

        purgeOnUse = true;

        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (intents.contains(monster.intent)) {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, magicNumber));
            }
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new TemporaryWeave(), 1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADED_BLOCK);
            initializeDescription();
        }
    }
}
