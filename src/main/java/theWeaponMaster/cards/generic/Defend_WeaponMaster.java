package theWeaponMaster.cards.generic;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class Defend_WeaponMaster extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(Defend_WeaponMaster.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    public static final CardRarity RARITY = CardRarity.BASIC;
    public static final CardTarget TARGET = CardTarget.SELF;
    public static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    public static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADED_BLOCK = 3;

    public Defend_WeaponMaster() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.block = baseBlock = BLOCK;

        this.tags.add(CardTags.STARTER_DEFEND);
        initializeDescription();
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
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
