package theWeaponMaster.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.characters.TheWeaponMaster;

import static theWeaponMaster.DefaultMod.makeCardPath;

public class FenrirUnleashed extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(FenrirUnleashed.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    public static final String IMG = makeCardPath("fenrirunleashed.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheWeaponMaster.Enums.COLOR_GRAY;

    private static final int COST = -1;
    private static final int DAMAGE = 8;
    private static final int UPGRADED_DAMAGE =5;
    private static final int EVOLUTION = 3;
    private static final double OVERKILL_MOD = 1.5;
    private static double overkill_mod = 1;
    private static int totalAttacks;
    private AbstractMonster weakest = null;

    public FenrirUnleashed() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        isInnate = true;

        this.damage = baseDamage = DAMAGE;
        isMultiDamage = true;
    }

    @Override
    public boolean canUpgrade() {
        return AbstractDungeon.player.hasRelic("Splintering Steel");
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADED_DAMAGE);
            overkill_mod = OVERKILL_MOD;
            rawDescription = UPGRADED_DESCRIPTION;
            initializeDescription();
        }

    }

    private void weakestMonster(AbstractPlayer p) {
        for (AbstractMonster target : AbstractDungeon.getMonsters().monsters) {
            if (target != null && target.currentHealth > 0 && !target.isDead && !target.isDying && !target.isEscaping) {
                if (weakest == null) {
                    weakest = target;
                    DefaultMod.logger.info("First weakest target: " + weakest.toString());
                }
                if (target.currentHealth < weakest.currentHealth) {
                    weakest = target;
                    DefaultMod.logger.info("New weakest target: " + weakest.toString());
                }
            }
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(weakest, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        weakest = null;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int effect = 10;
        if (p.hasRelic(ChemicalX.ID)) {
            effect += 2;
            p.getRelic(ChemicalX.ID).flash();
        }
        //TODO: Automatically switch target when weakest monster is dying
        for (int attacks = 0; attacks < effect; attacks++) {
            weakestMonster(p);
        }
    }
}
