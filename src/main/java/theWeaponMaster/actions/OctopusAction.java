package theWeaponMaster.actions;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.DefaultMod;
import theWeaponMaster.characters.TheWeaponMaster;
import theWeaponMaster.patches.CenterGridCardSelectScreen;
import theWeaponMaster.powers.*;
import theWeaponMaster.relics.ArsenalRelic;

import static theWeaponMaster.DefaultMod.makeCardPath;

public class OctopusAction extends AbstractGameAction {
    private boolean pickCard = false;
    private AbstractPlayer owner;
    private AbstractCard funCard;
    private AbstractPower power;

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
            group.addToTop(new ShiftingChoiceCard("Fenrir", "Fenrir", makeCardPath("Power.png"), "Equip the ever evolving Fenrir.", AbstractCard.CardType.POWER));
            group.addToTop(new ShiftingChoiceCard("Cerberus", "Cerberus", makeCardPath("Power.png"), "Equip the quick Cerberus.", AbstractCard.CardType.POWER));
            group.addToTop(new ShiftingChoiceCard("Revenant", "Revenant", makeCardPath("Power.png"), "Equip the hungry Revenant Cleaver", AbstractCard.CardType.POWER));
            group.addToTop(new ShiftingChoiceCard("Atropos", "Atropos", makeCardPath("Power.png"), "Equip the versatile Atropos' Shears.", AbstractCard.CardType.POWER));
            group.addToTop(new ShiftingChoiceCard("Leviathan", "Leviathan", makeCardPath("Power.png"), "Equip the oppressive Leviathan Gauntlet.", AbstractCard.CardType.POWER));

            CenterGridCardSelectScreen.centerGridSelect = true;
            AbstractDungeon.gridSelectScreen.open(group, 1, "Choose a Weapon", false);
        } else if (pickCard && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            pickCard = false;
            AbstractCard cardChoice = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            CenterGridCardSelectScreen.centerGridSelect = false;

            new ExchangeWeaponsAction(owner);

            if (cardChoice.name.equals("Fenrir")) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new FenrirPower(owner)));
                ArsenalRelic.currentWeapon = "Fenrir";
            }
            if (cardChoice.name.equals("Cerberus")) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new CerberusPower(owner)));
                ArsenalRelic.currentWeapon = "Cerberus";
            }
            if (cardChoice.name.equals("Revenant")) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new RevenantPower(owner)));
                ArsenalRelic.currentWeapon = "Revenant";
            }
            if (cardChoice.name.equals("Atropos")) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new AtroposPower(owner)));
                ArsenalRelic.currentWeapon = "Atropos";
            }
            if (cardChoice.name.equals("Leviathan")) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new LeviathanPower(owner)));
                ArsenalRelic.currentWeapon = "Leviathan";
            }

            isDone = true;
        }
        tickDuration();
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

