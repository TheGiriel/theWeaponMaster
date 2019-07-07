package theWeaponMaster.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class StaggeredPower extends AbstractPower {

    public static final String POWER_ID = "ViciousPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("ViciousPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTION = powerStrings.DESCRIPTIONS;
    public static final int TIER_TWO = 3;
    public static final int TIER_THREE = TIER_TWO * 2;

    public StaggeredPower(final AbstractCreature owner, final AbstractCreature source, int amount) {

    }
}
