package theWeaponMaster.cards.revolver;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.ReloadAction;
import theWeaponMaster.cards.abstractcards.AbstractRevolverCard;
import theWeaponMaster.patches.WeaponMasterTags;
import theWeaponMaster.powers.StaggerPower;
import theWeaponMaster.relics.RevolverRelic;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class RevolverWarningShot extends AbstractRevolverCard {


    public static final String ID = TheWeaponMaster.makeID(RevolverWarningShot.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] DESCRIPTIONS = cardStrings.EXTENDED_DESCRIPTION;
    public static final CardRarity RARITY = CardRarity.COMMON;
    public static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    public static final CardType TYPE = CardType.SKILL;
    public static final int COST = 1;
    public static final int UPGRADED_COST = 0;

    public RevolverWarningShot() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        tags.add(WeaponMasterTags.AMMUNITION);
    }

    @Override
    public void setNormalDescription() {
        this.cost = COST;
        rawDescription = DESCRIPTION;
        type = TYPE;
        target = TARGET;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.getRelic(RevolverRelic.ID).counter <= 0) {
            new ReloadAction();
            return;
        }
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new StaggerPower(monster, this, 0)));
        }
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
