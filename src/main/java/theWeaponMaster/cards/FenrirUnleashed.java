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
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.FenrirEvolveAction;
import theWeaponMaster.actions.FenrirUnleashedSelectAction;

import java.util.ArrayList;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class FenrirUnleashed extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(FenrirUnleashed.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    public static final String IMG = makeCardPath("fenrirunleashed.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    private static final int COST = -1;
    private static final int DAMAGE = 8;
    private static final int UPGRADED_DAMAGE =5;
    private static int totalAttacks;
    public static int baseDamageStatic;
    public static ArrayList<AbstractMonster> targetList = new ArrayList<>();

    public FenrirUnleashed() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        isInnate = true;

        this.damage = baseDamage = DAMAGE;
        baseDamageStatic = baseDamage;
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
            initializeDescription();
        }

    }

    private void weakestMonster(AbstractPlayer p) {
        targetList.clear();
        int j = 0;
        int totalDamage = 0;
        int overKill = 0;
        new FenrirUnleashedSelectAction();
        for (AbstractMonster monster : targetList) {

            TheWeaponMaster.logger.info("Target List: " + monster + " Monster HP: " + monster.currentHealth);
        }
        for (int i = 0; i < totalAttacks; i++) {
            totalDamage += overKill;
            overKill = 0;
            if (totalDamage < targetList.get(j).currentHealth) {
                TheWeaponMaster.logger.info("New target: " + targetList.get(j));
                totalDamage += this.damage;
            } else if (totalDamage >= targetList.get(j).currentHealth) {
                TheWeaponMaster.logger.info(targetList.get(j) + " is dead. select new target:");
                j++;
                TheWeaponMaster.logger.info("New target: " + targetList.get(j));
                totalDamage = 0;
                overKill = totalDamage - targetList.get(j).currentHealth;
                new FenrirEvolveAction();
                i--;
            }
            AbstractDungeon.actionManager.addToBottom(new DamageAction(targetList.get(j), new DamageInfo(p, this.damage + overKill, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
        targetList.clear();
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        totalAttacks = EnergyPanel.totalCount;
        if (p.hasRelic(ChemicalX.ID)) {
            totalAttacks += 2;
            p.getRelic(ChemicalX.ID).flash();
        }
        //TODO: Automatically switch target when weakest monster is dying
        weakestMonster(p);
        targetList.clear();
    }
}
