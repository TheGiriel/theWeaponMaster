package theWeaponMaster.cards.revolver;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.DaggerSprayEffect;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.abstractcards.AbstractRevolverCard;
import theWeaponMaster.relics.RevolverRelic;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;
import static theWeaponMaster.patches.WeaponMasterTags.AMMUNITION;

public class RevolverBuckshot extends AbstractRevolverCard {

    public static final String ID = TheWeaponMaster.makeID(RevolverBuckshot.class.getSimpleName());
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String[] DESCRIPTIONS = cardStrings.EXTENDED_DESCRIPTION;

    public static final String IMG = makeCardPath("Attack.png");

    public static final CardRarity RARITY = CardRarity.UNCOMMON;
    public static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    public static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    public static final int COST = 1;
    public static final int DAMAGE = 4;
    public static final int UPGRADED_DAMAGE = 3;

    public RevolverBuckshot() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.isMultiDamage = true;
        this.damage = baseDamage = DAMAGE;
        tags.add(AMMUNITION);

        initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADED_DAMAGE);
        }
    }

    @Override
    public void setNormalDescription() {
        this.cost = COST;
        rawDescription = DESCRIPTIONS[0];
        type = TYPE;
        target = TARGET;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.getRelic(RevolverRelic.ID).counter <= 0) {
            return;
        }

        AbstractDungeon.actionManager.addToBottom(new VFXAction(new DaggerSprayEffect(
                AbstractDungeon.getMonsters().shouldFlipVfx()), 0.1F));
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE));
    }
}
