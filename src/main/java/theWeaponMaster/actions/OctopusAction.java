package theWeaponMaster.actions;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawReductionPower;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.legendary_weapons.LeviathanGauntletCharger;
import theWeaponMaster.patches.CenterGridCardSelectScreen;
import theWeaponMaster.relics.ArsenalRelic;

import java.util.ArrayList;

import static theWeaponMaster.TheWeaponMaster.makeCardPath;

public class OctopusAction extends AbstractGameAction {
    private boolean pickCard = false;
    private AbstractPlayer p = AbstractDungeon.player;
    private ArrayList<AbstractCard> weapon = new ArrayList<>();

    private int damage = -1;
    private int block = -1;
    private AbstractMonster target;
    private AbstractCard funCard;
    private DamageInfo.DamageType damageTypeForTurn;

    public OctopusAction() {
        duration = Settings.ACTION_DUR_XFAST;
        actionType = ActionType.WAIT;

    }

    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_XFAST) {
            pickCard = true;
            CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            if (!(ArsenalRelic.currentWeapon.equals("Fenrir")) && ArsenalRelic.fenrirUnlocked) {
                group.addToTop(new ShiftingChoiceCard("Fenrir", "Fenrir", makeCardPath("Power.png"), "Equip the evolving Fenrir.", AbstractCard.CardType.POWER));
            }
            if (!(ArsenalRelic.currentWeapon.equals("Cerberus")) && ArsenalRelic.cerberusUnlocked) {
                group.addToTop(new ShiftingChoiceCard("Cerberus", "Cerberus", makeCardPath("Power.png"), "Equip the quick Cerberus.", AbstractCard.CardType.POWER));
            }
            if (!(ArsenalRelic.currentWeapon.equals("Revenant")) && ArsenalRelic.revenantUnlocked) {
                group.addToTop(new ShiftingChoiceCard("Revenant Cleaver", "Revenant Cleaver", makeCardPath("Power.png"), "Equip the hungry Revenant_Cleaver", AbstractCard.CardType.POWER));
            }
            if (!(ArsenalRelic.currentWeapon.equals("Atropos")) && ArsenalRelic.atroposUnlocked) {
                group.addToTop(new ShiftingChoiceCard("Atropos' Shears", "Atropos' Shears", makeCardPath("Power.png"), "Equip the versatile Atropos_Shears.", AbstractCard.CardType.POWER));
            }
            if (!(ArsenalRelic.currentWeapon.equals("Leviathan")) && ArsenalRelic.leviathanUnlocked) {
                group.addToTop(new ShiftingChoiceCard("Leviathan Gauntlet", "Leviathan Gauntlet", makeCardPath("Power.png"), "Equip the oppressive Leviathan_Gauntlet.", AbstractCard.CardType.POWER));
            }

            CenterGridCardSelectScreen.centerGridSelect = true;
            AbstractDungeon.gridSelectScreen.open(group, 1, "Choose your Weapon", false, false, true, false);
        } else if (pickCard && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            pickCard = false;
            AbstractCard cardChoice = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            CenterGridCardSelectScreen.centerGridSelect = false;

            new ImprovedWeaponCardRemovalAction();

            if (cardChoice.name.equals("Fenrir")) {
                AbstractDungeon.actionManager.addToBottom(new GiveWeaponsAction(cardChoice.toString()));
                new ArsenalRelic().setCurrentWeapon("Fenrir");
            }
            if (cardChoice.name.equals("Cerberus")) {
                AbstractDungeon.actionManager.addToBottom(new GiveWeaponsAction(cardChoice.toString()));
                new ArsenalRelic().setCurrentWeapon("Cerberus");
            }
            if (cardChoice.name.equals("Revenant Cleaver")) {
                AbstractDungeon.actionManager.addToBottom(new GiveWeaponsAction(cardChoice.toString()));
                new ArsenalRelic().setCurrentWeapon("Revenant");
            }
            if (cardChoice.name.equals("Atropos' Shears")) {
                AbstractDungeon.actionManager.addToBottom(new GiveWeaponsAction(cardChoice.toString()));
                new ArsenalRelic().setCurrentWeapon("Atropos");
            }
            if (cardChoice.name.equals("Leviathan Gauntlet")) {
                AbstractDungeon.actionManager.addToBottom(new GiveWeaponsAction(cardChoice.toString()));
                new ArsenalRelic().setCurrentWeapon("Leviathan");
            }
            AbstractDungeon.player.draw(2);
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DrawReductionPower(p, 0)));
            isDone = true;
        }
        tickDuration();
    }

    public void leviathanCharge() {
        if (duration == Settings.ACTION_DUR_XFAST) {
            pickCard = true;
            CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

            if (ArsenalRelic.leviathanCharges > 0) {
                group.addToTop(new ShiftingChoiceCard("Discharge", "Discharge", makeCardPath("Attack.png"), "L Release all remaining Leviathan charges and deal" + LeviathanGauntletCharger.getDamage() + "damage " + ArsenalRelic.leviathanCharges + " times to random enemies.", AbstractCard.CardType.ATTACK));
            }

            if (ArsenalRelic.leviathanCharges < 3) {
                group.addToTop(new ShiftingChoiceCard("Recharge", "Recharge", makeCardPath("Skill.png"), "Recharge your Gauntlet and gain " + 5 * (3 - ArsenalRelic.leviathanCharges) + "block.", AbstractCard.CardType.SKILL));
            }

            CenterGridCardSelectScreen.centerGridSelect = true;
            AbstractDungeon.gridSelectScreen.open(group, 1, "Choose Gauntlet Charger effect:", false, false, false, false);
        } else if (pickCard && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            pickCard = false;
            AbstractCard cardChoice = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            CenterGridCardSelectScreen.centerGridSelect = false;

            if (cardChoice.name.equals("Discharge")) {
                LeviathanGauntletCharger.recharge = false;
                new LeviathanChargeAction(-ArsenalRelic.leviathanCharges);
                ArsenalRelic.leviathanCharges = 0;
            }
            if (cardChoice.name.equals("Recharge")) {
                LeviathanGauntletCharger.recharge = true;
                new LeviathanChargeAction(3 - ArsenalRelic.leviathanCharges);
            }

            isDone = true;
        }
        tickDuration();
    }

    private static class ShiftingChoiceCard extends CustomCard {
        private static final int COST = -2;
        private String baseID;

        ShiftingChoiceCard(String id, String name, String IMG, String description, CardType type) {
            super(makeID(id), name, IMG, COST, description, type, theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY, CardRarity.SPECIAL, CardTarget.NONE);

            baseID = id;

        }

        private static String makeID(String id) {
            return TheWeaponMaster.makeID("Legendary Weapon: " + id);
        }


        @Override
        public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        }

        @Override
        public void upgrade() {
        }
    }
}

