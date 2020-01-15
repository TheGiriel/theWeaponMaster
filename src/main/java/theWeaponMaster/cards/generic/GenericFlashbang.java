package theWeaponMaster.cards.generic;

import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.powers.StaggerPower;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class GenericFlashbang extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(GenericFlashbang.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final CardRarity RARITY = CardRarity.UNCOMMON;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final CardType TYPE = CardType.SKILL;
    public static final int COST = 1;
    public static final int MAGIC_NUMBER = 1;
    public static final int UPGRADED_MAGIC_NUMBER = 1;

    public GenericFlashbang() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;

        ExhaustiveField.ExhaustiveFields.exhaustive.set(this, this.magicNumber);
        ExhaustiveField.ExhaustiveFields.baseExhaustive.set(this, this.magicNumber);

        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int targetIndex = AbstractDungeon.getMonsters().monsters.indexOf(m);
        if (targetIndex > 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.getMonsters().monsters.get(targetIndex - 1), p, new StaggerPower(m, this, 0)));
        }
        AbstractDungeon.actionManager.addToTop(new StunMonsterAction(m, p));
        try {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.getMonsters().monsters.get(targetIndex + 1), p, new StaggerPower(m, this, 0)));
        } catch (IndexOutOfBoundsException e) {
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            ExhaustiveField.ExhaustiveFields.isExhaustiveUpgraded.set(this, true);
            ExhaustiveField.ExhaustiveFields.exhaustive.set(this, this.magicNumber);
            ExhaustiveField.ExhaustiveFields.baseExhaustive.set(this, this.magicNumber);
            initializeDescription();
        }
    }
}