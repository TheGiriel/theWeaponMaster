package theWeaponMaster.cards.Not_finished;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.GiveWeaponsAction;
import theWeaponMaster.cards.AbstractDynamicCard;
import theWeaponMaster.relics.ArsenalRelic;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class LeviathanReload extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(LeviathanReload.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = makeCardPath("Skill.png");

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int BLOCK = 8;

    public LeviathanReload() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        block = baseBlock = BLOCK;
    }

    @Override
    public void upgrade() {

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArsenalRelic.leviathanShots = 3;
        AbstractDungeon.player.masterDeck.removeCard(this.cardID);
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        AbstractDungeon.actionManager.addToBottom(new GiveWeaponsAction("Eject"));
    }
}
