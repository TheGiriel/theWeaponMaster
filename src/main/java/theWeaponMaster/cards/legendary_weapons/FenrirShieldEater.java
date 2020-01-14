package theWeaponMaster.cards.legendary_weapons;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.actions.utility.ShakeScreenAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.FenrirEvolveAction;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.relics.SplinteringSteelRelic;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class FenrirShieldEater extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(FenrirShieldEater.class.getSimpleName());
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG = makeCardPath("fenrirshieldeater.png");

    public static final CardRarity RARITY = CardRarity.SPECIAL;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;

    public static final int COST = 1;
    public static final int MAGIC_NUMBER = 6;
    public static final int UPGRADED_MAGIC_NUMBER = 1;
    public static final int DAMAGE = 8;
    public static final int UPGRADED_DAMAGE = 4;

    public FenrirShieldEater() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;
        this.damage = baseDamage = DAMAGE;

        this.setBackgroundTexture("theWeaponMasterResources/images/512/bg_fenrir_attack.png", "theWeaponMasterResources/images/1024/bg_fenrir_attack.png");

        initializeDescription();
    }

    @Override
    public boolean canUpgrade() {
        return AbstractDungeon.player.hasRelic(SplinteringSteelRelic.ID);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int shieldEater = 0;
        boolean canEvolve = false;
        if (m.currentBlock > 0) {
            if (magicNumber >= m.currentBlock) {
                shieldEater = m.currentBlock;
                canEvolve = true;
            } else {
                shieldEater = magicNumber;
            }
        }

        AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(m.hb.x, m.hb.y)));
        AbstractDungeon.actionManager.addToBottom(new ShakeScreenAction(0.2F, ScreenShake.ShakeDur.SHORT, ScreenShake.ShakeIntensity.LOW));
        AbstractDungeon.actionManager.addToBottom(new LoseBlockAction(m, p, magicNumber));
        if (shieldEater >= 1) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, shieldEater));
        }
        if (canEvolve) {
            AbstractDungeon.actionManager.addToBottom(new FenrirEvolveAction());
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            upgradeDamage(UPGRADED_DAMAGE);
            initializeDescription();
        }
    }
}