package theWeaponMaster.cards.bully;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.EnemyForceAction;
import theWeaponMaster.cards.abstractcards.AbstractBullyCard;
import theWeaponMaster.powers.IntimidatePower;
import theWeaponMaster.powers.TauntPower;

import java.util.HashSet;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;
import static theWeaponMaster.patches.WeaponMasterTags.BULLY;
import static theWeaponMaster.patches.WeaponMasterTags.TAUNT;

public class BullyMeanToEveryone extends AbstractBullyCard {

    public static final String ID = TheWeaponMaster.makeID(BullyMeanToEveryone.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final CardRarity RARITY = CardRarity.UNCOMMON;
    public static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    public static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    public static final int COST = 2;
    public static final int MAGIC_NUMBER = 1;
    public static final int UPGRADED_MAGIC_NUMBER = 1;
    public static final int BULLY_COST = 5;
    public static final int UPGRADED_BULLY_COST = 2;
    private HashSet<AbstractMonster.Intent> intents;

    public BullyMeanToEveryone() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.bullyNumber = baseBullyNumber = BULLY_COST;

        isInnate = true;
        exhaust = true;

        tags.add(TAUNT);
        tags.add(BULLY);
        intents = EnemyForceAction.getIntents(this);
        purgeOnUse = true;
    }

    @Override
    public boolean cardPlayable(AbstractMonster m) {
        boolean canUse = AbstractDungeon.getMonsters().monsters.stream().anyMatch(e -> intents.contains(e.intent) && !e.hasPower(TauntPower.POWER_ID));
        if (canUse) {
            return true;
        } else
            cantUseMessage = "I already pissed everyone off!";
        return false;
    }

    @Override
    protected String getCantPlayMessage() {
        return EnemyForceAction.noneDefending();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int strengthBuff = 0;
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (intents.contains(monster.intent) && !monster.hasPower(IntimidatePower.POWER_ID)) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new TauntPower(monster, p)));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new StrengthPower(monster, magicNumber)));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber)));
                strengthBuff++;
            }
        }
        if (strengthBuff > 1) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LoseStrengthPower(p, strengthBuff / 2)));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            upgradeBullyNumber(UPGRADED_BULLY_COST);
            retain = true;
            initializeDescription();
        }
    }
}