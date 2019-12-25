package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.patches.CenterGridCardSelectScreen;
import theWeaponMaster.powers.LaceratePower;
import theWeaponMaster.powers.ManaBurnPower;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;
import static theWeaponMaster.cards.revolver.RevolverSpecialGradeAmmo.getPublicDamage;
import static theWeaponMaster.cards.revolver.RevolverSpecialGradeAmmo.getPublicMagic;

public class SpecialGradeAmmoAction extends AbstractGameAction {

    private boolean pickCard = false;
    private AbstractPlayer p = AbstractDungeon.player;
    private AbstractMonster m;

    public SpecialGradeAmmoAction(AbstractMonster m) {
        duration = Settings.ACTION_DUR_XFAST;
        actionType = ActionType.WAIT;
        this.m = m;
    }

    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_XFAST) {
            pickCard = true;
            CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

            group.addToTop(new OctopusAction.ShiftingChoiceCard("Splintering", "Splintering", makeCardPath("Attack.png"), "Deal " + getPublicDamage() + "damage and apply " + getPublicMagic() + " Lacerate to target enemy.", AbstractCard.CardType.ATTACK));

            group.addToTop(new OctopusAction.ShiftingChoiceCard("Ethereal", "Ethereal", makeCardPath("Attack.png"), "Deal " + getPublicDamage() + "damage and apply " + getPublicMagic() + " Mana_Burn to target enemy.", AbstractCard.CardType.ATTACK));

            CenterGridCardSelectScreen.centerGridSelect = true;
            AbstractDungeon.gridSelectScreen.open(group, 1, "Choose the ammo type:", false, false, false, false);
        } else if (pickCard && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            pickCard = false;
            AbstractCard cardChoice = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            CenterGridCardSelectScreen.centerGridSelect = false;

            if (cardChoice.name.equals("Splintering")) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, getPublicDamage(), DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new LaceratePower(m, p, getPublicMagic())));
            } else if (cardChoice.name.equals("Ethereal")) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, getPublicDamage(), DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new ManaBurnPower(m, p, getPublicMagic())));
            }

            isDone = true;
        }
        tickDuration();
    }

}
