package theWeaponMaster.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.revolver.RevolverUnload;
import theWeaponMaster.patches.WeaponMasterTags;
import theWeaponMaster.util.TextureLoader;

import static theWeaponMaster.TheWeaponMaster.makePowerPath;


public class WeaponMasterDoubleTapPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = TheWeaponMaster.makeID(WeaponMasterDoubleTapPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(WeaponMasterDoubleTapPower.class.getSimpleName());
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));
    public AbstractCreature source;

    public WeaponMasterDoubleTapPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (card.hasTag(WeaponMasterTags.AMMUNITION) && card.type.equals(AbstractCard.CardType.ATTACK) && !card.cardID.equals(RevolverUnload.ID)) {
            AbstractCard tempCard = card.makeSameInstanceOf();
            tempCard.tags.add(WeaponMasterTags.TEMPORARY);

            AbstractMonster target = (AbstractMonster) action.target;

            tempCard.current_x = tempCard.target_x = Settings.WIDTH / 2.0f - 300.0f * Settings.scale;
            tempCard.current_y = tempCard.target_y = Settings.HEIGHT / 2.0f;
            tempCard.freeToPlayOnce = true;
            if (target != null) {
                tempCard.baseDamage *= ((double) amount / 100);
                TheWeaponMaster.logger.info("bonus damage: " + tempCard.baseDamage);
            }
            tempCard.purgeOnUse = true;
            AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(tempCard, target, card.energyOnUse, true));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }


    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new WeaponMasterDoubleTapPower(owner, source, amount);
    }
}
