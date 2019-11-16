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
import theWeaponMaster.relics.ShockwaveModulatorRelic;

import static theWeaponMaster.DefaultMod.makeCardPath;

public class LeviathanDeepImpact extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(LeviathanDeepImpact.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    public static final String IMG = makeCardPath("Attack.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheWeaponMaster.Enums.COLOR_GRAY;

    private static final int COST = 2;
    private static final int DAMAGE = 8;
    private static final int UPGRADED_DAMAGE = 2;
    private static final int MAGIC_NUMBER = 4;
    private static final int UPGRADED_MAGIC_NUMBER = 2;

    public LeviathanDeepImpact() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = baseDamage = DAMAGE;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;

    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADED_DAMAGE);
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            initializeDescription();
        }
    }

    public boolean canUpgrade() {
        return AbstractDungeon.player.hasRelic(ShockwaveModulatorRelic.ID);
    }

    public int impactDamage(AbstractMonster monster){
        if (monster.currentBlock>0){
            this.damage += magicNumber;
        }
        return this.damage;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster target : AbstractDungeon.getMonsters().monsters) {
            if (target.currentBlock!=0){
                AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(p, impactDamage(target) ), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            } else {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(p, impactDamage(target) ), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                break;
            }
        }
    }
}
