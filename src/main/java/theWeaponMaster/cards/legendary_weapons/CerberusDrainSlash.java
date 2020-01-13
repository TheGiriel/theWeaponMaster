package theWeaponMaster.cards.legendary_weapons;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.FlashAction;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.relics.HellhoundOilRelic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class CerberusDrainSlash extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(CerberusDrainSlash.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String[] DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    public static final CardRarity RARITY = CardRarity.SPECIAL;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final CardType TYPE = CardType.ATTACK;
    public static final int COST = 3;
    public static final int DAMAGE = 12;
    public static final int UPGRADED_DAMAGE = 2;
    public static final int MAGIC_NUMBER = 3;
    public static final int UPGRADED_MAGIC_NUMBER = 1;
    private static final int SECOND_VALUE = 3;
    public String NAME = cardStrings.NAME;
    private HashSet<String> stolenPower = new HashSet<>();

    public CerberusDrainSlash() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = baseDamage = DAMAGE;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.secondValue = baseSecondValue = SECOND_VALUE;

        populatePowers();
    }

    public void populatePowers() {
        stolenPower.add(BarricadePower.POWER_ID);
        stolenPower.add(StrengthPower.POWER_ID);
        stolenPower.add(MetallicizePower.POWER_ID);
        stolenPower.add(PlatedArmorPower.POWER_ID);
        stolenPower.add(MalleablePower.POWER_ID);
        stolenPower.add(ArtifactPower.POWER_ID);
        stolenPower.add(AngryPower.POWER_ID);
        stolenPower.add(IntangiblePower.POWER_ID);
        stolenPower.add(ThornsPower.POWER_ID);
        stolenPower.add(RitualPower.POWER_ID);
        stolenPower.add(ThieveryPower.POWER_ID);
    }

    @Override
    public boolean canUpgrade() {
        return AbstractDungeon.player.hasRelic(HellhoundOilRelic.ID);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADED_DAMAGE);
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(new FlashAction(this, magicNumber, m, this::Flash, true));
    }

    private void Flash(Object state, ArrayList<AbstractCard> discarded) {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractMonster m = (AbstractMonster) state;
        int flashBonus = discarded.size() - 1;

        if (state != null) {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, discarded.size() - 2));
            if (upgraded && discarded.size() - 1 >= magicNumber - 1) {
                stealPowers(p, m);
                baseMagicNumber += 2;
                baseSecondValue += 2;
            } else if (discarded.size() - 1 == magicNumber) {
                stealPowers(p, m);
                baseMagicNumber += 2;
                baseSecondValue += 2;
            }
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage + flashBonus, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
    }

    private void stealPowers(AbstractPlayer p, AbstractMonster m) {
        ArrayList<String> tempSteal = new ArrayList<>();
        ArrayList<Integer> tempStealValue = new ArrayList<>();
        for (AbstractPower power : m.powers) {
            if (stolenPower.contains(power.ID) && power.amount > 0) {
                tempSteal.add(power.ID);
                tempStealValue.add(power.amount);
            }
        }
        if (tempSteal.size() != 0) {
            int i = new Random().nextInt(tempSteal.size());
            switch (tempSteal.get(i)) {
                case AngryPower.POWER_ID:
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new AngryPower(p, Math.min(tempStealValue.get(i), secondValue))));
                    break;
                case ThornsPower.POWER_ID:
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ThornsPower(p, Math.min(tempStealValue.get(i), secondValue))));
                    break;
                case BarricadePower.POWER_ID:
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BarricadePower(p)));
                    break;
                case ArtifactPower.POWER_ID:
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, Math.min(tempStealValue.get(i), secondValue))));
                    break;
                case StrengthPower.POWER_ID:
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, Math.min(tempStealValue.get(i), secondValue))));
                    break;
                case MalleablePower.POWER_ID:
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MalleablePower(p, Math.min(tempStealValue.get(i), secondValue))));
                    break;
                case PlatedArmorPower.POWER_ID:
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PlatedArmorPower(p, Math.min(tempStealValue.get(i), secondValue))));
                    break;
                case MetallicizePower.POWER_ID:
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MetallicizePower(p, Math.min(tempStealValue.get(i), secondValue))));
                    break;
                case IntangiblePower.POWER_ID:
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new IntangiblePower(p, Math.min(tempStealValue.get(i), secondValue))));
                    break;
                case RitualPower.POWER_ID:
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RitualPower(p, Math.min(tempStealValue.get(i), secondValue))));
                    break;
                case ThieveryPower.POWER_ID:
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ThieveryPower(p, Math.min(tempStealValue.get(i), secondValue))));
                    break;
            }
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(m, p, tempSteal.get(i), Math.min(tempStealValue.get(i), secondValue)));
        }
    }
}
