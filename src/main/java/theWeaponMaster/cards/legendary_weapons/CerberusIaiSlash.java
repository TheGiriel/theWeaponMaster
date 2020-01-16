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
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.FlashAction;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.relics.HellhoundOilRelic;

import java.util.ArrayList;

import static com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField.alwaysRetain;
import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class CerberusIaiSlash extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(CerberusIaiSlash.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final CardRarity RARITY = CardRarity.SPECIAL;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final CardType TYPE = CardType.ATTACK;
    public static final int COST = 2;
    public static final int DAMAGE = 9;
    public static final int UPGRADED_DAMAGE = 3;
    public static final int MAGIC_NUMBER = 2;
    public static final int UPGRADED_MAGIC_NUMBER = 1;
    int turnCount = 0;

    public CerberusIaiSlash() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.damage = baseDamage = DAMAGE;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.secondValue = baseSecondValue = turnCount;

        this.setBackgroundTexture("theWeaponMasterResources/images/512/bg_cerberus_attack.png", "theWeaponMasterResources/images/1024/bg_cerberus_attack.png");

        alwaysRetain.set(this, true);

        initializeDescription();
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
    public void triggerOnManualDiscard() {
        baseSecondValue = turnCount = 0;
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
        int flashBonus = discarded.size() * 2 - 2;
        float iaiBonus = 0.25f;
        if (upgraded) {
            iaiBonus += 0.08f;
        }

        if (state != null) {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, discarded.size() - 2));
            if (upgraded && discarded.size() - 1 >= magicNumber - 1) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, (int) (this.damage + (flashBonus * (1 + iaiBonus * (turnCount)))), DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            } else if (discarded.size() - 1 == magicNumber) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, (int) (this.damage + (flashBonus * (1 + iaiBonus * (turnCount)))), DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            } else {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, (this.damage + flashBonus), DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            }
        }

        baseSecondValue = turnCount = 0;
    }

    @Override
    public void atTurnStart() {
        if (AbstractDungeon.player.hand.group.contains(this)) {
            turnCount++;
            baseSecondValue++;
        }
    }

}

