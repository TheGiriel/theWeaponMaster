package theWeaponMaster.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.actions.ReloadAction;
import theWeaponMaster.util.TextureLoader;

import static theWeaponMaster.TheWeaponMaster.makeRelicOutlinePath;
import static theWeaponMaster.TheWeaponMaster.makeRelicPath;
import static theWeaponMaster.patches.WeaponMasterTags.AMMUNITION;

public class RevolverRelic extends CustomRelic {

    public static final String ID = TheWeaponMaster.makeID(RevolverRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public static int SHOTS = 6;
    public static int shotsLeft = SHOTS;
    public static AbstractCard preloaded;

    public RevolverRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.CLINK);
        this.counter = SHOTS;
        this.description = DESCRIPTIONS[0];
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.hasTag(AMMUNITION) && counter <= 0) {
            AbstractDungeon.actionManager.addToBottom(new ReloadAction());
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
            return;
        } else if (card.hasTag(AMMUNITION)) {
            this.counter--;
        }
        counter = Math.min(Math.max(counter, 0), 6);
        if (AbstractDungeon.player.hasRelic(HeavyDrum.ID)) {
            counter = Math.min(Math.max(counter, 0), 5);
        }
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        if (counter <= 0) {
            counter = 0;
        }
        if (AbstractDungeon.player.hasRelic(HeavyDrum.ID)) {
            SHOTS = 5;
        }
    }

}
