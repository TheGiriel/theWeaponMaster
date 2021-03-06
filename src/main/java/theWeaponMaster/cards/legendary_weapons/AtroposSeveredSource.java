package theWeaponMaster.cards.legendary_weapons;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
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

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class AtroposSeveredSource extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(AtroposSeveredSource.class.getSimpleName());
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    public static final String IMG = makeCardPath("Attack.png");

    public static final CardRarity RARITY = CardRarity.SPECIAL;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    public static final int COST = 1;
    public static final int DAMAGE = 6;
    public static final int UPGRADED_DAMAGE = 2;
    public static final int MAGIC_NUMBER = 1;
    public static final int UPGRADED_MAGIC_NUMBER = 1;

    public AtroposSeveredSource() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.damage = baseDamage = DAMAGE;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        isInnate = true;

        this.setBackgroundTexture("theWeaponMasterResources/images/512/bg_atropos_attack.png", "theWeaponMasterResources/images/1024/bg_atropos_attack.png");

        initializeDescription();
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADED_DAMAGE);
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            initializeDescription();
        }
    }

    @Override
    public boolean canUpgrade() {
        return AbstractDungeon.player.hasRelic(ManaWhetstoneRelic.ID);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new ManaBurnPower(m, p, 1)));
        ManaBurnAction.ignite(m, magicNumber);
    }

}