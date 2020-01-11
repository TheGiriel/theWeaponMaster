package theWeaponMaster.cards.legendary_weapons;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.FenrirEvolveAction;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.powers.DefensiveStancePower;
import theWeaponMaster.relics.SplinteringSteelRelic;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class FenrirDefensiveStance extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(FenrirDefensiveStance.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG = makeCardPath("lw_fenrir.png");

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int BLOCK = 6;
    private static final int UPGRADED_BLOCK = 3;

    public static final String weapon = "Fenrir";

    public FenrirDefensiveStance() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.block = baseBlock = BLOCK;
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
            initializeDescription();
        }
    }

    //TODO: Reduce block gained by defensive stance when using Fenrir cards.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DefensiveStancePower(p, block)));
        int attacking = 0;
        int numbMonsters = AbstractDungeon.getMonsters().monsters.size();
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (monster.intent == AbstractMonster.Intent.ATTACK || monster.intent == AbstractMonster.Intent.ATTACK_DEFEND || monster.intent == AbstractMonster.Intent.ATTACK_DEBUFF || monster.intent == AbstractMonster.Intent.ATTACK_BUFF) {
                attacking++;
            }
        }
        if (attacking == numbMonsters) {
            new FenrirEvolveAction();
        }
    }
}
