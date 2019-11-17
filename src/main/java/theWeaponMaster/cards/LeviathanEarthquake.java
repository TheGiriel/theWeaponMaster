package theWeaponMaster.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.relics.ShockwaveModulatorRelic;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class LeviathanEarthquake extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(LeviathanEarthquake.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    public static final String IMG = makeCardPath("Attack.png");

    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    public static final AbstractCard.CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    private static final int COST = 3;
    private static final int DAMAGE = 12;
    private static final int UPGRADED_DAMAGE = 5;
    private static final int MAGIC_NUMBER = 1;

    private static int thorns;

    public LeviathanEarthquake() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = baseDamage = DAMAGE;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADED_DAMAGE);
            initializeDescription();
        }
    }

    public boolean canUpgrade() {
        return AbstractDungeon.player.hasRelic(ShockwaveModulatorRelic.ID);
    }

    //TODO: Improve method
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster c : AbstractDungeon.getMonsters().monsters) {
            if (c.hasPower(ThornsPower.POWER_ID)) {
                //thorns = c.getPower(ThornsPower.POWER_ID).amount;
                int blockedDamage = damage;
                //new ThornBypassAction(c, thorns);
                if (c.currentBlock > 0) {
                    if (damage > c.currentBlock) {
                        AbstractDungeon.actionManager.addToBottom(new LoseBlockAction(c, p, c.currentBlock));
                        blockedDamage = damage - c.currentBlock;
                    } else {
                        AbstractDungeon.actionManager.addToBottom(new LoseBlockAction(c, p, damage));
                    }
                } else
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(c, new DamageInfo(p, blockedDamage, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.SMASH));

                //new ThornBypassAction(c, thorns).ThornsReapply();
            } else
                AbstractDungeon.actionManager.addToBottom(new DamageAction(c, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SMASH));
        }
    }
}
