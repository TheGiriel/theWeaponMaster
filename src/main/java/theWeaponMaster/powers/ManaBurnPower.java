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
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import theWeaponMaster.util.TextureLoader;

import static theWeaponMaster.DefaultMod.makePowerPath;

public class ManaBurnPower extends AbstractPower {

    private static final String POWER_ID = "ManaBurnPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    private AbstractCreature source;
    private int manaBurnIntensity ;
    private AbstractMonster m;

    public ManaBurnPower(AbstractCreature owner, AbstractCreature source, int manaBurn){
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = manaBurn;
        this.source = source;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
        this.type = PowerType.DEBUFF;
        m = (AbstractMonster) this.owner;

        this.manaBurnIntensity = (int)Math.ceil(owner.maxHealth*0.01*this.amount);
        isTurnBased = true;

    }

    private void updateDamage(){
        if(this.amount > 3){
            this.amount = 3;
        }
        this.manaBurnIntensity = (int)Math.ceil(owner.maxHealth*0.01*this.amount);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (this.amount<3) {
            this.description = DESCRIPTION[0] + this.amount + DESCRIPTION[1] + this.amount*3 + DESCRIPTION[2] + this.amount;
        } else {
            this.description = DESCRIPTION[0] + this.amount + DESCRIPTION[1] + this.amount*3 + DESCRIPTION[3];
        }
    }

    public void stackPower(int stackAmount){
        this.amount += stackAmount;
        if (this.amount > 3) {
            this.amount = 3;
        }
        updateDamage();
    }

    public void atStartOfTurn() {
        if (m.intent == Intent.ATTACK_BUFF || m.intent == Intent.ATTACK_DEBUFF || m.intent == Intent.DEFEND_BUFF || m.intent == Intent.DEFEND_DEBUFF) {
            AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this.owner, this.source, this.manaBurnIntensity, AbstractGameAction.AttackEffect.FIRE));
        } else if (m.intent == Intent.BUFF || m.intent == Intent.DEBUFF) {
            AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this.owner, this.source, this.manaBurnIntensity * 2, AbstractGameAction.AttackEffect.FIRE));

        } else if (m.intent == Intent.STRONG_DEBUFF || m.intent == Intent.MAGIC) {
            AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this.owner, this.source, this.manaBurnIntensity * 3, AbstractGameAction.AttackEffect.FIRE));
        }
    }
}
