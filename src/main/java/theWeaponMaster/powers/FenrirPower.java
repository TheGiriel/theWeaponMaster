package theWeaponMaster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.cards.*;
import theWeaponMaster.util.TextureLoader;

public class FenrirPower extends AbstractPower {

    private static final String POWER_ID = "FenrirPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("FenrirPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(DefaultMod.makePowerPath("fenrir_placeholder_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(DefaultMod.makePowerPath("fenrir_placeholder_32.png"));

    public FenrirPower(AbstractPlayer player) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = player;
        type = PowerType.BUFF;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        this.description = DESCRIPTION[0];
        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        AbstractDungeon.player.masterDeck.addToBottom(new FenrirLacerate());
        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(new FenrirLacerate(), true, false));
        AbstractDungeon.player.masterDeck.addToBottom(new FenrirHeavySwing());
        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(new FenrirHeavySwing(), true, false));
        AbstractDungeon.player.masterDeck.addToBottom(new FenrirUnleashed());
        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(new FenrirUnleashed(), true, false));
        AbstractDungeon.player.masterDeck.addToBottom(new FenrirShieldEater());
        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(new FenrirShieldEater(), true, false));
        AbstractDungeon.player.masterDeck.addToBottom(new FenrirDefensiveStance());
        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(new FenrirDefensiveStance(), true, false));
    }

    @Override
    public void onRemove() {
        AbstractDungeon.player.masterDeck.removeCard("theWeaponMaster:FenrirLacerate");
        AbstractDungeon.player.masterDeck.removeCard("theWeaponMaster:FenrirHeavySwing");
        AbstractDungeon.player.masterDeck.removeCard("theWeaponMaster:FenrirShieldEater");
        AbstractDungeon.player.masterDeck.removeCard("theWeaponMaster:FenrirDefensiveStance");
        AbstractDungeon.player.masterDeck.removeCard("theWeaponMaster:FenrirUnleashed");
    }
}
