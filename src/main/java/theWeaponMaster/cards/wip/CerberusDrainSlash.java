package theWeaponMaster.cards.wip;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class CerberusDrainSlash extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(CerberusDrainSlash.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    public static final String IMG = makeCardPath("Attack.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int UPGRADED_DAMAGE = 3;
    private static final int MAGIC_NUMBER = 1;

    private int powerAmt = 0;

    private HashSet<String> stolenPower = new HashSet<>();

    public CerberusDrainSlash() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        stolenPower.add(BarricadePower.POWER_ID);
        stolenPower.add(StrengthPower.POWER_ID);
        stolenPower.add(MetallicizePower.POWER_ID);
        stolenPower.add(PlatedArmorPower.POWER_ID);
        stolenPower.add(MalleablePower.POWER_ID);
        stolenPower.add(ArtifactPower.POWER_ID);
        stolenPower.add(AngryPower.POWER_ID);
        stolenPower.add(IntangiblePower.POWER_ID);
        stolenPower.add(RitualPower.POWER_ID);
        stolenPower.add(ThieveryPower.POWER_ID);

    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADED_DAMAGE);
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {


        ArrayList<String> tempSteal = new ArrayList<>();
        ArrayList<Integer> tempStealValue = new ArrayList<>();
        if (m.powers.size() != 0) {
            for (AbstractPower power : m.powers) {
                if (stolenPower.contains(power.ID)) {
                    tempSteal.add(power.ID);
                    tempStealValue.add(power.amount);
                }
            }
            if (tempSteal.size() != 0) {
                int i = new Random().nextInt(tempSteal.size());
                switch (tempSteal.get(i)) {
                    case AngryPower.POWER_ID:
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new AngryPower(p, tempStealValue.get(i))));
                        break;
                    case BarricadePower.POWER_ID:
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BarricadePower(p)));
                        break;
                    case ArtifactPower.POWER_ID:
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, tempStealValue.get(i))));
                        break;
                    case StrengthPower.POWER_ID:
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, tempStealValue.get(i))));
                        break;
                    case MalleablePower.POWER_ID:
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MalleablePower(p, tempStealValue.get(i))));
                        break;
                    case PlatedArmorPower.POWER_ID:
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PlatedArmorPower(p, tempStealValue.get(i))));
                        break;
                    case MetallicizePower.POWER_ID:
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MetallicizePower(p, tempStealValue.get(i))));
                        break;
                    case IntangiblePower.POWER_ID:
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new IntangiblePower(p, tempStealValue.get(i))));
                        break;
                    case RitualPower.POWER_ID:
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RitualPower(p, tempStealValue.get(i))));
                        break;
                    case ThieveryPower.POWER_ID:
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ThieveryPower(p, tempStealValue.get(i))));
                        break;
                }
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(m, p, tempSteal.get(i), tempStealValue.get(i)));
            }
            //&& stolenPower.contains(m.powers.get(new Random().nextInt(m.powers.size())))

        }
    }
}
