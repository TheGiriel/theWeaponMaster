package theWeaponMaster.cards.bully;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.EnemyForceAction;
import theWeaponMaster.cards.abstractcards.AbstractBullyCard;
import theWeaponMaster.powers.IntimidatePower;
import theWeaponMaster.powers.TauntPower;

import java.util.HashSet;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;
import static theWeaponMaster.patches.WeaponMasterTags.BULLY;
import static theWeaponMaster.patches.WeaponMasterTags.TAUNT;

public class BullyTaunt extends AbstractBullyCard {

    public static final String ID = TheWeaponMaster.makeID(BullyTaunt.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static String UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    public static final CardRarity RARITY = CardRarity.UNCOMMON;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    public static final int COST = 1;
    public static final int MAGIC_NUMBER = 2;
    public static final int UPGRADED_MAGIC_NUMBER = 1;
    public static final int BULLY_COST = 3;
    public static final int UPGRADED_BULLY_NUMBER = 3;
    private HashSet<AbstractMonster.Intent> intents;

    public BullyTaunt() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.bullyNumber = baseBullyNumber = BULLY_COST;

        tags.add(TAUNT);
        tags.add(BULLY);
        intents = EnemyForceAction.getIntents(this);
        ExhaustiveField.ExhaustiveFields.exhaustive.set(this, magicNumber);
        ExhaustiveField.ExhaustiveFields.baseExhaustive.set(this, magicNumber);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (intents.contains(m.intent)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new TauntPower(m, p)));
        }
    }

    @Override
    public boolean cardPlayable(AbstractMonster m) {
        try {
            if (intents.contains(m.intent) && !m.hasPower(IntimidatePower.POWER_ID)) {
                return true;
            }
        } catch (NullPointerException e) {

        }
        cantUseMessage = "This enemy already attacking me.";
        return false;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            upgradeBullyNumber(UPGRADED_BULLY_NUMBER);
            initializeDescription();
        }
    }
}