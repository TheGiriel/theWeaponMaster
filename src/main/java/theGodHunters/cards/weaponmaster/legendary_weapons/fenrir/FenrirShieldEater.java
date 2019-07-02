package theGodHunters.cards.weaponmaster.legendary_weapons.fenrir;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theGodHunters.DefaultMod;
import theGodHunters.cards.AbstractDynamicCard;
import theGodHunters.characters.TheWeaponMaster;
import theGodHunters.patches.WeaponMasterTags;

import static theGodHunters.DefaultMod.makeCardPath;

public class FenrirShieldEater extends AbstractDynamicCard {


    public static final String ID = DefaultMod.makeID(FenrirShieldEater.class.getSimpleName());
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
    private static final int MAGIC_NUMBER = 2;
    private static final int UPGRADE_MAGIC_NUMBER = 1;

    public FenrirShieldEater() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.damage = this.baseDamage = DAMAGE;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;

        this.tags.add(WeaponMasterTags.LW_FENRIR);
    }
    //TODO: Basic: Create a weapon that increases in power permanently whenever you destroy enemy block.

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int shieldEater;
        if (damage*1.25 > m.currentBlock) {
            shieldEater = m.currentBlock;
        } else {
            shieldEater = (int) (damage*1.25);
        }

        if (m.currentBlock > 0){
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, (int) (damage*1.25), damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        } else{
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, shieldEater));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(p, new DamageInfo(p, this.magicNumber, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.POISON));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADED_DAMAGE);
            upgradeMagicNumber(UPGRADE_MAGIC_NUMBER);
            initializeDescription();
        }
    }

    @Override
    public boolean canUpgrade() {
        return true;
    }
}