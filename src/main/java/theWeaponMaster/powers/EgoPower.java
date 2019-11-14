package theWeaponMaster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.util.TextureLoader;

public class EgoPower extends AbstractPower {

    private static final String POWER_ID = DefaultMod.makeID(EgoPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("EgoPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;


    private static final Texture tex84 = TextureLoader.getTexture(DefaultMod.makePowerPath("vicious1_placeholder_32.png"));
    private static final Texture tex32 = TextureLoader.getTexture(DefaultMod.makePowerPath("vicious1_placeholder_32.png"));

    public EgoPower(AbstractPlayer player) {

    }

}
