package theWeaponMaster.powers;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.util.TextureLoader;
public class ManaBurnPower
        extends AbstractPower
{
    private static final String POWER_ID = "ManaBurnPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("ManaBurnPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(DefaultMod.makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(DefaultMod.makePowerPath("placeholder_power32.png"));

    private AbstractCreature source;
    private int manaBurnIntensity;
    private AbstractMonster m;

    public ManaBurnPower(AbstractCreature owner, AbstractCreature source, int manaBurn) {
        this.name = NAME;
        this.ID = "ManaBurnPower";
        this.owner = owner;
            this.amount = manaBurn;
        this.source = source;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
        this.type = AbstractPower.PowerType.DEBUFF;
        this.m = (AbstractMonster)this.owner;

        this.manaBurnIntensity = (int)Math.ceil(owner.maxHealth * 0.01D * this.amount);
        this.isTurnBased = true;
    }

    private void updateDamage() {
        if (this.amount > 3) {
            this.amount = 3;
        }
        this.manaBurnIntensity = (int)Math.ceil(this.owner.maxHealth * 0.01D * this.amount);
        updateDescription();
    }

    public void updateDescription() {
        if (this.amount < 3) {
            this.description = DESCRIPTION[0] + this.amount + DESCRIPTION[1] + (this.amount * 3) + DESCRIPTION[2] + this.amount;
        } else {
            this.description = DESCRIPTION[0] + this.amount + DESCRIPTION[1] + (this.amount * 3) + DESCRIPTION[3];
        }
    }

    public void stackPower(int stackAmount) {
        this.amount += stackAmount;
        if (this.amount > 2) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.source, "ManaBurnPower"));
            DefaultMod.logger.info("Removing Mana burn, applying Manablaze.");
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.source, new ManablazePower(this.owner, this.source)));
        }
        updateDamage();

    }

    public void atStartOfTurn() {
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flashWithoutSound();
            if (this.m.intent == AbstractMonster.Intent.ATTACK_BUFF || this.m.intent == AbstractMonster.Intent.ATTACK_DEBUFF || this.m.intent == AbstractMonster.Intent.DEFEND_BUFF || this.m.intent == AbstractMonster.Intent.DEFEND_DEBUFF) {
                AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this.owner, this.source, this.manaBurnIntensity, AbstractGameAction.AttackEffect.FIRE));
            } else if (this.m.intent == AbstractMonster.Intent.BUFF || this.m.intent == AbstractMonster.Intent.DEBUFF) {
                AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this.owner, this.source, this.manaBurnIntensity * 2, AbstractGameAction.AttackEffect.FIRE));
            }
            else if (this.m.intent == AbstractMonster.Intent.STRONG_DEBUFF || this.m.intent == AbstractMonster.Intent.MAGIC) {
                AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this.owner, this.source, this.manaBurnIntensity * 3, AbstractGameAction.AttackEffect.FIRE));
            }
        }
    }
}