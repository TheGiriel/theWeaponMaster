package theWeaponMaster.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.relics.HellhoundOilRelic;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class CerberusIaiSlash extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(CerberusIaiSlash.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG = makeCardPath("Attack.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 6;
    private static final int UPGRADED_DAMAGE = 3;
    private static final int MAGIC_NUMBER = 2;

    public CerberusIaiSlash() {
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

    @Override
    public boolean canUpgrade() {
        return AbstractDungeon.player.hasRelic(HellhoundOilRelic.ID);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int iaiBonus;
        if (p.hand.size() == 1) {
            iaiBonus = 0;
        }
        if (p.hand.size() == 2) {
            iaiBonus = 2;
        } else iaiBonus = 4;
        AbstractDungeon.actionManager.addToBottom(new DiscardAction(p, p, magicNumber, true));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage + iaiBonus), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
    }
}
