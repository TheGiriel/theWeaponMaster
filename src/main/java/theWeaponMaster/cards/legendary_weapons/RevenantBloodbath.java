package theWeaponMaster.cards.legendary_weapons;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.RevenantStarveAction;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.powers.ViciousPower;
import theWeaponMaster.relics.ArsenalRelic;
import theWeaponMaster.relics.GhoulskinSheathRelic;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;
import static theWeaponMaster.patches.WeaponMasterTags.REVENANT;
import static theWeaponMaster.powers.ViciousPower.TIER_TWO;

public class RevenantBloodbath extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(RevenantBloodbath.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    public static final CardRarity RARITY = CardRarity.SPECIAL;
    public static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    public static final CardType TYPE = CardType.ATTACK;
    public static final int COST = 3;
    public static final int DAMAGE = 7;
    public static final int UPGRADED_DAMAGE = 3;
    public static final int MAGIC_NUMBER = 10;
    public static final int HUNGER = 0;

    public RevenantBloodbath() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.damage = baseDamage = DAMAGE;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.secondValue = baseSecondValue = TIER_TWO;

        this.setBackgroundTexture("theWeaponMasterResources/images/512/bg_revenant_attack.png",
                "theWeaponMasterResources/images/1024/bg_revenant_attack.png");

        getSated();
        tags.add(REVENANT);
        initializeDescription();

        initializeDescription();
    }

    @Override
    public void initializeDescription() {
        getSated();
        super.initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADED_DAMAGE);
            rawDescription = DESCRIPTION[1];
            initializeDescription();
        }
    }


    @Override
    public boolean cardPlayable(AbstractMonster m) {
        if (AbstractDungeon.player.energy.energy != 0 || AbstractDungeon.player.hasRelic(ChemicalX.ID) || ArsenalRelic.revenantHunger + AbstractDungeon.player.getPower(ViciousPower.POWER_ID).amount >= TIER_TWO) {
            return true;
        }
        cantUseMessage = "I don't have enough Energy or Vicious.";
        return false;
    }

    public void getSated() {
        if (upgraded) {
            rawDescription = DESCRIPTION[1];
        } else rawDescription = DESCRIPTION[0];
    }

    public boolean canUpgrade() {
        return AbstractDungeon.player.hasRelic(GhoulskinSheathRelic.ID);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int initialDamageBonus = p.getPower(ViciousPower.POWER_ID).amount / 5;

        p.getPower(ViciousPower.POWER_ID).amount += ArsenalRelic.revenantHunger + 5;
        AbstractDungeon.actionManager.addToBottom(new RevenantStarveAction(-HUNGER, false));

        for (int i = p.getPower(ViciousPower.POWER_ID).amount; i >= 5; i -= 5) {
            int finalDamageBonus = p.getPower(ViciousPower.POWER_ID).amount / TIER_TWO;
            AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(new DamageInfo(p, (damage - initialDamageBonus + finalDamageBonus), DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            p.getPower(ViciousPower.POWER_ID).amount -= TIER_TWO;
        }
    }
}
