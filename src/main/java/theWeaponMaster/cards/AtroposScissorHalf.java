package theWeaponMaster.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.ManaBurnAction;
import theWeaponMaster.powers.ManaBurnPower;
import theWeaponMaster.relics.ManaWhetstoneRelic;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class AtroposScissorHalf extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(AtroposScissorHalf.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    public static final String IMG = makeCardPath("Attack.png");

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 3;
    private static final int UPGRADED_DAMAGE = 1;
    private static final int MAGIC_NUMBER = 2;
    private static final int UPGRADED_MAGIC_NUMBER = 1;
    private int scissorCombo = 0;

    public AtroposScissorHalf() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = baseDamage = DAMAGE;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
    }

    @Override
    public void atTurnStart() {
        scissorCombo = 0;
    }

    @Override
    public boolean canUpgrade() {
        return AbstractDungeon.player.hasRelic(ManaWhetstoneRelic.ID);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            upgradeDamage(UPGRADED_DAMAGE);
            initializeDescription();
        }
    }

    //TODO: improve code

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.actionManager.cardsPlayedThisTurn.size() >= 2) {
            if (AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisTurn.size() - 2).cardID.equals(AtroposScissorHalf.ID) || AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisTurn.size() - 2).cardID.equals(AtroposSeveredScissors.ID)) {
                scissorCombo = scissorCombo + magicNumber;
            } else {
                scissorCombo = 0;
            }
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage + scissorCombo, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        if (m.hasPower(ManaBurnPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.33F));
            new ManaBurnAction(m, p, m.getPower(ManaBurnPower.POWER_ID).amount);
        }
    }
}