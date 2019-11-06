package theWeaponMaster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.actions.WeaponCardRemovalAction;
import theWeaponMaster.cards.CerberusIaiSlash;
import theWeaponMaster.cards.CerberusSlash;
import theWeaponMaster.cards.Not_finished.*;
import theWeaponMaster.util.TextureLoader;

import java.util.ArrayList;

public class CerberusPower extends AbstractPower {

    public static final String POWER_ID = "CerberusPower";
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("CerberusPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(DefaultMod.makePowerPath("cerberus_placeholder_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(DefaultMod.makePowerPath("cerberus_placeholder_32.png"));

    CardGroup group;
    String a = "theWeaponMaster:CerberusSlash";
    String b = "theWeaponMaster:CerberusIaiSlash";
    String c = "theWeaponMaster:CerberusEssenceSlash";
    String d = "theWeaponMaster:CerberusModularSlash";
    String e = "theWeaponMaster:CerberusDrainSlash";
    String f = "";
    String g = "";

    public CerberusPower(AbstractPlayer player) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = player;
        type = PowerType.BUFF;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        AbstractDungeon.player.masterDeck.addToBottom(new CerberusSlash());
        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(new CerberusSlash(), true, false));
        AbstractDungeon.player.masterDeck.addToBottom(new CerberusIaiSlash());
        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(new CerberusIaiSlash(), true, false));
        AbstractDungeon.player.masterDeck.addToBottom(new CerberusEssenceSlash());
        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(new CerberusEssenceSlash(), true, false));
        AbstractDungeon.player.masterDeck.addToBottom(new CerberusModularSlash());
        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(new CerberusModularSlash(), true, false));
        AbstractDungeon.player.masterDeck.addToBottom(new CerberusDrainSlash());
        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(new CerberusDrainSlash(), true, false));
    }

    @Override
    public void onRemove() {
        new WeaponCardRemovalAction(a,b,c,d,e,f,g);
    }
}
