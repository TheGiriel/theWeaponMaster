package theWeaponMaster.cards.generic;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.SleightOfHandAction;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class GenericSleightOfHand extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(GenericSleightOfHand.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    public static final CardRarity RARITY = CardRarity.COMMON;
    public static final CardTarget TARGET = CardTarget.SELF;
    public static final CardType TYPE = CardType.SKILL;
    public static final int COST = 1;
    private static final int BLOCK = 5;
    public static final int MAGIC_NUMBER = 2;

    public GenericSleightOfHand() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.block = baseBlock = BLOCK;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DiscardAction(p, p, 1, false));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 2));
        AbstractDungeon.actionManager.addToBottom(new SleightOfHandAction());
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
        }
    }

}