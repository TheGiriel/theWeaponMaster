package theWeaponMaster.cards.martialarts;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.patches.WeaponMasterTags;
import theWeaponMaster.powers.RecklessnessPower;
import theWeaponMaster.powers.ViciousPower;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class MartialRecklessness extends AbstractDynamicCard {


    public static final String ID = TheWeaponMaster.makeID(MartialRecklessness.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final CardRarity RARITY = CardRarity.UNCOMMON;
    public static final CardTarget TARGET = CardTarget.SELF;
    public static final CardType TYPE = CardType.POWER;

    public static final int COST = 2;
    public static final int UPGRADED_COST = 1;

    public MartialRecklessness() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        int playerVicious;
        try {
            playerVicious = AbstractDungeon.player.getPower(ViciousPower.POWER_ID).amount / 10;
        } catch (NullPointerException e) {
            playerVicious = 0;
        }
        this.magicNumber = baseMagicNumber = Math.max(1, playerVicious);

        tags.add(WeaponMasterTags.MARTIAL);
        initializeDescription();
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RecklessnessPower(p, p)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new VulnerablePower(p, magicNumber, false)));
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        this.magicNumber = baseMagicNumber = Math.max(1, AbstractDungeon.player.getPower(ViciousPower.POWER_ID).amount / 5);
        super.onPlayCard(c, m);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
