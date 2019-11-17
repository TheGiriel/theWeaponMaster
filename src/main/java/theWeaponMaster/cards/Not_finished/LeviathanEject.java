package theWeaponMaster.cards.Not_finished;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
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

public class LeviathanEject extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(LeviathanEject.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG = makeCardPath("Attack.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int UPGRADED_DAMAGE = 3;

    public LeviathanEject() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = baseDamage = DAMAGE;
        purgeOnUse = true;
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
    public void use(AbstractPlayer p, AbstractMonster m) {
        //TODO: Create a modular card to reload Leviathan attacks. # The first effect is a 0 energy card that ejects the three spent cartridges to deal damage to random enemies and deals extra damage for every cartridge used. (Sent back to the deck.) # The second effect is a 0 energy card that reloads all cartridges and returns 1 energy if three were expended previously.
        for (int i = 0; i< ArsenalRelic.leviathanShots; i++) {
            AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(new DamageInfo(m, this.damage), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
        ArsenalRelic.leviathanShots = 0;
        AbstractDungeon.player.masterDeck.removeCard(this.cardID);
        AbstractDungeon.actionManager.addToBottom(new GiveWeaponsAction("Reload"));
    }
}
