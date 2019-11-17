package theWeaponMaster.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.EnemyForceAction;
import theWeaponMaster.powers.ManaBurnPower;
import theWeaponMaster.powers.SeveredPathPower;

import java.util.HashSet;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;
import static theWeaponMaster.patches.WeaponMasterTags.INTIMIDATE;

public class AtroposSeveredPath extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(AtroposSeveredPath.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String[] DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;
    private static final int DAMAGE = 5;
    private static final int UPGRADED_DAMAGE = 5;
    private static final int MAGIC_NUMBER = 1;
    public String NAME = cardStrings.NAME;
    private HashSet<AbstractMonster.Intent> intents = new HashSet<>();

    public AtroposSeveredPath() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        tags.add(INTIMIDATE);
        intents = EnemyForceAction.getIntents(this);
    }

    @Override
    protected String getCantPlayMessage() {
        return "No Enemy is attacking.";
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADED_DAMAGE);
            initializeDescription();
        }
    }

    /*@Override
    public boolean canPlay(AbstractCard card) {
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters){
            if (m.intent == AbstractMonster.Intent.ATTACK || m.intent == AbstractMonster.Intent.ATTACK_BUFF || m.intent == AbstractMonster.Intent.ATTACK_DEBUFF || m.intent == AbstractMonster.Intent.ATTACK_DEFEND) {
                enemyIntent = true;
            } target
        }
        return enemyIntent;
    }*/

    @Override
    public boolean cardPlayable(AbstractMonster m) {
        try {
            if (intents.contains(m.intent)) {
                return true;
            }
        } catch (NullPointerException e) {
            TheWeaponMaster.logger.info("Some error happened: " + e);
        }
        return false;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new SeveredPathPower(m, p)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new ManaBurnPower(m, p, magicNumber)));
    }

}
