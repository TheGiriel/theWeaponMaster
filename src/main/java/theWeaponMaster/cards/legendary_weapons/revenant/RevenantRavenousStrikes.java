package theWeaponMaster.cards.legendary_weapons.revenant;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.cards.AbstractDynamicCard;
import theWeaponMaster.characters.TheWeaponMaster;
import theWeaponMaster.powers.ViciousPower;

import static theWeaponMaster.DefaultMod.makeCardPath;

public class RevenantRavenousStrikes extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(RevenantRavenousStrikes.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG = makeCardPath("Attack.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheWeaponMaster.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int UPGRADED_DAMAGE = 3;
    private static final int MAGIC_NUMBER = 1;
    private int dmgDEALT = 0;

    public RevenantRavenousStrikes() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = MAGIC_NUMBER;
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADED_DAMAGE);
            initializeDescription();
        }
    }


    public void use(AbstractPlayer p, AbstractMonster m) {

        DefaultMod.logger.info("calculating heal amount.");
        dmgDEALT = (damage-m.currentBlock)/2;

        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        DefaultMod.logger.info("dealing damage.");
        AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, dmgDEALT));
        DefaultMod.logger.info("healing: " + dmgDEALT);
        dmgDEALT = 0;

        DefaultMod.logger.info("Gain Vicious - Ravenous Strikes.");

        DefaultMod.logger.info("Gained Vicious - Ravenous Strikes.");
        if (AbstractDungeon.player.hasPower("ViciousPower")) {
            AbstractDungeon.actionManager.addToTurnStart(new ApplyPowerAction(p, p, new ViciousPower(p, magicNumber), magicNumber));
        } else {
            AbstractDungeon.actionManager.addToTurnStart(new ApplyPowerAction(p, p, new ViciousPower(p, magicNumber), magicNumber));
        }
        this.magicNumber++;
        DefaultMod.logger.info("updating magic number");
    }

}
