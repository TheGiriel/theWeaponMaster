package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.LightningOrbEvokeAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theWeaponMaster.patches.CenterGridCardSelectScreen;
import theWeaponMaster.relics.ArsenalRelic;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;
import static theWeaponMaster.cards.legendary_weapons.LeviathanGauntletCharger.getPublicBlock;
import static theWeaponMaster.cards.legendary_weapons.LeviathanGauntletCharger.getPublicDamage;

public class LeviathanGauntletAction extends AbstractGameAction {

    private boolean pickCard = false;
    private AbstractPlayer p = AbstractDungeon.player;

    public LeviathanGauntletAction() {
        duration = Settings.ACTION_DUR_XFAST;
        actionType = ActionType.WAIT;
    }

    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_XFAST) {
            pickCard = true;
            CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

            if (ArsenalRelic.leviathanCharges > 0) {
                group.addToTop(new OctopusAction.ShiftingChoiceCard("Discharge", "Discharge", makeCardPath("Attack.png"), "Release all remaining Leviathan charges and deal " + getPublicDamage() + " damage " + ArsenalRelic.leviathanCharges + " times to random enemies.", AbstractCard.CardType.ATTACK));
            }

            if (ArsenalRelic.leviathanCharges < 3) {
                group.addToTop(new OctopusAction.ShiftingChoiceCard("Recharge", "Recharge", makeCardPath("Skill.png"), "Recharge your Gauntlet and gain " + getPublicBlock() * (3 - ArsenalRelic.leviathanCharges) + " block.", AbstractCard.CardType.SKILL));
            }

            CenterGridCardSelectScreen.centerGridSelect = true;
            AbstractDungeon.gridSelectScreen.open(group, 1, "Choose Gauntlet Charger effect:", false, false, false, false);
        } else if (pickCard && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            pickCard = false;
            AbstractCard cardChoice = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            CenterGridCardSelectScreen.centerGridSelect = false;

            if (cardChoice.name.equals("Discharge")) {
                for (int i = 0; i < ArsenalRelic.leviathanCharges; i++) {
                    AbstractDungeon.actionManager.addToBottom(new LightningOrbEvokeAction(new DamageInfo(p, getPublicDamage(), DamageInfo.DamageType.THORNS), false));
                }
                AbstractDungeon.actionManager.addToBottom(new LeviathanChargeAction(-ArsenalRelic.leviathanCharges));
            } else if (cardChoice.name.equals("Recharge")) {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, getPublicBlock() * (3 - ArsenalRelic.leviathanCharges)));
                AbstractDungeon.actionManager.addToBottom(new LeviathanChargeAction(3 - ArsenalRelic.leviathanCharges));
            }

            isDone = true;
        }
        tickDuration();
    }
}
