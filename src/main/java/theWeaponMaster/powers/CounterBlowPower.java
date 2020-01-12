package theWeaponMaster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.util.TextureLoader;

import static theWeaponMaster.TheWeaponMaster.makePowerPath;

public class CounterBlowPower extends AbstractPower {

    public static final String POWER_ID = TheWeaponMaster.makeID(CounterBlowPower.class.getSimpleName());
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(CounterBlowPower.class.getSimpleName());
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    private static final AbstractPlayer player = AbstractDungeon.player;
    public CardGroup drawPile = AbstractDungeon.player.drawPile;
    private int costBonus;

    public CounterBlowPower(AbstractPlayer p, int counterPower, int costBonus) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.costBonus = costBonus;

        this.owner = p;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.type = PowerType.BUFF;
        this.amount = counterPower;

        updateDescription();
    }
/*
    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        if (player.drawPile!=null){
            player.drawPile.moveToDiscardPile(player.drawPile.getTopCard());
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new ThornsPower(player, 6)));
        }
        return damage;
    }
*/

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        try {
            if (AbstractDungeon.player.drawPile != null && info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner) {
                this.flash();
                int cardCost = drawPile.getTopCard().cost;
                if (cardCost < 0) {
                    cardCost = 0;
                }
                if (AbstractDungeon.player.drawPile.getTopCard().type == AbstractCard.CardType.ATTACK) {
                    AbstractDungeon.actionManager.addToTop(new DamageAction(info.owner, new DamageInfo(player, Math.min(Math.max(cardCost + costBonus, drawPile.getTopCard().baseDamage), this.amount))));
                } else
                    AbstractDungeon.actionManager.addToTop(new DamageAction(info.owner, new DamageInfo(player, cardCost + costBonus, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
                AbstractDungeon.player.drawPile.moveToDiscardPile(drawPile.getTopCard());
            }
        } catch (ArrayIndexOutOfBoundsException e) {

        }
        return damageAmount;
    }

    public void updateDescription() {
        this.description = DESCRIPTION[0] + costBonus + DESCRIPTION[1] + this.amount + DESCRIPTION[2] + costBonus + DESCRIPTION[3];
    }

}
