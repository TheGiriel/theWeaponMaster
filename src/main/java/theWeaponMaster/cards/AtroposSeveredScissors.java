package theWeaponMaster.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.ManaBurnAction;
import theWeaponMaster.relics.ManaWhetstoneRelic;
import theWeaponMaster.util.FlipCard;
import theWeaponMaster.util.Scissors;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class AtroposSeveredScissors extends AbstractDynamicCard implements Scissors, FlipCard {

    public static final String ID = TheWeaponMaster.makeID(AtroposSeveredScissors.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    public static final String IMG = makeCardPath("Attack.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 3;
    private static final int UPGRADED_DAMAGE = 1;
    private static final int BLOCK = 2;
    private static final int UPGRADED_BLOCK = 1;
    private static final int MAGIC_NUMBER = 1;
    private static final int UPGRADED_MAGIC_NUMBER = 1;
    public static boolean scissorFlip = false;
    public static boolean leftHalf = false;
    public static boolean rightHalf = false;

    public AtroposSeveredScissors() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = baseDamage = DAMAGE;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.block = baseBlock = BLOCK;

        addScissors();
        exhaust = false;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            upgradeDamage(UPGRADED_DAMAGE);
            upgradeBlock(UPGRADED_BLOCK);
            initializeDescription();
        }
    }

    @Override
    public boolean canUpgrade() {
        return AbstractDungeon.player.hasRelic(ManaWhetstoneRelic.ID);
    }


    //TODO: Improve Code
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (scissorFlip) {
            exhaust = true;
            flipUse(p, m);
        } else {
            standardUse(p, m);
        }
    }


    public void flipCard() {
        if (scissorFlip) {
            this.name = DESCRIPTION[0];
            rawDescription = DESCRIPTION[2];
            this.cost = 0;
        } else {
            this.name = cardStrings.NAME;
            rawDescription = DESCRIPTION[1];
            this.cost = COST;
        }
        initializeDescription();
    }

    @Override
    public void standardUse(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i <= 1; i++) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.2F));
            ManaBurnAction.ignite(m, magicNumber);
        }
        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(new AtroposScissorHalf(), true, false));
        scissorFlip = true;
        flipCard();
    }

    @Override
    public void flipUse(AbstractPlayer p, AbstractMonster m) {
        if (!AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty() && scissors.contains(AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1).cardID)) {
            damage += block;
        } else {
            damage = baseDamage;
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        ManaBurnAction.ignite(m, magicNumber);
        rightHalf = true;

    }

    @Override
    public void addScissors() {
        scissors.add(AtroposScissorHalf.ID);
        scissors.add(AtroposSeveredScissors.ID);
    }
}
