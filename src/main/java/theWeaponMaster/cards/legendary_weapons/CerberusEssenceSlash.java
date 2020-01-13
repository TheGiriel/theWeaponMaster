package theWeaponMaster.cards.legendary_weapons;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.FlashAction;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.relics.HellhoundOilRelic;

import java.util.ArrayList;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class CerberusEssenceSlash extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(CerberusEssenceSlash.class.getSimpleName());
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    public static final String IMG = makeCardPath("Attack.png");

    public static final CardRarity RARITY = CardRarity.SPECIAL;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    public static final int COST = 1;
    public static final int DAMAGE = 6;
    public static final int UPGRADED_DAMAGE = 3;
    public static final int MAGIC_NUMBER = 2;
    public static final int UPGRADED_MAGIC_NUMBER = 1;
    private static final int SECOND_VALUE = 2;
    private static final int UPGRADED_SECOND_VALUE = 1;

    public CerberusEssenceSlash() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = baseDamage = DAMAGE;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.secondValue = baseSecondValue = SECOND_VALUE;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADED_DAMAGE);
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            upgradeSecondValue(UPGRADED_SECOND_VALUE);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public boolean canUpgrade() {
        return AbstractDungeon.player.hasRelic(HellhoundOilRelic.ID);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(new FlashAction(this, magicNumber, m, this::Flash, true));
    }

    private void Flash(Object state, ArrayList<AbstractCard> discarded) {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractMonster m = (AbstractMonster) state;
        int essenceDamage = 0;
        int flashBonus = discarded.size() - 1;

        if (state != null) {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, discarded.size() - 2));

            if (upgraded && discarded.size() - 1 >= magicNumber - 1) {
                for (AbstractPower power : m.powers) {
                    essenceDamage++;
                }
            } else if (discarded.size() - 1 == magicNumber) {
                for (AbstractPower power : m.powers) {
                    essenceDamage++;
                }
            }
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage + flashBonus, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, essenceDamage * secondValue, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.FIRE));

    }
}
