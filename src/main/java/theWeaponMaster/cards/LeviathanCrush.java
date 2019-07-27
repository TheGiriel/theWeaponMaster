package theWeaponMaster.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.characters.TheWeaponMaster;

import static theWeaponMaster.DefaultMod.makeCardPath;

public class LeviathanCrush extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(LeviathanCrush.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static String UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    public static final String IMG = makeCardPath("Attack.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheWeaponMaster.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 8;
    private static final int UPGRADED_DAMAGE = 2;
    private int armorCrush = 0;

    public LeviathanCrush() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void upgrade() {
        if(!upgraded){
            upgradeName();
            upgradeDamage(UPGRADED_DAMAGE);
            rawDescription = UPGRADED_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public boolean canUpgrade() {
        return AbstractDungeon.player.hasRelic("ShockwaveModulatorRelic");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {


        update();
        //TODO: Review the code and write something better.
        if (m.currentBlock>0 && !upgraded){
            if (m.currentBlock < damage*2) {
                armorCrush = (int) Math.ceil(m.currentBlock*.5);
            } else {
                armorCrush = damage;
            }
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage*2, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            DefaultMod.logger.info(damage);
        }
        try {
            Thread.sleep(125);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (armorCrush>0) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p,armorCrush/2, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            DefaultMod.logger.info(damage);
        }
        else {
            armorCrush = 0;
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(p, new DamageInfo(p, (int) Math.ceil(damage*.25), damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            DefaultMod.logger.info(damage);
        }

    }
}
