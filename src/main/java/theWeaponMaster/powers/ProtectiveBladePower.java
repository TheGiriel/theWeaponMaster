package theWeaponMaster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.abstractcards.AbstractBullyCard;
import theWeaponMaster.cards.revolver.RevolverUnloadShot;
import theWeaponMaster.patches.WeaponMasterTags;
import theWeaponMaster.util.TextureLoader;

import static theWeaponMaster.patches.WeaponMasterTags.BULLY;

public class ProtectiveBladePower extends AbstractPower {

    public static final String POWER_ID = TheWeaponMaster.makeID(ProtectiveBladePower.class.getSimpleName());
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("ProtectiveBladePower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(TheWeaponMaster.makePowerPath("fenrir_placeholder_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(TheWeaponMaster.makePowerPath("fenrir_placeholder_32.png"));

    public ProtectiveBladePower(AbstractPlayer player, int turns) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = player;
        this.amount = turns;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        this.description = DESCRIPTION[0] + amount + DESCRIPTION[1];

        isTurnBased = true;

        updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        if (amount + stackAmount > 3) {
            amount = 3;
        }
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.cardID.equals(RevolverUnloadShot.ID)) {
            return;
        }
        if (card.hasTag(BULLY)) {
            AbstractBullyCard bullyCard = (AbstractBullyCard) card;
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(owner, owner, bullyCard.bullyNumber));
        } else if (!card.hasTag(WeaponMasterTags.BULLY) && card.type == AbstractCard.CardType.ATTACK) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(owner, owner, 2));
        }
    }

    @Override
    public void atStartOfTurnPostDraw() {
        if (amount <= 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this));
        } else amount--;
        updateDescription();
    }
}
