package theWeaponMaster.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.ManaBurnAction;
import theWeaponMaster.cards.legendary_weapons.AtroposSeveredScissors;
import theWeaponMaster.cards.legendary_weapons.AtroposSeveredSoul;
import theWeaponMaster.cards.tempCards.AtroposRightHalf;
import theWeaponMaster.util.TextureLoader;

import java.util.HashSet;

import static com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.*;

public class ManaBurnPower extends AbstractPower implements HealthBarRenderPower {
    public static final String POWER_ID = TheWeaponMaster.makeID(ManaBurnPower.class.getSimpleName());
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("ManaBurnPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;

    public static final Texture manaburn_84 = TextureLoader.getTexture(TheWeaponMaster.makePowerPath("Mana_Burn_placeholder_84.png"));
    public static final Texture manaburn_32 = TextureLoader.getTexture(TheWeaponMaster.makePowerPath("Mana_Burn_placeholder_32.png"));
    public static final Texture manablaze_84 = TextureLoader.getTexture(TheWeaponMaster.makePowerPath("manablaze_placeholder_84.png"));
    public static final Texture manablaze_32 = TextureLoader.getTexture(TheWeaponMaster.makePowerPath("manablaze_placeholder_32.png"));

    private AbstractCreature source;
    public static HashSet<AbstractMonster.Intent> intent = new HashSet<>();
    public static AbstractMonster m;
    public static double IGNITE = 0.03;
    public static HashSet<String> triggers = new HashSet<>();
    private int manaBurnTotal;

    public ManaBurnPower(AbstractMonster owner, AbstractCreature source, int manaBurn) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = m = owner;
        this.amount = manaBurn;
        this.source = source;

        this.region128 = new TextureAtlas.AtlasRegion(manaburn_84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(manaburn_32, 0, 0, 32, 32);

        this.type = AbstractPower.PowerType.DEBUFF;
        m = (AbstractMonster) this.owner;

        this.isTurnBased = true;

        intent.add(ATTACK_BUFF);
        intent.add(ATTACK_DEBUFF);
        intent.add(DEFEND_BUFF);
        intent.add(DEFEND_DEBUFF);
        intent.add(BUFF);
        intent.add(DEBUFF);
        intent.add(STRONG_DEBUFF);
        intent.add(MAGIC);

        triggers.add(AtroposRightHalf.ID);
        triggers.add(AtroposSeveredSoul.ID);
        triggers.add(AtroposSeveredScissors.ID);

        getHealthBarAmount();
        updateDescription();

        new ManaBurnAction(owner, (AbstractPlayer) source);
    }

    private void updateDamage() {
        this.manaBurnTotal = (int) Math.ceil(this.owner.maxHealth * IGNITE * this.amount);
        getHealthBarAmount();
        updateDescription();
    }

    private int manaBurnDamageTurn() {
        return (int) Math.ceil(this.owner.maxHealth * IGNITE * this.amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTION[0] + manaBurnDamageTurn() + DESCRIPTION[1] + this.amount;
        getHealthBarAmount();
    }

    public int getHealthBarAmount() {
        if (intent.contains(m.intent)) {
            return manaBurnDamageTurn();
        }
        return 0;
    }

    @Override
    public Color getColor() {
        return Color.CYAN;
    }

    public void stackPower(int stackAmount) {
        this.amount += stackAmount;
        getHealthBarAmount();
        updateDamage();
    }

    public void reducePower(int stackAmount) {
        this.amount -= stackAmount;
        getHealthBarAmount();
        updateDamage();
    }

    public void atStartOfTurn() {
        if (intent.contains(m.intent)) {
            ManaBurnAction.ignite(m, amount);
        }
        getHealthBarAmount();
    }
}
