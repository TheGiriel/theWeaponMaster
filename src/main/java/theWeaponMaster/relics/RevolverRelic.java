package theWeaponMaster.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.powers.ReloadPower;
import theWeaponMaster.util.TextureLoader;

import static theWeaponMaster.TheWeaponMaster.makeRelicOutlinePath;
import static theWeaponMaster.TheWeaponMaster.makeRelicPath;
import static theWeaponMaster.patches.WeaponMasterTags.REVOLVER;

public class RevolverRelic extends CustomRelic {

    public static final String ID = TheWeaponMaster.makeID(RevolverRelic.class.getSimpleName());
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));
    public static int SHOTS = 6;
    public static AbstractCreature owner;

    public RevolverRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.CLINK);
        this.counter = SHOTS;

        owner = owner;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.hasTag(REVOLVER)) {
            if (this.counter <= 0) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new ReloadPower()));
                return;
            }
            this.counter--;
        }
    }

    @Override
    public void update() {
        if (this.counter <= 0) {
            beginLongPulse();
        }
    }

    public void onEquip() {
    }

    @Override
    public void atBattleStart() {
        if (this.counter <= 0) {
            counter = 0;
        }
    }

    @Override
    public void onEnterRestRoom() {
        super.onEnterRestRoom();
    }
}
