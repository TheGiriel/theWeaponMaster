package theWeaponMaster.cards.abstractcards;

import basemod.abstracts.CustomCard;

public abstract class AbstractDefaultCard extends CustomCard {

    // Custom Abstract Cards can be a bit confusing. While this is a simple base for simply adding a second magic number,
    // if you're new to modding I suggest you skip this file until you know what unique things that aren't provided
    // by default, that you need in your own cards.

    // In this example, we use a custom Abstract Card in order to define a new magic number. From here on out, we can
    // simply use that in our cards, so long as we put "extends AbstractDynamicCard" instead of "extends CustomCard" at the start.
    // In simple terms, it's for things that we don't want to define again and again in every single card we make.

    public int secondValue;        // Just like magic number, or any number for that matter, we want our regular, modifiable stat
    public int baseSecondValue;    // And our base stat - the number in it's base state. It will reset to that by default.
    public boolean upgradedSecondValue; // A boolean to check whether the number has been upgraded or not.
    public boolean isSecondValueModified; // A boolean to check whether the number has been modified or not, for coloring purposes. (red/green)

    public AbstractDefaultCard(final String id,
                               final String name,
                               final String img,
                               final int cost,
                               final String rawDescription,
                               final CardType type,
                               final CardColor color,
                               final CardRarity rarity,
                               final CardTarget target) {

        super(id, name, img, cost, rawDescription, type, color, rarity, target);

        // Set all the things to their default values.
        isCostModified = false;
        isCostModifiedForTurn = false;
        isDamageModified = false;
        isBlockModified = false;
        isMagicNumberModified = false;
        isSecondValueModified = false;
    }

    public void displayUpgrades() { // Display the upgrade - when you click a card to upgrade it
        super.displayUpgrades();
        if (upgradedSecondValue) { // If we set upgradedDefaultSecondMagicNumber = true in our card.
            secondValue = baseSecondValue; // Show how the number changes, as out of combat, the base number of a card is shown.
            isSecondValueModified = true; // Modified = true, color it green to highlight that the number is being changed.
        }

    }

    public void upgradeSecondValue(int amount) { // If we're upgrading (read: changing) the number. Note "upgrade" and NOT "upgraded" - 2 different things. One is a boolean, and then this one is what you will usually use - change the integer by how much you want to upgrade.
        baseSecondValue += amount; // Upgrade the number by the amount you provide in your card.
        secondValue = baseSecondValue; // Set the number to be equal to the base value.
        upgradedSecondValue = true; // Upgraded = true - which does what the above method does.
    }
}