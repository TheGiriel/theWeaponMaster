package theWeaponMaster.cards.revolver;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.ReloadAction;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.patches.WeaponMasterTags;
import theWeaponMaster.relics.HeavyDrum;
import theWeaponMaster.relics.RevolverRelic;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class RevolverQuickload extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(RevolverQuickload.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final CardRarity RARITY = CardRarity.UNCOMMON;
    public static final CardTarget TARGET = CardTarget.SELF;
    public static final CardType TYPE = CardType.SKILL;
    public static final int COST = 1;
    public static final int UPGRADED_COST = 0;

    private int reloadThreshold = 6;

    public RevolverQuickload() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        if (AbstractDungeon.isPlayerInDungeon() && AbstractDungeon.player.hasRelic(HeavyDrum.ID)) {
            reloadThreshold = 5;
        }

        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ReloadAction());
        for (AbstractCard card : p.drawPile.group) {
            if (card.hasTag(WeaponMasterTags.AMMUNITION)) {
                p.drawPile.moveToHand(card, p.drawPile);
                break;
            }
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

    @Override
    public boolean cardPlayable(AbstractMonster m) {
        if (RevolverRelic.shotsLeft != reloadThreshold) {
            return true;
        } else
            cantUseMessage = "There's no point reloading a full gun!";
        return false;
    }
}
