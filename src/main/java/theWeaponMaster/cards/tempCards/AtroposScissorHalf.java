package theWeaponMaster.cards.tempCards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
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
import theWeaponMaster.cards.legendary_weapons.AtroposSeveredScissors;
import theWeaponMaster.relics.ManaWhetstoneRelic;
import theWeaponMaster.util.Scissors;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class AtroposScissorHalf extends AbstractDynamicCard implements Scissors {

    public static final String ID = TheWeaponMaster.makeID(AtroposScissorHalf.class.getSimpleName());
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(AtroposScissorHalf.class.getSimpleName());
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardRarity RARITY = CardRarity.SPECIAL;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    public static final int COST = 0;
    public static final int DAMAGE = 3;
    public static final int UPGRADED_DAMAGE = 1;
    public static final int BLOCK = 2;
    public static final int UPGRADED_BLOCK = 1;
    public static final int MAGIC_NUMBER = 1;
    public static final int UPGRADED_MAGIC_NUMBER = 1;
    public String NAME = cardStrings.NAME;

    public AtroposScissorHalf() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = baseDamage = DAMAGE;
        this.block = baseBlock = BLOCK;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;

        this.isEthereal = true;
        this.purgeOnUse = true;

        this.setBackgroundTexture("theWeaponMasterResources/images/512/bg_atropos_skill.png", "theWeaponMasterResources/images/1024/bg_atropos_skill.png");
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
            upgradeBlock(UPGRADED_BLOCK);
            initializeDescription();
        }
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        try {
            AbstractDungeon.player.drawPile.removeCard(this);
            AbstractDungeon.player.discardPile.removeCard(this);
            AbstractDungeon.player.exhaustPile.removeCard(this);
        } catch (NullPointerException e) {

        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        ManaBurnAction.ignite(m, magicNumber);
    }

    @Override
    public void addScissors() {
        scissors.add(AtroposRightHalf.ID);
        scissors.add(AtroposSeveredScissors.ID);
    }
}