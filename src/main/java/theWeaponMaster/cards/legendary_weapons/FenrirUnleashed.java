package theWeaponMaster.cards.legendary_weapons;

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
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;

import java.util.ArrayList;
import java.util.Random;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class FenrirUnleashed extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(FenrirUnleashed.class.getSimpleName());
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    public static final String IMG = makeCardPath("fenrirunleashed.png");

    public static final CardRarity RARITY = CardRarity.SPECIAL;
    public static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    public static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    public static final int COST = -1;
    public static final int DAMAGE = 8;
    public static final int UPGRADED_DAMAGE = 5;
    public static final int MAGIC_NUMBER = 3;
    public static int totalAttacks;
    public static int baseDamageStatic;
    public static ArrayList<AbstractMonster> targetList = new ArrayList<>();

    public FenrirUnleashed() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.damage = baseDamage = DAMAGE;
        isMultiDamage = true;

        this.setBackgroundTexture("theWeaponMasterResources/images/512/bg_fenrir_attack.png", "theWeaponMasterResources/images/1024/bg_fenrir_attack.png");

        initializeDescription();
    }

    @Override
    public boolean canUpgrade() {
        return AbstractDungeon.player.hasRelic("Splintering Steel");
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
    public boolean cardPlayable(AbstractMonster m) {
        if ((EnergyPanel.getCurrentEnergy() > 0) || AbstractDungeon.player.hasRelic(ChemicalX.ID)) {
            return true;
        }
        cantUseMessage = "I don't have enough energy.";
        return false;
    }

    public AbstractGameAction.AttackEffect randomSlash() {
        Random rand = new Random();
        switch (rand.nextInt(4)) {
            case 0:
                return AbstractGameAction.AttackEffect.SLASH_DIAGONAL;
            case 1:
                return AbstractGameAction.AttackEffect.SLASH_HORIZONTAL;
            case 2:
                return AbstractGameAction.AttackEffect.SLASH_VERTICAL;
            case 3:
                return AbstractGameAction.AttackEffect.SLASH_HEAVY;
        }
        return AbstractGameAction.AttackEffect.SLASH_DIAGONAL;
    }

    private void weakestMonster(AbstractPlayer p) {
        int j = 0;
        int totalDamage = 0;
        boolean dead = false;
        new FenrirUnleashedSelectAction();
        for (AbstractMonster monster : targetList) {

            TheWeaponMaster.logger.info("Target List: " + monster + " Monster HP: " + monster.currentHealth);
        }
        for (int i = 0; i < totalAttacks; i++) {
            if (targetList.get(j).currentHealth == 0 || targetList.get(j).isDead) {
                j++;
                i--;
                continue;
            }
            TheWeaponMaster.logger.info("New target: " + targetList.get(j));
            totalDamage += damage;
            if (totalDamage >= targetList.get(j).currentHealth + targetList.get(j).currentBlock) {
                dead = true;
                totalDamage = 0;
            }
            AbstractDungeon.actionManager.addToBottom(new DamageAction(targetList.get(j), new DamageInfo(p, damage, damageTypeForTurn), randomSlash()));
            if (dead && j < targetList.size() - 1) {
                totalAttacks++;
                new FenrirEvolveAction();
                j++;
            }
            if (j == targetList.size()) {
                totalAttacks = 0;
            }
            dead = false;
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        totalAttacks = EnergyPanel.totalCount;
        EnergyPanel.useEnergy(totalAttacks);
        if (p.hasRelic(ChemicalX.ID)) {
            totalAttacks += 2;
            p.getRelic(ChemicalX.ID).flash();
        }
        //TODO: Kinda works?
        weakestMonster(p);
        targetList.clear();
    }
}
