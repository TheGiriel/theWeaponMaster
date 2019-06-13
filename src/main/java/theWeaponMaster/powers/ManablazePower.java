package theWeaponMaster.powers;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.util.TextureLoader;
public class ManablazePower extends  AbstractPower{
    private static final String POWER_ID = "ManablazePower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("ManablazePower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(DefaultMod.makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(DefaultMod.makePowerPath("placeholder_power32.png"));

    private AbstractCreature source;
    private int manablazeIntensity;
    private AbstractMonster m;

    ManablazePower(AbstractCreature owner, AbstractCreature source) {
        this.name = NAME;
        this.ID = "ManablazePower";
        this.owner = owner;
        this.source = source;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
        this.type = AbstractPower.PowerType.DEBUFF;
        this.m = (AbstractMonster)this.owner;

        this.manablazeIntensity = (int)Math.ceil(this.owner.maxHealth * 0.03D);
        this.description = DESCRIPTION[0];
    }

    public void atStartOfTurn() {
        if (this.m.intent == AbstractMonster.Intent.ATTACK_BUFF || this.m.intent == AbstractMonster.Intent.ATTACK_DEBUFF || this.m.intent == AbstractMonster.Intent.DEFEND_BUFF || this.m.intent == AbstractMonster.Intent.DEFEND_DEBUFF) {
             AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this.owner, this.source, this.manablazeIntensity, AbstractGameAction.AttackEffect.FIRE));
        } else if (this.m.intent == AbstractMonster.Intent.BUFF || this.m.intent == AbstractMonster.Intent.DEBUFF) {
             AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this.owner, this.source, this.manablazeIntensity * 2, AbstractGameAction.AttackEffect.FIRE));
        }
        else if (this.m.intent == AbstractMonster.Intent.STRONG_DEBUFF || this.m.intent == AbstractMonster.Intent.MAGIC) {
             AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this.owner, this.source, this.manablazeIntensity * 3, AbstractGameAction.AttackEffect.FIRE));
        }
    }
}