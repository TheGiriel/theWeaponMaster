package theWeaponMaster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.util.TextureLoader;

public class EgoPower extends AbstractPower {

    public static final String POWER_ID = TheWeaponMaster.makeID(EgoPower.class.getSimpleName());
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("EgoPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;


    private static final Texture tex84 = TextureLoader.getTexture(TheWeaponMaster.makePowerPath("vicious1_placeholder_32.png"));
    private static final Texture tex32 = TextureLoader.getTexture(TheWeaponMaster.makePowerPath("vicious1_placeholder_32.png"));

    public EgoPower(AbstractPlayer player) {

    }

}
