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
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.actions.FenrirEvolveAction;
import theWeaponMaster.characters.TheWeaponMaster;
import theWeaponMaster.patches.WeaponMasterTags;

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
    private static AbstractMonster weakest;

    public FenrirUnleashed() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        isInnate = true;

        this.damage = baseDamage = DAMAGE;
        this.tags.add(WeaponMasterTags.LW_FENRIR);
        isMultiDamage = true;
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

    //TODO: Attack random y enemies x energy times.
    // Add Overkill damage.
    private AbstractMonster weakestMonster() {
        weakest = null;
        for (AbstractMonster target : AbstractDungeon.getMonsters().monsters) {
            if (!target.isDeadOrEscaped() && !target.isDying) {
                if (weakest == null) {
                    weakest = target;
                }
                if (target.currentHealth < weakest.currentHealth) {
                    weakest = target;
                    DefaultMod.logger.info("New weakest target: " + weakest.toString());
                }
            }
        }
        return weakest;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int overkill;
        int effect = EnergyPanel.totalCount;
        if (p.hasRelic(ChemicalX.ID)) {
            effect += 2;
            p.getRelic(ChemicalX.ID).flash();
        }
        //TODO: Automatically switch target when weakest monster is dying
        for (int i = 0; i < effect; i++) {
            weakestMonster();
            overkill = 0;
            if (!weakest.isDying) {
                if (damage > weakest.currentHealth) {
                    overkill = damage - weakest.currentHealth;
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(weakest, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                    new FenrirEvolveAction();
                }
            }
            AbstractDungeon.actionManager.addToBottom(new DamageAction(weakest, new DamageInfo(p, damage + (int) (overkill * overkill_mod), DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        }
    }
}
