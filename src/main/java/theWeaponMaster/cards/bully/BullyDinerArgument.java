package theWeaponMaster.cards.bully;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.EnemyForceAction;
import theWeaponMaster.cards.abstractcards.AbstractBullyCard;
import theWeaponMaster.powers.IntimidatePower;
import theWeaponMaster.powers.StaggerPower;
import theWeaponMaster.powers.TauntPower;

import java.util.HashSet;
import java.util.Random;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;
import static theWeaponMaster.patches.WeaponMasterTags.*;

public class BullyDinerArgument extends AbstractBullyCard {

    public static final String ID = TheWeaponMaster.makeID(BullyDinerArgument.class.getSimpleName());
    public static final String IMG = makeCardPath("bullydinerargument.png");

    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final CardRarity RARITY = CardRarity.RARE;
    public static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    public static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    public static final int COST = 3;
    public static final int MAGIC_NUMBER = 2;
    public static final int UPGRADED_MAGIC_NUMBER = 1;
    public static final int BULLY_COST = 10;
    public static final int UPGRADED_BULLY_COST = 3;
    private HashSet<AbstractMonster.Intent> attacking;
    private HashSet<AbstractMonster.Intent> defending;


    public BullyDinerArgument() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.bullyNumber = baseBullyNumber = BULLY_COST;

        exhaust = true;
        tags.add(BULLY);
        tags.add(INTIMIDATE);
        attacking = EnemyForceAction.getIntents(this);
        tags.remove(INTIMIDATE);
        tags.add(TAUNT);
        defending = EnemyForceAction.getIntents(this);
        tags.remove(TAUNT);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            upgradeBullyNumber(UPGRADED_BULLY_COST);
            initializeDescription();
        }
    }


    private void randomArgument(AbstractPlayer p, AbstractMonster m) {
        switch (new Random().nextInt(5)) {
            case 0:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, 10), AbstractGameAction.AttackEffect.SMASH));
                break;
            case 1:
                if (defending.contains(m.intent)) {
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(m, p, 5));
                    break;
                }
                if (m.hasPower(TauntPower.POWER_ID)) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new GainStrengthPower(m, 1)));
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new LoseStrengthPower(m, 1)));
                    break;
                }
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new IntimidatePower(m, p)));
                break;
            case 2:
                if (attacking.contains(m.intent)) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new GainStrengthPower(m, 1)));
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new LoseStrengthPower(m, 1)));
                    break;
                }
                if (m.hasPower(IntimidatePower.POWER_ID)) {
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(m, p, 5));
                    break;
                }
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new TauntPower(m, p)));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new StaggerPower(m, this, 8)));
                break;
            case 4:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new GainStrengthPower(m, 2)));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new LoseStrengthPower(m, 2)));
                break;
            case 5:
                AbstractDungeon.actionManager.addToBottom(new HealAction(m, p, 10));
                break;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            AbstractDungeon.getMonsters().monsters.forEach(e -> randomArgument(p, e));
        }
    }
}
