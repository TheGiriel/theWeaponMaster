package theWeaponMaster.cards.legendary_weapons;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.powers.SeveredPainPower;
import theWeaponMaster.relics.ManaWhetstoneRelic;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class AtroposSeveredPain extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(AtroposSeveredPain.class.getSimpleName());
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    public static final String IMG = makeCardPath("Skill.png");

    public static final CardRarity RARITY = CardRarity.SPECIAL;
    public static final CardTarget TARGET = CardTarget.SELF;
    public static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    public static final int COST = 1;
    public static final int MAGIC_NUMBER = 1;
    public static final int UPGRADED_MAGIC_NUMBER = 1;

    public AtroposSeveredPain() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;

        this.setBackgroundTexture("theWeaponMasterResources/images/512/bg_atropos_skill.png", "theWeaponMasterResources/images/1024/bg_atropos_skill.png");

        initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            rawDescription = UPGRADED_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public boolean canUpgrade() {
        return AbstractDungeon.player.hasRelic(ManaWhetstoneRelic.ID);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SeveredPainPower(p)));
        if (upgraded) {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
        }
    }
}
