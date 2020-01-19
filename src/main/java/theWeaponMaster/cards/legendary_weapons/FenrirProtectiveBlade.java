package theWeaponMaster.cards.legendary_weapons;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.EnemyForceAction;
import theWeaponMaster.actions.FenrirEvolveAction;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.patches.WeaponMasterTags;
import theWeaponMaster.powers.ProtectiveBladePower;
import theWeaponMaster.relics.SplinteringSteelRelic;

import java.util.HashSet;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class FenrirProtectiveBlade extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(FenrirProtectiveBlade.class.getSimpleName());
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG = makeCardPath("lw_fenrir.png");

    public static final CardRarity RARITY = CardRarity.SPECIAL;
    public static final CardTarget TARGET = CardTarget.SELF;
    public static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    public static final int COST = 1;
    public static final int BLOCK = 8;
    public static final int UPGRADED_BLOCK = 2;
    public static final int MAGIC_NUMBER = 2;
    public static final int UPGRADED_MAGIC_NUMBER = 1;
    public static final int SECOND_VALUE = 0;

    private static HashSet<AbstractMonster.Intent> intents;

    public FenrirProtectiveBlade() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.block = baseBlock = BLOCK;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.secondValue = baseSecondValue = SECOND_VALUE;

        tags.add(WeaponMasterTags.INTIMIDATE);
        intents = EnemyForceAction.getIntents(this);

        this.setBackgroundTexture("theWeaponMasterResources/images/512/bg_fenrir_skill.png", "theWeaponMasterResources/images/1024/bg_fenrir_skill.png");

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
            upgradeBlock(UPGRADED_BLOCK);
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            initializeDescription();
        }
    }

    //TODO: Reduce block gained by defensive stance when using Fenrir cards.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ProtectiveBladePower(p, magicNumber)));
        boolean evolve = true;
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (!intents.contains(monster.intent)) {
                evolve = false;
                break;
            }
        }
        if (evolve) {
            new FenrirEvolveAction();
        }
    }
}
