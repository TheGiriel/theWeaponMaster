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
import theWeaponMaster.relics.HellhoundOilRelic;

import java.util.ArrayList;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class CerberusModularSlash extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(CerberusModularSlash.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final CardRarity RARITY = CardRarity.SPECIAL;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final CardType TYPE = CardType.ATTACK;
    public static final int COST = 2;
    public static final int DAMAGE = 10;
    public static final int UPGRADED_DAMAGE = 2;
    public static final int MAGIC_NUMBER = 4;
    public static final int UPGRADED_MAGIC_NUMBER = 1;

    public CerberusModularSlash() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.damage = baseDamage = DAMAGE;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;

        this.setBackgroundTexture("theWeaponMasterResources/images/512/bg_cerberus_attack.png", "theWeaponMasterResources/images/1024/bg_cerberus_attack.png");

        initializeDescription();
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
        int flashBonus = 0;
        int flashBlock = 0;
        int flashEnergy = 0;
        int flashCurse = 0;

        if (state != null && discarded != null) {
            if (discarded.size() > 1) {
                for (AbstractCard c : discarded) {
                    if (c.type.equals(CardType.ATTACK)) {
                        flashBonus++;
                    }
                    if (c.type.equals(CardType.SKILL)) {
                        flashBlock += 3;
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
