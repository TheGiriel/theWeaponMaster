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
import theWeaponMaster.DefaultMod;
import theWeaponMaster.actions.GiveWeaponsAction;
import theWeaponMaster.characters.TheWeaponMaster;
import theWeaponMaster.powers.ManaBurnPower;
import theWeaponMaster.relics.ManaWhetstoneRelic;

import static theWeaponMaster.DefaultMod.makeCardPath;

public class AtroposSeveredScissors extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(AtroposSeveredScissors.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    public static final String IMG = makeCardPath("Attack.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheWeaponMaster.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 3;
    private static final int UPGRADED_DAMAGE = 1;
    private static final int MAGIC_NUMBER = 1;
    private static final int UPGRADED_MAGIC_NUMBER = 1;

    public AtroposSeveredScissors() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = baseDamage = DAMAGE;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            upgradeDamage(UPGRADED_DAMAGE);
            initializeDescription();
        }
    }

    @Override
    public boolean canUpgrade() {
        return AbstractDungeon.player.hasRelic(ManaWhetstoneRelic.ID);
    }

    //TODO: Make action
    public void manaBurnDamage(AbstractPlayer p, AbstractMonster m) {
        if (m.hasPower(ManaBurnPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, (int) (Math.ceil(m.maxHealth * ManaBurnPower.igniteDamage * m.getPower(ManaBurnPower.POWER_ID).amount)), DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.FIRE));
        }
    }

    //TODO: Improve Code
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        if (upgraded) {
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.33F));
            manaBurnDamage(p, m);
        }
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.33F));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.33F));
        manaBurnDamage(p, m);
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.33F));
        AbstractDungeon.player.masterDeck.removeCard(this.cardID);
        AbstractDungeon.actionManager.addToBottom(new GiveWeaponsAction("Scissor Half"));
    }
}
