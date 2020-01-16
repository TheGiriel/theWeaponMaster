package theWeaponMaster.cards.legendary_weapons;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.FenrirEvolveAction;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.powers.LaceratePower;
import theWeaponMaster.powers.ViciousPower;
import theWeaponMaster.relics.SplinteringSteelRelic;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class FenrirHeavySwing extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(FenrirHeavySwing.class.getSimpleName());
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] DESCRIPTIONS = cardStrings.EXTENDED_DESCRIPTION;

    public static final String IMG = makeCardPath("fenrirheavyswing.png");

    public static final CardRarity RARITY = CardRarity.SPECIAL;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    public static final int COST = 2;
    public static final int DAMAGE = 3;
    public static final int UPGRADED_DAMAGE = 1;
    public static final int MAGIC_NUMBER = 0;

    public FenrirHeavySwing() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.damage = baseDamage = DAMAGE + (magicNumber / 2);
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;

        this.setBackgroundTexture("theWeaponMasterResources/images/512/bg_fenrir_attack.png", "theWeaponMasterResources/images/1024/bg_fenrir_attack.png");

        initializeDescription();
    }

    @Override
    public boolean canUpgrade() {
        return AbstractDungeon.player.hasRelic(SplinteringSteelRelic.ID);
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
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        baseDamage = DAMAGE + (magicNumber / 2);
        initializeDescription();
    }


    @Override
    public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster mo, float tmp) {
        if (player.hasPower(ViciousPower.POWER_ID)) {
            rawDescription = DESCRIPTIONS[0] + (int) (tmp * player.hand.size()) + DESCRIPTIONS[1] + (player.hand.size() / 2) + DESCRIPTIONS[2];
            initializeDescription();
            return (tmp * player.hand.size()) + (float) player.getPower(ViciousPower.POWER_ID).amount / 5;
        } else
            rawDescription = DESCRIPTIONS[0] + (int) (tmp * player.hand.size()) + DESCRIPTIONS[1] + (player.hand.size() / 2) + DESCRIPTIONS[2];
        initializeDescription();
        return (tmp * player.hand.size());
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new LaceratePower(m, p.hand.size() / 3)));
        if (p.hand.size() > 3) {
            new FenrirEvolveAction();
        }
        rawDescription = DESCRIPTIONS[0] + damage + DESCRIPTIONS[1] + (p.hand.size() / 3) + DESCRIPTIONS[2];
        initializeDescription();
    }
}