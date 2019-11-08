package theWeaponMaster.actions;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.cards.*;
import theWeaponMaster.cards.Not_finished.*;
import theWeaponMaster.characters.TheWeaponMaster;
import theWeaponMaster.patches.CenterGridCardSelectScreen;
import theWeaponMaster.relics.ArsenalRelic;

import java.util.ArrayList;

import static theWeaponMaster.DefaultMod.makeCardPath;

public class OctopusAction extends AbstractGameAction {
    private boolean pickCard = false;
    private AbstractPlayer owner;
    private AbstractCard funCard;
    private AbstractPower power;
    private ArrayList<AbstractCard> weapon = new ArrayList<>();

    public OctopusAction(AbstractPlayer player, AbstractCard q) {
        duration = Settings.ACTION_DUR_XFAST;
        actionType = ActionType.WAIT;
        owner = player;
        funCard = q;
    }

    @Override
    public void update() {

        funCard.applyPowers();
        if (duration == Settings.ACTION_DUR_XFAST) {
            pickCard = true;
            CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            if (!ArsenalRelic.currentWeapon.equals("Fenrir") && ArsenalRelic.fenrirUnlocked) {
                group.addToTop(new ShiftingChoiceCard("Fenrir", "Fenrir", makeCardPath("Power.png"), "Equip the evolving Fenrir.", AbstractCard.CardType.POWER));
            }
            if (!ArsenalRelic.currentWeapon.equals("Cerberus") && ArsenalRelic.cerberusUnlocked) {
                group.addToTop(new ShiftingChoiceCard("Cerberus", "Cerberus", makeCardPath("Power.png"), "Equip the quick Cerberus.", AbstractCard.CardType.POWER));
            }
            if (!ArsenalRelic.currentWeapon.equals("Revenant Cleaver") && ArsenalRelic.revenantUnlocked) {
                group.addToTop(new ShiftingChoiceCard("Revenant Cleaver", "Revenant Cleaver", makeCardPath("Power.png"), "Equip the hungry Revenant Cleaver", AbstractCard.CardType.POWER));
            }
            if (!ArsenalRelic.currentWeapon.equals("Atropos' Shears") && ArsenalRelic.atroposUnlocked) {
                group.addToTop(new ShiftingChoiceCard("Atropos' Shears", "Atropos' Shears", makeCardPath("Power.png"), "Equip the versatile Atropos' Shears.", AbstractCard.CardType.POWER));
            }
            if (!ArsenalRelic.currentWeapon.equals("Leviathan Gauntlet") && ArsenalRelic.leviathanUnlocked) {
                group.addToTop(new ShiftingChoiceCard("Leviathan Gauntlet", "Leviathan Gauntlet", makeCardPath("Power.png"), "Equip the oppressive Leviathan Gauntlet.", AbstractCard.CardType.POWER));
            }

            CenterGridCardSelectScreen.centerGridSelect = true;
            AbstractDungeon.gridSelectScreen.open(group, 1, "Choose your Weapon", false);
        } else if (pickCard && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            pickCard = false;
            AbstractCard cardChoice = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            CenterGridCardSelectScreen.centerGridSelect = false;

            new ImprovedWeaponCardRemovalAction();

            if (cardChoice.name.equals("Fenrir")) {
                weapon.add(new FenrirLacerate());
                weapon.add(new FenrirHeavySwing());
                weapon.add(new FenrirUnleashed());
                weapon.add(new FenrirShieldEater());
                weapon.add(new FenrirDefensiveStance());

                new ArsenalRelic().setCurrentWeapon("Fenrir");
            }
            if (cardChoice.name.equals("Cerberus")) {
                weapon.add(new CerberusSlash());
                weapon.add(new CerberusIaiSlash());
                weapon.add(new CerberusEssenceSlash());
                weapon.add(new CerberusModularSlash());
                weapon.add(new CerberusDrainSlash());

                new ArsenalRelic().setCurrentWeapon("Cerberus");
            }
            if (cardChoice.name.equals("Revenant Cleaver")) {
                weapon.add(new RevenantRavenous());
                weapon.add(new RevenantChopChopCHOP());
                weapon.add(new RevenantHungrySteel());
                weapon.add(new RevenantSnoutToTail());
                weapon.add(new RevenantBloodbath());

                new ArsenalRelic().setCurrentWeapon("Revenant Cleaver");
            }
            if (cardChoice.name.equals("Atropos' Shears")) {
                weapon.add(new AtroposSeveredSource());
                weapon.add(new AtroposSeveredScissors());
                weapon.add(new AtroposSeveredPath());
                weapon.add(new AtroposSeveredPain());
                weapon.add(new AtroposSeveredSoul());

                new ArsenalRelic().setCurrentWeapon("Atropos' Shears");
            }
            if (cardChoice.name.equals("Leviathan Gauntlet")) {
                weapon.add(new LeviathanCrush());
                weapon.add(new LeviathanEject());
                weapon.add(new LeviathanGroundSplitter());
                weapon.add(new LeviathanDeepImpact());
                weapon.add(new LeviathanEarthquake());

                new ArsenalRelic().setCurrentWeapon("Leviathan Gauntlet");
            }

            giveWeapons();
            weapon.clear();

            isDone = true;
        }
        tickDuration();
    }

    private void giveWeapons(){
        for (AbstractCard c : weapon){
            AbstractDungeon.player.masterDeck.addToBottom(c);
            AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, true, false));
        }
    }

    private static class ShiftingChoiceCard extends CustomCard {
        private static final int COST = -2;
        private String baseID;

        ShiftingChoiceCard(String id, String name, String IMG, String description, CardType type) {
            super(makeID(id), name, IMG, COST, description, type, TheWeaponMaster.Enums.COLOR_GRAY, CardRarity.SPECIAL, CardTarget.SELF);

            baseID = id;

        }

        private static String makeID(String id) {
            return DefaultMod.makeID("Legendary Weapon: " + id);
        }


        @Override
        public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        }

        @Override
        public void upgrade() {
        }
    }
}

