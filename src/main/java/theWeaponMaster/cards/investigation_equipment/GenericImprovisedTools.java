package theWeaponMaster.cards.investigation_equipment;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.powers.ImprovisedToolsPower;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class GenericImprovisedTools extends AbstractDynamicCard {


    public static final String ID = TheWeaponMaster.makeID(GenericImprovisedTools.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(GenericImprovisedTools.class.getSimpleName());
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int DAMAGE = 0;
    private static final int UPGRADED_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPGRADED_BLOCK = 0;
    private static final int MAGIC_NUMBER = 2;
    private static final int UPGRADED_MAGIC_NUMBER = 1;

    public GenericImprovisedTools() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ImprovisedToolsPower(p, upgraded)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
        }
    }
}
