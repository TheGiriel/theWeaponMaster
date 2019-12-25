package theWeaponMaster.cards.legendary_weapons;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.FlashAction;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;

import java.util.ArrayList;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class CerberusModularSlash extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(CerberusModularSlash.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 2;
    private static final int DAMAGE = 12;
    private static final int UPGRADED_DAMAGE = 3;
    private static final int MAGIC_NUMBER = 4;
    private static final int UPGRADED_MAGIC_NUMBER = 1;

    public CerberusModularSlash() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = baseDamage = DAMAGE;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
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
        int flashBonus = -2;
        int flashBlock = 0;
        int flashEnergy = 0;
        int flashCurse = 0;

        if (state != null && discarded != null) {
            if (discarded.size() > 1) {
                for (AbstractCard c : discarded) {
                    if (c.type.equals(CardType.ATTACK)) {
                        flashBonus += 2;
                    }
                    if (c.type.equals(CardType.SKILL)) {
                        flashBlock += 5;
                    }
                    if (c.type.equals(CardType.STATUS) || c.type.equals(CardType.CURSE)) {
                        flashCurse++;
                    }
                    if (c.type.equals(CardType.POWER)) {
                        flashEnergy++;
                    }
                }
            }
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage + flashBonus, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        if (flashBlock != 0) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, flashBlock));
        }
        if (flashEnergy != 0) {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(flashEnergy / 2));
        }
        if (flashCurse != 0) {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, flashCurse / 2));
        }

    }
}
