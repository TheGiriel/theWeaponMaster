package theWeaponMaster.cards.legendary_weapons;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.cards.tempCards.AtroposLeftHalf;
import theWeaponMaster.cards.tempCards.AtroposRightHalf;
import theWeaponMaster.cards.tempCards.AtroposScissorHalf;
import theWeaponMaster.powers.ManaBurnPower;
import theWeaponMaster.relics.ManaWhetstoneRelic;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class AtroposSeveredScissors extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(AtroposSeveredScissors.class.getSimpleName());
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    public static final String IMG = makeCardPath("Attack.png");

    public static final CardRarity RARITY = CardRarity.SPECIAL;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    public static final int COST = 1;
    public static final int DAMAGE = 3;
    public static final int UPGRADED_DAMAGE = 1;
    public static final int BLOCK = 2;
    public static final int UPGRADED_BLOCK = 1;
    public static final int MAGIC_NUMBER = 1;

    public AtroposSeveredScissors() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.damage = baseDamage = DAMAGE;
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.block = baseBlock = BLOCK;

        this.setBackgroundTexture("theWeaponMasterResources/images/512/bg_atropos_attack.png", "theWeaponMasterResources/images/1024/bg_atropos_attack.png");

        initializeDescription();
        this.cardsToPreview = new AtroposScissorHalf();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADED_DAMAGE);
            upgradeBlock(UPGRADED_BLOCK);
            this.cardsToPreview.upgrade();
            initializeDescription();
        }
    }

    @Override
    public boolean canUpgrade() {
        return AbstractDungeon.player.hasRelic(ManaWhetstoneRelic.ID);
    }


    //TODO: Improve Code
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new ManaBurnPower(m, p, magicNumber)));

        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(new AtroposRightHalf(), Settings.WIDTH * 0.6F, Settings.HEIGHT * 0.5F, false));
        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(new AtroposLeftHalf(), Settings.WIDTH * 0.4F, Settings.HEIGHT * 0.5F, false));
    }
}
