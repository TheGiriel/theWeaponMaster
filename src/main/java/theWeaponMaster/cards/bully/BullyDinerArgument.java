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
import theWeaponMaster.powers.ViciousPower;

import java.util.HashSet;
import java.util.Random;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;
import static theWeaponMaster.patches.WeaponMasterTags.*;

public class BullyDinerArgument extends AbstractBullyCard {

    public static final String ID = TheWeaponMaster.makeID(BullyDinerArgument.class.getSimpleName());
    public static final String IMG = makeCardPath("bullydinerargument.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    private static final int COST = 3;
    private static final int MAGIC_NUMBER = 2;
    private static final int UPGRADED_MAGIC_NUMBER = 1;
    private static final int BULLY_COST = 10;
    private static final int UPGRADED_BULLY_NUMBER = 3;
    private HashSet<AbstractMonster.Intent> attacking = new HashSet<>();
    private HashSet<AbstractMonster.Intent> defending = new HashSet<>();


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
            increaseVicious(UPGRADED_BULLY_NUMBER);
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
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new IntimidatePower(m, p)));
                break;
            case 2:
                if (attacking.contains(m.intent)) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new GainStrengthPower(m, 2)));
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new LoseStrengthPower(m, 2)));
                    break;
                }
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new TauntPower(m, p)));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new StaggerPower(m, p, 8)));
                break;
            case 4:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new GainStrengthPower(m, 2)));
                break;
            case 5:
                AbstractDungeon.actionManager.addToBottom(new HealAction(m, p, 10));
                break;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                randomArgument(p, monster);
            }
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ViciousPower(p, bullyNumber)));
    }
}
