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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class CerberusDrainSlash extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(CerberusDrainSlash.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String[] DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 3;
    private static final int DAMAGE = 20;
    private static final int UPGRADED_DAMAGE = 5;
    private static final int MAGIC_NUMBER = 3;
    private static final int UPGRADED_MAGIC_NUMBER = -1;
    private static final int SECOND_VALUE = 3;
    public String NAME = cardStrings.NAME;
    private HashSet<String> stolenPower = new HashSet<>();

    public CerberusDrainSlash() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = baseDamage = DAMAGE;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.defaultSecondMagicNumber = defaultBaseSecondMagicNumber = SECOND_VALUE;

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
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADED_DAMAGE);
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
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
        int flashBonus = 0;
        boolean steal = false;

        if (state != null && discarded != null) {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, discarded.size() - 2));
            flashBonus = discarded.size() * 2 - 2;
            if (discarded.size() - 1 == magicNumber) {
                stealPowers(p, m);
                baseMagicNumber++;
                defaultBaseSecondMagicNumber++;
            }
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage + flashBonus, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    private void stealPowers(AbstractPlayer p, AbstractMonster m) {
        ArrayList<String> tempSteal = new ArrayList<>();
        ArrayList<Integer> tempStealValue = new ArrayList<>();
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
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new AngryPower(p, Math.min(tempStealValue.get(i), defaultSecondMagicNumber))));
                    break;
                case ThornsPower.POWER_ID:
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ThornsPower(p, Math.min(tempStealValue.get(i), defaultSecondMagicNumber))));
                    break;
                case BarricadePower.POWER_ID:
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BarricadePower(p)));
                    break;
                case ArtifactPower.POWER_ID:
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, Math.min(tempStealValue.get(i), defaultSecondMagicNumber))));
                    break;
                case StrengthPower.POWER_ID:
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, Math.min(tempStealValue.get(i), defaultSecondMagicNumber))));
                    break;
                case MalleablePower.POWER_ID:
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MalleablePower(p, Math.min(tempStealValue.get(i), defaultSecondMagicNumber))));
                    break;
                case PlatedArmorPower.POWER_ID:
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PlatedArmorPower(p, Math.min(tempStealValue.get(i), defaultSecondMagicNumber))));
                    break;
                case MetallicizePower.POWER_ID:
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MetallicizePower(p, Math.min(tempStealValue.get(i), defaultSecondMagicNumber))));
                    break;
                case IntangiblePower.POWER_ID:
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new IntangiblePower(p, Math.min(tempStealValue.get(i), defaultSecondMagicNumber))));
                    break;
                case RitualPower.POWER_ID:
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RitualPower(p, Math.min(tempStealValue.get(i), defaultSecondMagicNumber))));
                    break;
                case ThieveryPower.POWER_ID:
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ThieveryPower(p, Math.min(tempStealValue.get(i), defaultSecondMagicNumber))));
                    break;
            }
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(m, p, tempSteal.get(i), Math.min(tempStealValue.get(i), defaultSecondMagicNumber)));
        }
    }
}
