package theWeaponMaster;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import theWeaponMaster.util.TextureLoader;

public class WeaponUI {


    public static Texture cerbUI = TextureLoader.getTexture("theWeaponMasterResources/images/orbs/128/cerberus_orb_uneq_128.png");

    public WeaponUI() {
    }

    public void init() {
        //UI Square #1 x: 55.0,  y: 250.0,    width: 256, height: 256
        //UI Square #2 x: 106.5, y: 300.0,    width: 256, height: 256
        //UI Square #3 x: 258.0, y: 250.0,    width: 256, height: 256
        //UI Square #4 x: 308.0, y: 153.5,    width: 256, height: 256
        //UI Square #5 x: 258.0, y: 47.0,     width: 256, height: 256
    }

    public void render(SpriteBatch sb) {

    }

}
