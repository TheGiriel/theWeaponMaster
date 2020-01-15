package theWeaponMaster.cards.abstractcards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import theWeaponMaster.powers.MarksmanshipPower;
import theWeaponMaster.relics.HeavyDrum;

public abstract class AbstractRevolverCard extends AbstractDynamicCard {

    private static int COST;

    public AbstractRevolverCard(String id,
                                String img,
                                int cost,
                                CardType type,
                                CardColor color,
                                CardRarity rarity,
                                CardTarget target) {

        super(id, img, cost, type, color, rarity, target);
    }

    public void displayUpgrades() {
        super.displayUpgrades();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    public void setNormalDescription() {
        this.cost = COST;
    }


    @Override
    public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster mo, float tmp) {
        if (this.type != CardType.ATTACK) {
            return tmp;
        }
        if (player.hasRelic(HeavyDrum.ID)) {
            tmp++;
        }
        if (player.hasPower(DexterityPower.POWER_ID) && player.hasPower(MarksmanshipPower.POWER_ID)) {
            applyPowers();
            return super.calculateModifiedCardDamage(player, mo, tmp + player.getPower(DexterityPower.POWER_ID).amount);
        }
        return tmp;
    }

    @Override
    public void applyPowers() {
    }
}
