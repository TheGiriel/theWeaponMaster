package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.powers.ManaBurnPower;

public class ManaBurnAction extends AbstractGameAction {

    public static int manaBurnTotal;
    private AbstractCreature owner;
    public static int ownerHP;

    public ManaBurnAction(AbstractMonster owner, AbstractPlayer source) {
        this.owner = owner;
        this.source = source;
        manaBurnTotal = amount;
        ownerHP = owner.maxHealth;
    }

    public static void ignite(AbstractMonster m, int ignitePower) {
        if (m.hasPower(ManaBurnPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new LoseHPAction(m, AbstractDungeon.player, (int) Math.ceil(ownerHP * ManaBurnPower.IGNITE * ignitePower), AbstractGameAction.AttackEffect.FIRE));
        }
    }

    @Override
    public void update() {

    }

}
