package theWeaponMaster.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.characters.TheDefault;
import theWeaponMaster.variables.MultiShotVariable;

import static theWeaponMaster.DefaultMod.makeCardPath;

public class MultiShot extends AbstractRevolvers {

    public static final String ID = DefaultMod.makeID(MultiShot.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Attack.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 4;
    private static final int SHOTS = 2;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int UPGRADED_SHOTS = 3;
    private int shots;


    public MultiShot(){
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        baseDamage = DAMAGE;
        isMultiDamage = true;

        isInnate = true;

        shots = SHOTS;

        this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m){

        if (upgraded){
            shots = UPGRADED_SHOTS;
        } else {
            shots = SHOTS;
        }

        for (int i=0; i<shots; i++){
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY)
            );
        }
    }

    @Override
    public void upgrade(){
        if (!upgraded){
            upgradeName();
            upgradeShots(UPGRADED_SHOTS);
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }

}
