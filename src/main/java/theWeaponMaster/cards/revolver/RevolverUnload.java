package theWeaponMaster.cards.revolver;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.abstractcards.AbstractRevolverCard;
import theWeaponMaster.relics.RevolverRelic;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager;
import static theWeaponMaster.TheWeaponMaster.makeCardPath;
import static theWeaponMaster.patches.WeaponMasterTags.AMMUNITION;

public class RevolverUnload extends AbstractRevolverCard {

    public static final String ID = TheWeaponMaster.makeID(RevolverUnload.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    public static final CardRarity RARITY = CardRarity.RARE;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final CardType TYPE = CardType.ATTACK;
    public static final int COST = 3;
    public static final int DAMAGE = 6;
    private static final int UPGRADE_DAMAGE = 2;
    public static int SECOND_VALUE = 6;

    public RevolverUnload() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.damage = baseDamage = DAMAGE;
        initializeDescription();
        this.secondValue = baseSecondValue = SECOND_VALUE;

        purgeOnUse = true;

    }

    @Override
    public void upgrade() {
        upgradeName();
        upgradeDamage(UPGRADE_DAMAGE);
        initializeDescription();
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (c.hasTag(AMMUNITION) && c.type == CardType.ATTACK) {
            baseSecondValue = RevolverRelic.shotsLeft;
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < Math.min(secondValue, p.drawPile.size()); i++) {
            actionManager.addToBottom(new MoveCardsAction(p.discardPile, p.drawPile));
        }
    }

}
