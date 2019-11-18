package theWeaponMaster.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.ManaBurnAction;
import theWeaponMaster.powers.ManaBurnPower;

import static com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField.grave;
import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class AtroposSeveredSoul extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(AtroposSeveredSoul.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    public static final String IMG = makeCardPath("Attack.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    private static final int COST = 3;
    private static final int DAMAGE = 23;
    private static final int UPGRADED_DAMAGE = 7;

    public AtroposSeveredSoul() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.cost = COST;
        this.damage = baseDamage = DAMAGE;

        grave.set(this, true);
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
        int manaBurn = 0;
        if (m.hasPower(ManaBurnPower.POWER_ID)) {
            manaBurn = m.getPower(ManaBurnPower.POWER_ID).amount;
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        ManaBurnAction.ignite(m, manaBurn);
        if (manaBurn != 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, p, ManaBurnPower.POWER_ID));
        }
    }
}
