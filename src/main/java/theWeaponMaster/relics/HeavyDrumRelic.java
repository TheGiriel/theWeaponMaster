package theWeaponMaster.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.patches.WeaponMasterTags;
import theWeaponMaster.util.TextureLoader;

import static theWeaponMaster.TheWeaponMaster.makeRelicOutlinePath;
import static theWeaponMaster.TheWeaponMaster.makeRelicPath;

public class HeavyDrumRelic extends CustomRelic {


    public static final String ID = TheWeaponMaster.makeID(HeavyDrumRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public HeavyDrumRelic() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStartPreDraw() {
        flash();
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (c.hasTag(WeaponMasterTags.AMMUNITION) && c.type == AbstractCard.CardType.ATTACK) {
            c.damage++;
        }
    }

    @Override
    public void onUnequip() {

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
