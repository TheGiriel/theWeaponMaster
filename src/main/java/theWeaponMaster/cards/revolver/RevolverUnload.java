package theWeaponMaster.cards.revolver;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.cards.abstractcards.AbstractDynamicCard;
import theWeaponMaster.relics.RevolverRelic;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static theWeaponMaster.TheWeaponMaster.makeCardPath;
import static theWeaponMaster.patches.WeaponMasterTags.AMMUNITION;

public class RevolverUnload extends AbstractDynamicCard {

    public static final String ID = TheWeaponMaster.makeID(RevolverUnload.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    public static final CardColor COLOR = theWeaponMaster.characters.TheWeaponMaster.Enums.COLOR_GRAY;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 3;
    private static final int MAGIC_NUMBER = 3;
    private static final int UPGRADED_MAGIC_NUMBER = 2;

    public RevolverUnload() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = baseMagicNumber = MAGIC_NUMBER;

        isInnate = true;
        purgeOnUse = true;
    }

    @Override
    public void upgrade() {

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> ammoDrawPile = new ArrayList<>();
        ammoDrawPile.addAll(player.drawPile.group);

        for (AbstractCard card : player.hand.group) {
            if (card.hasTag(AMMUNITION) && player.getRelic(RevolverRelic.ID).counter > 0) {
                actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, magicNumber, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                actionManager.addToBottom(new DiscardSpecificCardAction(card));
                player.getRelic(RevolverRelic.ID).counter--;
            } else if (player.getRelic(RevolverRelic.ID).counter == 0) {
                return;
            }
            actionManager.addToBottom(new WaitAction(0.1F));
        }

        for (AbstractCard card : ammoDrawPile) {
            if (player.getRelic(RevolverRelic.ID).counter > 0 && player.drawPile.size() > 0) {
                if (card.hasTag(AMMUNITION)) {
                    actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, magicNumber, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                    actionManager.addToBottom(new DiscardSpecificCardAction(card, player.drawPile));
                    player.getRelic(RevolverRelic.ID).counter--;
                    //RevolverRelic.shotsLeft--;
                } else {
                    actionManager.addToBottom(new DiscardSpecificCardAction(card, player.drawPile));
                }
            } else return;
            actionManager.addToBottom(new WaitAction(0.08F));
        }
    }

}
