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
import theWeaponMaster.powers.TauntPower;
import theWeaponMaster.powers.ViciousPower;

import java.util.HashSet;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;
import static theWeaponMaster.patches.WeaponMasterTags.TAUNT;

public class BullyTaunt extends AbstractBullyCard {

    public static final String ID = TheWeaponMaster.makeID(BullyTaunt.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static String UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int MAGIC_NUMBER = 2;
    private static final int UPGRADED_MAGIC_NUMBER = 1;
    private static final int BULLY_COST = 15;
    private static final int UPGRADED_BULLY_NUMBER = 3;
    private HashSet<AbstractMonster.Intent> intents = new HashSet<>();

    public BullyTaunt() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.bullyNumber = baseBullyNumber = BULLY_COST;

        tags.add(TAUNT);
        intents = EnemyForceAction.getIntents(this);
        ExhaustiveField.ExhaustiveFields.baseExhaustive.set(this, 2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m.intent != AbstractMonster.Intent.ATTACK && m.intent != AbstractMonster.Intent.ATTACK_BUFF && m.intent != AbstractMonster.Intent.ATTACK_DEBUFF && m.intent != AbstractMonster.Intent.ATTACK_DEFEND && m.intent != AbstractMonster.Intent.SLEEP && m.intent != AbstractMonster.Intent.STUN) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new TauntPower(m, p)));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ViciousPower(p, bullyNumber)));
    }


    @Override
    public boolean cardPlayable(AbstractMonster m) {
        try {
            if (intents.contains(m.intent)) {
                return true;
            }
        } catch (NullPointerException e) {
            TheWeaponMaster.logger.info("Some error happened: " + e);
        }
        return false;
    }


    @Override
    protected String getCantPlayMessage() {
        return EnemyForceAction.doesntDefend();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            increaseVicious(UPGRADED_BULLY_NUMBER);
            initializeDescription();
        }
    }
}