package theWeaponMaster.cards.legendary_weapons;

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
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.powers.ManaBurnPower;
import theWeaponMaster.relics.ManaWhetstoneRelic;

import static com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField.grave;
import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class AtroposSeveredSoul extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(AtroposSeveredSoul.class.getSimpleName());
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    public static final String IMG = makeCardPath("Attack.png");

    public static final CardRarity RARITY = CardRarity.SPECIAL;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    public static final int COST = 3;
    public static final int DAMAGE = 15;
    public static final int UPGRADED_DAMAGE = 5;

    public AtroposSeveredSoul() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.cost = COST;
        this.damage = baseDamage = DAMAGE;

        grave.set(this, true);
    }

    @Override
    public boolean canUpgrade() {
        return AbstractDungeon.player.hasRelic(ManaWhetstoneRelic.ID);
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
    public float calculateModifiedCardDamage(AbstractPlayer player, float tmp) {
        return super.calculateModifiedCardDamage(player, tmp);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int manaBurn = 0;
        if (m.hasPower(ManaBurnPower.POWER_ID)) {
            this.modifyCostForTurn(-m.getPower(ManaBurnPower.POWER_ID).amount);
            manaBurn = m.getPower(ManaBurnPower.POWER_ID).amount;
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        ManaBurnAction.ignite(m, manaBurn);
        if (manaBurn != 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, p, ManaBurnPower.POWER_ID));
        }
    }
}
