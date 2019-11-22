package theWeaponMaster.cards.bully;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.EnemyForceAction;
import theWeaponMaster.cards.abstractcards.AbstractBullyCard;
import theWeaponMaster.powers.IntimidatePower;
import theWeaponMaster.powers.TauntPower;
import theWeaponMaster.powers.ViciousPower;

import java.util.HashSet;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;
import static theWeaponMaster.patches.WeaponMasterTags.BULLY;
import static theWeaponMaster.patches.WeaponMasterTags.INTIMIDATE;

public class BullyTerrifyingHowl extends AbstractBullyCard {

    public static final String ID = TheWeaponMaster.makeID(BullyTerrifyingHowl.class.getSimpleName());
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = makeCardPath("Skill.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    private static final int COST = 3;
    private static final int BULLY_COST = 6;
    private static final int UPGRADED_BULLY_NUMBER = 4;
    private static final int MAGIC_NUMBER = 2;
    private HashSet<AbstractMonster.Intent> intents = new HashSet<>();

    public BullyTerrifyingHowl() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.bullyNumber = baseBullyNumber = BULLY_COST;

        tags.add(INTIMIDATE);
        tags.add(BULLY);
        intents = EnemyForceAction.getIntents(this);

        purgeOnUse = true;
    }

    @Override
    public void atTurnStart() {
        setBullyGain();
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        setBullyGain();
    }

    private void setBullyGain() {
        this.bullyNumber = baseBullyNumber = BULLY_COST;
        if (upgraded) {
            increaseVicious(UPGRADED_BULLY_NUMBER);
        }
        if (AbstractDungeon.isPlayerInDungeon()) {
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if (intents.contains(monster.intent) && !monster.hasPower(TauntPower.POWER_ID)) {
                    baseBullyNumber += UPGRADED_BULLY_NUMBER;
                }
            }
        }
    }

    @Override
    public boolean cardPlayable(AbstractMonster m) {
        try {
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if (intents.contains(monster.intent) && !monster.hasPower(TauntPower.POWER_ID)) {
                    return true;
                }
            }
        } catch (NullPointerException e) {
            TheWeaponMaster.logger.info("Some error happened: " + e);
        }
        return false;
    }


    @Override
    protected String getCantPlayMessage() {
        return EnemyForceAction.noneAttacking();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            increaseVicious(UPGRADED_BULLY_NUMBER);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        intents = EnemyForceAction.getIntents(this);
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (intents.contains(monster.intent)) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new IntimidatePower(monster, p)));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ViciousPower(p, UPGRADED_BULLY_NUMBER)));
            }
            if (upgraded) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new VulnerablePower(monster, magicNumber, false)));
            }
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ViciousPower(p, bullyNumber)));
        this.bullyNumber = baseBullyNumber = BULLY_COST;
    }
}
