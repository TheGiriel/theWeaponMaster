package theWeaponMaster.cards.revolver;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.CustomCartridgeAction;
import theWeaponMaster.cards.abstractcards.AbstractRevolverCard;
import theWeaponMaster.patches.WeaponMasterTags;
import theWeaponMaster.relics.RevolverRelic;

import java.util.ArrayList;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class RevolverCustomCartridge extends AbstractRevolverCard {


    public static final String ID = TheWeaponMaster.makeID(RevolverCustomCartridge.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final String[] DESCRIPTIONS = cardStrings.EXTENDED_DESCRIPTION;
    public static final CardRarity RARITY = CardRarity.UNCOMMON;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final CardType TYPE = CardType.ATTACK;
    public static final int COST = 2;
    private static final int UPGRADED_COST = 1;

    public static final int MAGIC_NUMBER = 150;
    public static final int UPGRADED_MAGIC_NUMBER = 50;
    private static final int SECOND_VALUE = 75;
    private static final int UPGRADED_SECOND_VALUE = 25;

    public RevolverCustomCartridge() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.secondValue = baseSecondValue = SECOND_VALUE;

        initializeDescription();
    }

    @Override
    public boolean cardPlayable(AbstractMonster m) {
        ArrayList<AbstractCard> ammunitionCards = new ArrayList(AbstractDungeon.player.hand.group);
        boolean canUse = ammunitionCards.stream().anyMatch(e -> e.hasTag(WeaponMasterTags.AMMUNITION) && (!e.cardID.equals(RevolverBuckshot.ID) || !e.cardID.equals(RevolverWarningShot.ID) || !e.cardID.equals(RevolverKneecap.ID)));
        if (canUse) {
            return true;
        } else {
            cantUseMessage = "I don't have any appropriate ammo...";
            return false;
        }
    }

    @Override
    public void setNormalDescription() {
        this.cost = COST;
        rawDescription = DESCRIPTIONS[0];
        type = TYPE;
        target = TARGET;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.getRelic(RevolverRelic.ID).counter <= 0) {
            return;
        }
        AbstractDungeon.actionManager.addToBottom(new CustomCartridgeAction(m, magicNumber, secondValue, this));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            upgradeSecondValue(UPGRADED_SECOND_VALUE);
            this.rawDescription = UPGRADED_DESCRIPTION;
            initializeDescription();
        }
    }
}
