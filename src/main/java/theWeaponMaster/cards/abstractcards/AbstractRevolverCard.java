package theWeaponMaster.cards.abstractcards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import theWeaponMaster.powers.MarksmanshipPower;
import theWeaponMaster.relics.HeavyDrum;
import theWeaponMaster.relics.RevolverRelic;

public abstract class AbstractRevolverCard extends AbstractDynamicCard {

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

    @Override
    public void modifyCostForTurn(int amt) {
        if (AbstractDungeon.player.hasRelic(RevolverRelic.ID) && AbstractDungeon.player.getRelic(RevolverRelic.ID).counter <= 0) {
            this.costForTurn = 0;
        } else {
            this.costForTurn = cost;
        }
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
        } else {
            applyPowers();
            return tmp;
        }
    }

    @Override
    public void applyPowers() {
    }
}
