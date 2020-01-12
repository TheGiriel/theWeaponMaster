package theWeaponMaster.cards.legendary_weapons;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.FenrirEvolveAction;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.relics.SplinteringSteelRelic;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class FenrirHeavySwing extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(FenrirHeavySwing.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG = makeCardPath("fenrirheavyswing.png");

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    private static final int COST = 2;
    private static final int DAMAGE = 3;
    private static final int UPGRADED_DAMAGE = 1;
    private static final int MAGIC_NUMBER = 0;

    public FenrirHeavySwing() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.damage = baseDamage = DAMAGE + magicNumber / 2;

        initializeDescription();
    }

    @Override
    public boolean canUpgrade() {
        return AbstractDungeon.player.hasRelic(SplinteringSteelRelic.ID);
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADED_DAMAGE);
            initializeDescription();
        }
    }

    private int heavySwing(AbstractPlayer player) {
        if (player.hasPower("ViciousPower")) {
            return damage * player.hand.size() + player.getPower("ViciousPower").amount;
        } else return damage * player.hand.size();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, heavySwing(p), DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        if (p.hand.size() > 3) {
            new FenrirEvolveAction();
        }
    }
}