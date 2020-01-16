package theWeaponMaster.cards.generic;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class GenericDodge extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(GenericDodge.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    public static final CardRarity RARITY = CardRarity.COMMON;
    public static final CardTarget TARGET = CardTarget.SELF;
    public static final CardType TYPE = CardType.SKILL;
    public static final int COST = 1;
    public static final int BLOCK = 6;
    public static final int UPGRADED_BLOCK = 3;
    public static final int MAGIC_NUMBER = 1;
    public static final int UPGRADED_MAGIC_NUMBER = 1;

    public GenericDodge() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.block = baseBlock = BLOCK;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;

        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        if (p.discardPile.getAttacks().size() != 0) {
            p.discardPile.moveToHand(p.discardPile.getRandomCard(CardType.ATTACK, true), p.discardPile);
        }
        if (p.discardPile.getSkills().size() != 0 && upgraded) {
            p.discardPile.moveToHand(p.discardPile.getRandomCard(CardType.SKILL, true), p.discardPile);
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADED_BLOCK);
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}