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
import theWeaponMaster.actions.RevenantStarveAction;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.powers.NoseToTailPower;
import theWeaponMaster.powers.ViciousPower;
import theWeaponMaster.relics.ArsenalRelic;
import theWeaponMaster.relics.GhoulskinSheathRelic;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;
import static theWeaponMaster.patches.WeaponMasterTags.REVENANT;

public class RevenantNoseToTail extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(RevenantNoseToTail.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath("Attack.png");
    public static final String[] DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public String NAME = cardStrings.NAME;

    private static final int COST = 2;
    private static final int DAMAGE = 8;
    private static final int UPGRADED_DAMAGE = 3;
    private static final int MAGIC_NUMBER = 8;
    private static final int UPGRADED_MAGIC_NUMBER = 1;
    private final int HUNGERCOST = 6;

    public RevenantNoseToTail() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.damage = baseDamage = DAMAGE;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.defaultSecondMagicNumber = defaultBaseSecondMagicNumber = ArsenalRelic.revenantHunger;

        getSated();
        tags.add(REVENANT);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            upgradeDamage(UPGRADED_DAMAGE);
        }
    }

    public void getSated() {
        if (ArsenalRelic.revenantHunger < HUNGERCOST) {
            rawDescription = DESCRIPTION[1];
        } else {
            rawDescription = DESCRIPTION[0];
        }
    }

    public boolean canUpgrade() {
        return AbstractDungeon.player.hasRelic(GhoulskinSheathRelic.ID);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        int hungryBoost = 0;
        if (ArsenalRelic.revenantHunger >= HUNGERCOST) {
            new RevenantStarveAction(-HUNGERCOST, false);
            hungryBoost++;
        } else {
            new RevenantStarveAction(0, true);
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new NoseToTailPower(m, magicNumber - hungryBoost), 1));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ViciousPower(p, 3)));
        getSated();
    }
}