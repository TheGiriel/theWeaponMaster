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

public class Flashbang extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(Flashbang.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 0;
    private static final int MAGIC_NUMBER = 1;
    private static final int UPGRADED_MAGIC_NUMBER = 1;

    public Flashbang() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;

        ExhaustiveField.ExhaustiveFields.exhaustive.set(this, this.magicNumber);
        ExhaustiveField.ExhaustiveFields.baseExhaustive.set(this, this.magicNumber);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new StunMonsterAction(m, p));
        AbstractDungeon.getMonsters().monsters.forEach(e -> {
            if (!e.equals(m))
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(e, p, new StaggerPower(e, p, 0)));
        });
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            //ExhaustiveField.ExhaustiveFields.isExhaustiveUpgraded.set(this, true);
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            initializeDescription();
        }
    }
}