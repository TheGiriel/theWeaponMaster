package theWeaponMaster.cards.generic;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class GenericDodge extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(GenericDodge.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;
    private static final int BLOCK = 6;
    private static final int UPGRADED_BLOCK = 3;
    private static final int MAGIC_NUMBER = 1;
    private static final int UPGRADED_MAGIC_NUMBER = 1;
    private static AbstractPlayer player = AbstractDungeon.player;

    public GenericDodge() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.block = baseBlock = BLOCK;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        for (int i = 0; i < magicNumber; i++) {
            if (player.discardPile.size() != 0 && !upgraded) {
                player.discardPile.getRandomCard(true, CardRarity.COMMON);
            }
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADED_BLOCK);
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            initializeDescription();
        }
    }
}