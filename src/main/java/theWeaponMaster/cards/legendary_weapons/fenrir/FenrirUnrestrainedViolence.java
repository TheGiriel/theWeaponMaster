package theWeaponMaster.cards.legendary_weapons.fenrir;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.cards.AbstractDynamicCard;
import theWeaponMaster.characters.TheWeaponMaster;
import theWeaponMaster.patches.WeaponMasterTags;

import static theWeaponMaster.DefaultMod.makeCardPath;

public class FenrirUnrestrainedViolence extends AbstractDynamicCard{

    public static final String ID = DefaultMod.makeID(FenrirUnrestrainedViolence.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    public static final String IMG = makeCardPath("Attack.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheWeaponMaster.Enums.COLOR_GRAY;

    private static final int COST = -1;
    private static final int DAMAGE = 8;
    private static final int UPGRADED_DAMAGE =5;

    public FenrirUnrestrainedViolence() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        isInnate = true;

        damage = baseDamage = DAMAGE;
        this.tags.add(WeaponMasterTags.LW_FENRIR);
        isMultiDamage = true;
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADED_DAMAGE);
            rawDescription = UPGRADED_DESCRIPTION;
            initializeDescription();
        }

    }

    //TODO: Attack random y enemies x energy times.
    // Add Overkill damage.

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {


        double escalate = 0;
        int effect = EnergyPanel.totalCount;
        if (p.hasRelic(ChemicalX.ID)) {
            effect += 2;
            p.getRelic(ChemicalX.ID).flash();
        }
        for (int i = 0; i< effect; i++){
            for (int j = 0; j< effect; j++) {
                AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(new DamageInfo(p, (int) (damage*(1 + (escalate*.25))), damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                DefaultMod.logger.info("Attacks: " + escalate);
                DefaultMod.logger.info("Damage: " + (damage*(1 + (escalate*.25))));
                escalate++;
            }
            escalate++;
        }
    }
}
