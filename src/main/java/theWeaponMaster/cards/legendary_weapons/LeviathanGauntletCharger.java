package theWeaponMaster.cards.legendary_weapons;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.LeviathanGauntletAction;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.relics.ArsenalRelic;
import theWeaponMaster.relics.ShockwaveModulatorRelic;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class LeviathanGauntletCharger extends AbstractDynamicCard /*implements FlipCard*/ {

    public static final String ID = TheWeaponMaster.makeID(LeviathanGauntletCharger.class.getSimpleName());
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    public static final String IMG = makeCardPath("Attack.png");

    public static final CardRarity RARITY = CardRarity.SPECIAL;
    public static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    public static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    public static final int COST = 1;
    public static final int DAMAGE = 4;
    public static final int UPGRADED_DAMAGE = 2;
    public static final int BLOCK = 4;
    public static final int UPGRADED_BLOCK = 2;

    public static int publicDamage;
    public static int publicBlock;

    public LeviathanGauntletCharger() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.damage = publicDamage = baseDamage = DAMAGE;
        this.block = publicBlock = baseBlock = BLOCK;
        this.secondValue = baseSecondValue = ArsenalRelic.leviathanCharges;

        this.setBackgroundTexture("theWeaponMasterResources/images/512/bg_leviathan_skill_3_charge_sm.png",
                "theWeaponMasterResources/images/1024/bg_leviathan_skill_3_charge.png");

        initializeDescription();
    }

    public static int getPublicDamage() {
        return publicDamage;
    }

    public static int getPublicBlock() {
        return publicBlock;
    }

    public boolean canUpgrade() {
        return AbstractDungeon.player.hasRelic(ShockwaveModulatorRelic.ID);
    }
    
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADED_DAMAGE);
            upgradeBlock(UPGRADED_BLOCK);
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new LeviathanGauntletAction());

    }
}
