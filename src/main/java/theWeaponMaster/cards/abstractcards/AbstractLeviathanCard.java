package theWeaponMaster.cards.abstractcards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.patches.WeaponMasterTags;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public abstract class AbstractLeviathanCard extends AbstractDefaultCard {

    public static final int CHARGE_COST = 0;

    public AbstractLeviathanCard(String id,
                                 String img,
                                 int cost,
                                 CardType type,
                                 CardColor color,
                                 CardRarity rarity,
                                 CardTarget target) {

        super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);

    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (c.hasTag(WeaponMasterTags.LEVIATHAN)) {

        }
    }
}