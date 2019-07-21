package theWeaponMaster.cards.bully;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.cards.AbstractDynamicCard;
import theWeaponMaster.characters.TheWeaponMaster;
import theWeaponMaster.powers.TauntPower;

import static theWeaponMaster.DefaultMod.makeCardPath;

public class BullyMeanToEveryone extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(BullyMeanToEveryone.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheWeaponMaster.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int MAGIC_NUMBER = 2;
    private static final int UPGRADED_MAGIC_NUMBER = 1;

    public BullyMeanToEveryone() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC_NUMBER;
        isInnate = true;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster t : AbstractDungeon.getMonsters().monsters) {
            if (t.intent != AbstractMonster.Intent.ATTACK && t.intent != AbstractMonster.Intent.ATTACK_BUFF && t.intent != AbstractMonster.Intent.ATTACK_DEBUFF && t.intent != AbstractMonster.Intent.ATTACK_DEFEND) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(t, p, new TauntPower(t, p)));

                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(t, p, new StrengthPower(t, 2)));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber)));
                DefaultMod.logger.info("Gaining +" + magicNumber + " Strength.");
            }
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_MAGIC_NUMBER);
            retain = true;
            initializeDescription();
        }
    }
}